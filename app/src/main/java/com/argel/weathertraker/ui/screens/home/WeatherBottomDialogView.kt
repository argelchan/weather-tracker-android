package com.argel.weathertraker.ui.screens.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.argel.weathertraker.R
import com.argel.weathertraker.core.exception.Failure
import com.argel.weathertraker.core.extension.failure
import com.argel.weathertraker.core.extension.loadFromURL
import com.argel.weathertraker.core.extension.observe
import com.argel.weathertraker.core.presentation.BaseBottomSheetDialogFragment
import com.argel.weathertraker.core.presentation.BaseViewState
import com.argel.weathertraker.data.dto.WeatherRequest
import com.argel.weathertraker.databinding.WeatherBottomDialogFragmentBinding
import com.argel.weathertraker.presentation.models.LocationModel
import com.argel.weathertraker.ui.customs.informativeDialog.InformativeDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi

@AndroidEntryPoint
@DelicateCoroutinesApi
class WeatherBottomDialogView(
    var isCancelableOutside: Boolean = true,
    private val request: WeatherRequest
) : BaseBottomSheetDialogFragment(R.layout.weather_bottom_dialog_fragment) {
    private val viewModel by viewModels<HomeViewModel>()

    private lateinit var binding: WeatherBottomDialogFragmentBinding
    private var dismissedCallback: () -> Unit = {}
    private var showedCallback: () -> Unit = {}
    private var setMoveMapCallback: (LocationModel) -> Unit = {}
    fun setDismissedCallback(dismissedCallback: () -> Unit) {
        this.dismissedCallback = dismissedCallback
    }

    fun setShowedCallback(showedCallback: () -> Unit) {
        this.showedCallback = showedCallback
    }

    fun setMoveMapCallback(setMoveMapCallback: (LocationModel) -> Unit) {
        this.setMoveMapCallback = setMoveMapCallback
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.apply {
            observe(suggestions, ::onViewSearchStateChanged)
            observe(state, ::onViewStateChanged)
            failure(failure, ::handleFailure)
        }
    }

    private fun onViewStateChanged(state: BaseViewState?) {
        when(state) {
            is BaseViewState.HideLoading -> {
                binding.progress.visibility = View.GONE
            }
            is BaseViewState.ShowLoading -> {
                binding.progress.visibility = View.VISIBLE
            }
            else -> {}
        }
    }

    @SuppressLint("SetTextI18n")
    private fun onViewSearchStateChanged(state: HomeViewState?) {
        when (state) {
            is HomeViewState.SuccessWeather -> {
                binding.apply {
                    tvCountry.text = request.country
                    tvTemp.text = "${state.data.temp}°C"
                    tvStatus.text = state.data.weather.firstOrNull()?.description
                    tvThermalSensation.text = "Se siente como ${state.data.feelsLike}°C"
                    tvWind.text = "Viento: ${state.data.speed} m/s"
                    tvHumidity.text = "Humedad: ${state.data.humidity}%"
                    tvAm.text = "Amanecer:\n${state.data.sys.sunrise}"
                    tvPm.text = "Atardecer:\n${state.data.sys.sunset}"
                    ivStatus.loadFromURL(getString(R.string.loadUrlIcon, state.data.weather.firstOrNull()?.icon))
                    setMoveMapCallback(state.data.location)
                }
            }
            else -> {}
        }
    }

    private fun handleFailure(failure: Failure?) {
        when(failure) {
            is Failure.ServerError, is Failure.NetworkConnection, is Failure.FeatureFailure -> {
                val dialog = InformativeDialog(
                    title = R.string.failure_no_internet_alternative,
                    negativeEnabled = false,
                    positiveCallback = {
                        requireActivity().onBackPressedDispatcher.onBackPressed()
                    },
                    description = R.string.empty                                                                                                                                                                                                                  ,
                )
                dialog.isCancelable = false
                dialog.show(childFragmentManager, "")
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isCancelable = isCancelableOutside
    }

    override fun setBinding(view: View) {
        binding = WeatherBottomDialogFragmentBinding.bind(view)

        binding.apply {
            lifecycleOwner = viewLifecycleOwner
        }

        viewModel.getWeatherById(location = request)
        showedCallback()
    }
}
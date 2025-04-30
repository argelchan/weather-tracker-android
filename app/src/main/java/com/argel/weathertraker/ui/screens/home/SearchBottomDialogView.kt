package com.argel.weathertraker.ui.screens.home

import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import com.argel.weathertraker.R
import com.argel.weathertraker.core.exception.Failure
import com.argel.weathertraker.core.extension.failure
import com.argel.weathertraker.core.extension.observe
import com.argel.weathertraker.core.presentation.BaseBottomSheetDialogFragment
import com.argel.weathertraker.databinding.SearchBottomDialogFragmentBinding
import com.argel.weathertraker.ui.customs.informativeDialog.InformativeDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi

@AndroidEntryPoint
@DelicateCoroutinesApi
class SearchBottomDialogView(
    var isCancelableOutside: Boolean = true
) : BaseBottomSheetDialogFragment(R.layout.search_bottom_dialog_fragment) {
    private val viewModel by viewModels<HomeViewModel>()

    private lateinit var binding: SearchBottomDialogFragmentBinding
    private val adapter = SuggestionsAdapter()
    private var dismissedCallback: () -> Unit = {}
    fun setDismissedCallback(dismissedCallback: () -> Unit) {
        this.dismissedCallback = dismissedCallback
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.apply {
            observe(suggestions, ::onViewSearchStateChanged)
            failure(failure, ::handleFailure)
        }
    }

    private fun onViewSearchStateChanged(state: HomeViewState?) {
        when (state) {
            is HomeViewState.Success -> adapter.submitList(state.data)
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
        binding = SearchBottomDialogFragmentBinding.bind(view)

        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            rvCountries.adapter = adapter
            etSearch.addTextChangedListener(afterTextChanged = {
                val query = it.toString()
                if (query.length >= 3) {
                    viewModel.searchSuggestion(query)
                } else {
                    adapter.submitList(emptyList())
                }
            })
        }
    }
}
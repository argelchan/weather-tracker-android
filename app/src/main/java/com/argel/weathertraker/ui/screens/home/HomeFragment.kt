package com.argel.weathertraker.ui.screens.home

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.argel.weathertraker.R
import com.argel.weathertraker.core.extension.observe
import com.argel.weathertraker.core.presentation.BaseFragment
import com.argel.weathertraker.data.dto.WeatherRequest
import com.argel.weathertraker.databinding.FragmentHomeBinding
import com.argel.weathertraker.presentation.models.CurrentLocationModel
import com.argel.weathertraker.presentation.models.SuggestModel
import com.argel.weathertraker.ui.customs.informativeDialog.InformativeDialog
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.WithFragmentBindings
import kotlinx.coroutines.DelicateCoroutinesApi

@AndroidEntryPoint
@WithFragmentBindings
@DelicateCoroutinesApi
class HomeFragment : BaseFragment(R.layout.fragment_home) {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var googleMap: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var searchDialog: SearchBottomDialogView? = null
    private val viewModel by viewModels<HomeViewModel>()

    override fun useBottomNav(): Boolean = true

    override fun setBinding(view: View) {
        binding = FragmentHomeBinding.bind(view)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner

            mcvSearch.setOnClickListener {
                searchDialog = SearchBottomDialogView(true).apply {
                    setDismissedCallback {
                        binding.populated.text = it?.name
                        binding.country.text = it?.description
                        showInfo(it)
                    }
                }

                searchDialog?.show(childFragmentManager, "SearchBottomDialogView")
            }
        }
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
    }

    private fun showInfo(suggest: SuggestModel?) {
        Handler(Looper.getMainLooper()).postDelayed({
            suggest?.let { item ->
                WeatherBottomDialogView(true, WeatherRequest(
                    id = item.id,
                    country = item.name,
                )).apply {
                    setShowedCallback {
                        searchDialog?.dismiss()
                    }
                    setMoveMapCallback {
                        val currentLatLng = LatLng(it.lat, it.lon)
                        googleMap.clear()
                        googleMap.addMarker(MarkerOptions().position(currentLatLng).title(
                            getString(
                                R.string.currentCountry
                            )))
                        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 15f))
                    }
                }.show(childFragmentManager, "WeatherBottomDialogView")
            }
        }, 400)
    }

    private val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
            permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                enableMyLocationAndZoom()
            }
            permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                enableMyLocation()
            } else -> {
                InformativeDialog(
                    R.string.notice,
                    R.string.location_permission_denied,
                    negativeEnabled = false,
                    positiveCallback = {}
                ).show(childFragmentManager, "InformativeDialog")
            }
        }
    }


    private fun checkLocationPermissions() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            enableMyLocationAndZoom()
        } else {
            requestLocationPermissions()
        }
    }

    @SuppressLint("MissingPermission")
    private fun enableMyLocationAndZoom() {
        googleMap.isMyLocationEnabled = true
        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            location?.let {
                val currentLatLng = LatLng(it.latitude, it.longitude)
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 15f))
                viewModel.getMyPlaceId(it.latitude, it.longitude)
            }
        }
    }

    private fun loadMyWeather(currentLocationModel: CurrentLocationModel) {
        binding.populated.text = currentLocationModel.populated
        binding.country.text = currentLocationModel.country
        WeatherBottomDialogView(true, WeatherRequest(
            id = currentLocationModel.placeId,
            country = currentLocationModel.populated,
        )).apply {
            setShowedCallback {
                searchDialog?.dismiss()
            }
        }.show(childFragmentManager, "WeatherBottomDialogView")
    }

    @SuppressLint("MissingPermission")
    private fun enableMyLocation() {
        googleMap.isMyLocationEnabled = true
    }

    private fun requestLocationPermissions() {
        locationPermissionRequest.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
    }

    private val callback = OnMapReadyCallback { googleMap ->
        this.googleMap = googleMap
        checkLocationPermissions()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

    private fun onViewSuggestionsChanged(data: HomeViewState?) {
        when(data) {
            is HomeViewState.SuccessLocation -> {
                loadMyWeather(data.data)
            }
            else -> {}
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.apply {
            observe(suggestions, ::onViewSuggestionsChanged)
            observe(state, ::onViewStateChanged)
            observe(failure, ::handleFailure)

        }
    }
}
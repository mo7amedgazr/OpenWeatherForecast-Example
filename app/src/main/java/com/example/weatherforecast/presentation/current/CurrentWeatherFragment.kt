package com.example.weatherforecast.presentation.current

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.IntentSender
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import com.example.setting.SettingsFragment
import com.example.weatherforecast.R
import com.example.weatherforecast.databinding.FragmentCurrentWeatherBinding
import com.example.weatherforecast.domain.current.entity.CurrentWeatherEntity
import com.example.common.data.views.BaseFragment
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.tasks.Task
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class CurrentWeatherFragment : BaseFragment() {

    private var _binding: FragmentCurrentWeatherBinding? = null

    private val mViewModel: CurrentWeatherViewModel by viewModels()

    @Inject
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback
    private lateinit var locationRequest: LocationRequest


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var launcher: ActivityResultLauncher<IntentSenderRequest>

    private var isViewCreated: Boolean = false
    private var ignoreCache: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        launcher =
            requireActivity().registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    // User accepted
                    initLocationListener()
                } else {
                    // User didn't accepted
                    showToast(getString(R.string.location_permission_denied))
                }
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentCurrentWeatherBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModel()
        initSettingsListener()

        if (!isViewCreated) {
            initCurrentWeather()
            isViewCreated = true
        }
    }

    private fun initCurrentWeather() {
        if (hasLocationPermission()) {
            showLocationPrompt()
        } else
            requestLocationPermission()
    }

    private fun initSettingsListener() {
        setFragmentResultListener(SettingsFragment.REQUEST_KEY) { _, bundle ->
            // read from the bundle
            val updated = bundle.getBoolean(SettingsFragment.UNIT_UPDATED_KEY)
            if (updated) {
                ignoreCache = true
                initCurrentWeather()
            }
        }
    }

    private fun requestLocationPermission() {
        val permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            if (isGranted) {
                // Do if the permission is granted
                showLocationPrompt()
            } else {
                // Do otherwise
                showToast(getString(R.string.location_permission_denied))
            }
        }

        permissionLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
    }

    private fun hasLocationPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun showLocationPrompt() {
        val locationRequest = LocationRequest.create()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)

        val result: Task<LocationSettingsResponse> =
            LocationServices.getSettingsClient(requireActivity())
                .checkLocationSettings(builder.build())

        result.addOnCompleteListener { task ->
            try {
                val response = task.getResult(ApiException::class.java)
                // All location settings are satisfied. The client can initialize location
                // requests here.
                initLocationListener()
            } catch (exception: ApiException) {
                when (exception.statusCode) {
                    LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> {
                        try {
                            // Cast to a resolvable exception.
                            val resolvable: ResolvableApiException =
                                exception as ResolvableApiException
                            val intentSenderRequest =
                                IntentSenderRequest.Builder(exception.resolution).build()
                            launcher.launch(intentSenderRequest)
                        } catch (e: IntentSender.SendIntentException) {
                            // Ignore the error.
                        } catch (e: ClassCastException) {
                            // Ignore, should be an impossible error.
                        }
                    }
                    LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> {
                        // Location settings are not satisfied. But could be fixed by showing the
                        // user a dialog.

                        // Location settings are not satisfied. However, we have no way to fix the
                        // settings so we won't show the dialog.
                    }
                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun initLocationListener() {
        locationRequest = LocationRequest.create()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = (20 * 1000).toLong()

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(result: LocationResult) {
                super.onLocationResult(result)

                val location = result.lastLocation
                mViewModel.getCurrentWeather(location.latitude, location.longitude, ignoreCache)
                fusedLocationProviderClient.removeLocationUpdates(locationCallback)

                ignoreCache = false
            }
        }

        handleLoading(true)
        fusedLocationProviderClient.requestLocationUpdates(
            locationRequest, locationCallback,
            Looper.myLooper()!!
        )
    }

    private fun initViewModel() {
        mViewModel.currentWeatherLiveData.observe(viewLifecycleOwner) { currentWeather ->
            handleCurrentWeather(currentWeather)
        }
        mViewModel.showError.observe(viewLifecycleOwner) { error ->
            error?.let { showToast(it) }
        }
        mViewModel.showLoading.observe(viewLifecycleOwner) { isLoading ->
            handleLoading(isLoading)
        }
    }

    private fun handleCurrentWeather(currentWeather: CurrentWeatherEntity) {
        handleLoading(false)
        binding.weather = currentWeather
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
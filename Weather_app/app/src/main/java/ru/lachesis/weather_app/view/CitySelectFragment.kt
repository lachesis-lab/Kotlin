package ru.lachesis.weather_app.view

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import ru.lachesis.weather_app.R
import ru.lachesis.weather_app.viewmodel.CitySelectViewModel
import ru.lachesis.weather_app.databinding.CitySelectFragmentBinding
import ru.lachesis.weather_app.model.City
import ru.lachesis.weather_app.model.Weather
import java.io.IOException

private const val REQUEST_CODE = 7778
private const val REFRESH_PERIOD = 60000L
private const val MINIMAL_DISTANCE = 100f

class CitySelectFragment : Fragment() {

    private val recyclerView : RecyclerView by lazy { binding.citySelectRecycler }
    private var isDataSetRus: Boolean = true
    private val adapter: CitySelectAdapter = CitySelectAdapter(object: OnItemViewClickListener{
        override fun click(weather: Weather) {
            openMainFragment(weather)
/*
            val bundle = Bundle()
            bundle.putParcelable(MainFragment.BUNDLE_EXTRA, weather)
            val fManager = activity?.supportFragmentManager
            val transaction = fManager?.beginTransaction()
            transaction?.replace(R.id.main_container,MainFragment.newInstance(bundle))
*/
/*
            transaction?.replace(R.id.day_fragment_container,DayFragment.newInstance())
*//*

//            transaction?.addToBackStack("")
            transaction?.commit()
*/
        }
    })
    private var _binding: CitySelectFragmentBinding? = null
    private val binding :CitySelectFragmentBinding
        get()=_binding!!
    private val onLocationListener = object : LocationListener {

        override fun onLocationChanged(location: Location) {
            context?.let {
                getAddressAsync(it, location)
            }
        }
    }
    companion object {
        fun newInstance() = CitySelectFragment()
    }

    private lateinit var viewModel: CitySelectViewModel

    interface OnItemViewClickListener {
        fun click(weather: Weather)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = CitySelectFragmentBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.citySelectFAB.setOnClickListener  {changeWeatherDataSet()}
        binding.locationFAB.setOnClickListener { checkPermission() }
        viewModel = ViewModelProvider(this).get(CitySelectViewModel::class.java)
        adapter.setWeatherData(viewModel.getCityList(isDataSetRus))
        viewModel.getLiveData().observe(viewLifecycleOwner, Observer { RenderData(it) })
        recyclerView.adapter = adapter
    }

    private fun checkPermission() {
        activity?.let {
            when {
                ContextCompat.checkSelfPermission(
                    it,
                    Manifest.permission.ACCESS_FINE_LOCATION    
                ) == PackageManager.PERMISSION_GRANTED -> {
                    getLocation()
                }
                shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION) -> {
                    showRationaleDialog()
                }
                else -> {
                    requestPermission()
                }
            }
        }
    }

    private fun checkPermissionsResult(requestCode: Int, grantResults: IntArray) {
        when (requestCode) {
            REQUEST_CODE -> {
                var grantedPermissions = 0
                if ((grantResults.isNotEmpty())) {
                    for (i in grantResults) {
                        if (i == PackageManager.PERMISSION_GRANTED) {
                            grantedPermissions++
                        }
                    }
                    if (grantResults.size == grantedPermissions) {
                        getLocation()
                    } else {
                        showDialog(
                            getString(R.string.dialog_title_no_gps),
                            getString(R.string.dialog_message_no_gps)
                        )
                    }
                } else {
                    showDialog(
                        getString(R.string.dialog_title_no_gps),
                        getString(R.string.dialog_message_no_gps)
                    )
                }
                return
            }
        }
    }

    private fun showDialog(title: String, message: String) {
        activity?.let {
            AlertDialog.Builder(it)
                .setTitle(title)
                .setMessage(message)
                .setNegativeButton(getString(R.string.dialog_button_close)) { dialog, _ -> dialog.dismiss() }
                .create()
                .show()
        }
    }
    private fun getLocation() {
        activity?.let { context ->
            if (ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) ==
                PackageManager.PERMISSION_GRANTED
            ) {
                val locationManager =
                    context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

                if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    val provider = locationManager.getProvider(LocationManager.GPS_PROVIDER)
                    provider?.let {
                        locationManager.requestLocationUpdates(
                            LocationManager.GPS_PROVIDER,
                            REFRESH_PERIOD,
                            MINIMAL_DISTANCE,
                            onLocationListener
                        )
                    }
                } else {
                    val location =
                        locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                    if (location == null) {
                        showDialog(
                            getString(R.string.dialog_title_gps_turned_off),
                            getString(R.string.dialog_message_last_location_unknown)
                        )
                    } else {
                        getAddressAsync(context, location)
                        showDialog(
                            getString(R.string.dialog_title_gps_turned_off),
                            getString(R.string.dialog_message_last_known_location)
                        )
                    }
                }
            } else {
                showRationaleDialog()
            }
        }
    }

    private fun getAddressAsync(
        context: Context,
        location: Location
    ) {
        val geoCoder = Geocoder(context)
        Thread {
            try {
                val addresses = geoCoder.getFromLocation(
                    location.latitude,
                    location.longitude,
                    1
                )
                binding.locationFAB.post {
                    showAddressDialog(addresses[0].getAddressLine(0), location)
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }.start()
    }

    private fun showAddressDialog(address: String, location: Location) {
        activity?.let {
            AlertDialog.Builder(it)
                .setTitle(getString(R.string.dialog_address_title))
                .setMessage(address)
                .setPositiveButton(getString(R.string.dialog_address_get_weather)) { _, _ ->
                    openMainFragment(
                        Weather(
                            City(
                                address,
                                location.latitude,
                                location.longitude
                            )
                        )
                    )
                }
                .setNegativeButton(getString(R.string.dialog_button_close)) { dialog, _ -> dialog.dismiss() }
                .create()
                .show()
        }
    }

    private fun openMainFragment(weather: Weather) {
        activity?.let{

            val bundle = Bundle()
            bundle.putParcelable(MainFragment.BUNDLE_EXTRA, weather)
            //val fManager = activity?.supportFragmentManager
            val transaction = it.supportFragmentManager.beginTransaction()
            transaction?.replace(R.id.main_container,MainFragment.newInstance(bundle))
/*
            transaction?.replace(R.id.day_fragment_container,DayFragment.newInstance())
*/
//            transaction?.addToBackStack("")
            transaction?.commit()
        }

    }


    private fun requestPermission() {
        activity?.let{
            requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_CODE)
        }
    }

    private fun showRationaleDialog() {
        activity?.let{
            AlertDialog.Builder(it)
                .setTitle(R.string.dialog_rationale_give_access)
                .setMessage(R.string.dialog_rationale_message)
                .setPositiveButton(R.string.dialog_rationale_give_access,{_,_->requestPermission()})
                .setNegativeButton(R.string.dialog_rationale_decline,{dialog,_->dialog.dismiss()})
                .create()
                .show()
        }

    }


    private fun changeWeatherDataSet() {
        if (isDataSetRus) {
            binding.citySelectFAB.setImageResource(R.drawable.ic_earth)
        } else {
            binding.citySelectFAB.setImageResource(R.drawable.ic_russia)
        }
        isDataSetRus = !isDataSetRus
        viewModel.getCityList(isDataSetRus)
    }

    /*
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CitySelectViewModel::class.java)
        adapter.setWeatherData(viewModel.getCityList())
        viewModel.getLiveData().observe(viewLifecycleOwner, Observer { RenderData(it) })
        recyclerView.adapter = adapter

        // TODO: Use the ViewModel
    }
*/
    override fun onDestroy() {
        adapter.removeListener()
        super.onDestroy()
    }
    private fun RenderData(weatherList: List<Weather>) {
        adapter.setWeatherData(weatherList)

    }

}
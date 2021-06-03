package ru.lachesis.weather_app.view

import android.content.*
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.view.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import ru.lachesis.weather_app.R
import ru.lachesis.weather_app.databinding.MainFragmentBinding
import ru.lachesis.weather_app.model.Weather
import ru.lachesis.weather_app.model.WeatherIntentService
import ru.lachesis.weather_app.viewmodel.AppState
import ru.lachesis.weather_app.viewmodel.MainViewModel
import java.util.*

public const val WEATHER_BROADCAST_INTENT_FILTER = "ru.lachesis.weather_app.broadcast_intent_filter"
public const val WEATHER_BROADCAST_EXTRA = "ru.lachesis.weather_app.weather_broadcast_extra"

class MainFragment : Fragment() {

    private var weatherService: WeatherIntentService? = null
    private lateinit var weatherServiceBinder: WeatherIntentService.ServiceBinder
        //WeatherIntentService.ServiceBinder()

    private var conn: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            weatherServiceBinder = service as WeatherIntentService.ServiceBinder
            weatherService = weatherServiceBinder.getService()
            isBound = true
        }

        override fun onServiceDisconnected(name: ComponentName?) {
 //           weatherServiceBinder = null
            isBound = false
        }
    }
    var isBound: Boolean = false
    private lateinit var viewModel: MainViewModel

    private lateinit var weatherBundle: Weather
    private var _binding: MainFragmentBinding? = null

    private val binding: MainFragmentBinding
        get() = _binding!!

    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val weather = intent?.getParcelableExtra<Weather>(WEATHER_BROADCAST_EXTRA)

            Thread(Runnable { weather?.let { setData(it) } })
        }
    }

    companion object {
        const val BUNDLE_EXTRA = "weather"
        fun newInstance(bundle: Bundle?): MainFragment {
            val fragment = MainFragment()
            fragment.arguments = bundle
            return fragment
        }
    }


    /*
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)
    }
*/
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        weatherBundle = arguments?.getParcelable<Weather>(BUNDLE_EXTRA) ?: Weather()
/*
        if (weatherBundle != null) {
            val city = weatherBundle.city
            binding.cityName.text = city.city
            binding.coordinates.text = String.format(
                getString(R.string.coordinates_label),
                city.lat.toString(),
                city.lon.toString()
            )
            binding.temperature.text = weatherBundle.temperature.toString()
            binding.tempFeel.text = weatherBundle.feelsLike.toString()
        }
*/

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
//        val loader = WeatherLoader(onLoadListener, weatherBundle.city.lat,weatherBundle.city.lon)
//        loader.loadWeather()
        bindWeatherService(weatherBundle)
//        weatherService.get

        val liveData = viewModel.getLiveData()
        liveData.observe(viewLifecycleOwner, { renderData(it) })//Observer { renderData(it) })
//        if (weather==null)
        //viewModel.getWeatherRemote(weatherBundle)

        requireActivity().supportFragmentManager.beginTransaction()
            .replace(binding.dayFragmentContainer.id, DayFragment.newInstance()).commit()

        registerForContextMenu(binding.cityName)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onCreateContextMenu(
        menu: ContextMenu,
        v: View,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val menuInflater: MenuInflater = requireActivity().menuInflater
        menuInflater.inflate(R.menu.context_menu, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val fManager = activity?.supportFragmentManager
        val transaction = fManager?.beginTransaction()
        transaction?.replace(binding.mainContainer.id, CitySelectFragment.newInstance())
        val fragment = fManager?.findFragmentById(R.id.day_fragment_container)
        if (fragment != null) {
            transaction?.hide(fragment)
        }
        transaction?.addToBackStack("")?.commit()

        return true
    }


//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
//        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
//        viewModel.getLiveData()
//            .observe(viewLifecycleOwner, { renderData(it) })//Observer { renderData(it) })
//        viewModel.getWeatherLocal()
//    }

    private fun View.showSnakeBar(
        text: String,
        actionText: String,
        action: (View) -> Unit,
        length: Int = Snackbar.LENGTH_INDEFINITE
    ) {
        Snackbar.make(this, text, length).setAction(actionText, action).show()
    }

    private fun renderData(appState: AppState?) {
        when (appState) {
            is AppState.Success -> {
                val weather = appState.weather
                binding.loadingLayout.visibility = View.GONE
                setData(weather)
            }
            is AppState.Error -> {
                binding.loadingLayout.visibility = View.GONE
                binding.mainViewContainer.showSnakeBar(
                    "Ошибка",
                    "Перегрузить",
                    { viewModel.getWeatherLocal(null) })
//                Snackbar.make(binding.mainViewContainer, "Ошибка", Snackbar.LENGTH_INDEFINITE)
//                    .setAction("Перегрузить", { viewModel.getWeatherLocal(null) })
//                    .show()
            }
            is AppState.Loading -> {
                binding.loadingLayout.visibility = View.VISIBLE
            }
        }

    }

    private fun setData(weather: Weather) {
        binding.date.text =
            weather.getDateString()//weather.date.toString()//java.util.Calendar.getInstance(Locale.getDefault()).time.toString()
        binding.tempLabel.text = resources.getString(R.string.temp_label)
        binding.tempFeelLabel.text = resources.getString(R.string.feeled_label)
        binding.cityName.text = weatherBundle.city.city
        binding.coordinates.text =
            String.format(
                Locale.getDefault(),
                "${resources.getString(R.string.coordinates_label)}: ${weatherBundle.city.lat},${weatherBundle.city.lon}"
            )
        binding.temperature.text = weather.temperature.toString()
        binding.tempFeel.text = weather.feelsLike.toString()
        binding.condition.text = weather.condition


    }

    override fun onStop() {
        super.onStop()
        context?.unregisterReceiver(receiver)
        context?.unbindService(conn)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun bindWeatherService(weather: Weather) {
        val intent = Intent(requireActivity(), WeatherIntentService::class.java)
        intent.putExtra(WEATHER_BROADCAST_EXTRA, weatherBundle)

        requireActivity().bindService(intent, conn, Context.BIND_AUTO_CREATE)

//        service.unbindService(binder.)

    }

}
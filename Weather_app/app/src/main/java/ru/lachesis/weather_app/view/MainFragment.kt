package ru.lachesis.weather_app.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import ru.lachesis.weather_app.R
import ru.lachesis.weather_app.databinding.MainFragmentBinding
import ru.lachesis.weather_app.viewmodel.MainViewModel

import ru.lachesis.weather_app.model.Weather
import ru.lachesis.weather_app.viewmodel.AppState
import java.util.*

class MainFragment  : Fragment() {

    companion object {
        const val BUNDLE_EXTRA = "weather"
        fun newInstance(bundle: Bundle?): MainFragment {
            val fragment = MainFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    private lateinit var viewModel: MainViewModel

    private var _binding: MainFragmentBinding? = null

    private val binding: MainFragmentBinding
        get() = _binding!!


/*
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)
    }
*/

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val weather = arguments?.getParcelable<Weather>(BUNDLE_EXTRA)
/*
        if (weather != null) {
            val city = weather.city
            binding.cityName.text = city.city
            binding.coordinates.text = String.format(
                getString(R.string.coordinates_label),
                city.lat.toString(),
                city.lon.toString()
            )
            binding.temperature.text = weather.temperature.toString()
            binding.tempFeel.text = weather.feelsLike.toString()
        }
*/
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        val liveData = viewModel.getLiveData()
            liveData.observe(viewLifecycleOwner, { renderData(it) })//Observer { renderData(it) })
//        if (weather==null)
            viewModel.getWeatherLocal(weather)
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(binding.dayFragmentContainer.id, DayFragment.newInstance()).commit()

        registerForContextMenu(binding.cityName)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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

    private fun renderData(appState: AppState?) {
        when (appState) {
            is AppState.Success -> {
                val weather = appState.weather
                binding.loadingLayout.visibility = View.GONE
                setData(weather)
            }
            is AppState.Error -> {
                binding.loadingLayout.visibility = View.GONE
                Snackbar.make(binding.mainViewContainer, "Ошибка", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Перегрузить", { viewModel.getWeatherLocal(null) })
                    .show()
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
        binding.cityName.text = weather.city.city
        binding.coordinates.text =
            String.format(Locale.getDefault(), "${resources.getString(R.string.coordinates_label)}: ${weather.city.lat},${weather.city.lon}")
        binding.temperature.text = weather.temperature.toString()
        binding.tempFeel.text = weather.feelsLike.toString()


    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
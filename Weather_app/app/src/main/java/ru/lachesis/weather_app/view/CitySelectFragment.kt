package ru.lachesis.weather_app.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import ru.lachesis.weather_app.R
import ru.lachesis.weather_app.viewmodel.CitySelectViewModel
import ru.lachesis.weather_app.databinding.CitySelectFragmentBinding
import ru.lachesis.weather_app.model.Weather

class CitySelectFragment : Fragment() {

    private val recyclerView : RecyclerView by lazy { binding.citySelectRecycler }
    private var isDataSetRus: Boolean = true
    private val adapter: CitySelectAdapter = CitySelectAdapter(object: OnItemViewClickListener{
        override fun click(weather: Weather) {
            val bundle = Bundle()
            bundle.putParcelable(MainFragment.BUNDLE_EXTRA, weather)
            val fManager = activity?.supportFragmentManager
            val transaction = fManager?.beginTransaction()
            transaction?.replace(R.id.main_container,MainFragment.newInstance(bundle))
/*
            transaction?.replace(R.id.day_fragment_container,DayFragment.newInstance())
*/
//            transaction?.addToBackStack("")
            transaction?.commit()
        }
    })
    private var _binding: CitySelectFragmentBinding? = null
    private val binding :CitySelectFragmentBinding
        get()=_binding!!

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
        viewModel = ViewModelProvider(this).get(CitySelectViewModel::class.java)
        adapter.setWeatherData(viewModel.getCityList(isDataSetRus))
        viewModel.getLiveData().observe(viewLifecycleOwner, Observer { RenderData(it) })
        recyclerView.adapter = adapter
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
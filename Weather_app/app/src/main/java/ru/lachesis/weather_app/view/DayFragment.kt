package ru.lachesis.weather_app.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.lachesis.weather_app.R
import ru.lachesis.weather_app.databinding.DayFragmentBinding
import ru.lachesis.weather_app.model.Weather
import ru.lachesis.weather_app.viewmodel.DayViewModel

class DayFragment : Fragment() {

    companion object {
        fun newInstance() = DayFragment()
    }

    private lateinit var viewModel: DayViewModel
    private var _binding : DayFragmentBinding?=null
    private val binding
        get()=_binding!!
    private lateinit var recyclerView : RecyclerView
    private val adapter: CustomRecyclerAdapter = CustomRecyclerAdapter()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DayFragmentBinding.inflate(inflater,container,false)
  //      recyclerView = inflater.inflate(R.layout.day_fragment,container,false) as RecyclerView
        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = binding.recyclerView
        viewModel = ViewModelProvider(this).get(DayViewModel::class.java)
        adapter.setDayForecastData(viewModel.getDayForecast())
        viewModel.dayLiveData.observe(viewLifecycleOwner, Observer() { renderData(it) })
//        recyclerView.layoutManager= LinearLayoutManager(view.context)
        recyclerView.adapter=adapter

//        binding.recyclerView.adapter = adapter

    }

/*
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DayViewModel::class.java)
        viewModel.dayLiveData.observe(viewLifecycleOwner, Observer() { renderData(it) })
        viewModel.getDayForecast()

    }
*/


    private fun renderData(dayForecast: List<Weather>) {
        adapter.setDayForecastData(dayForecast)

    }

}
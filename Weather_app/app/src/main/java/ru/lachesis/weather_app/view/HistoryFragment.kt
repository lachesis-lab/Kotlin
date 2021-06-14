package ru.lachesis.weather_app.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import ru.lachesis.weather_app.app.AppState
import ru.lachesis.weather_app.databinding.HistoryFragmentBinding
import ru.lachesis.weather_app.utils.showSnakeBar
import ru.lachesis.weather_app.viewmodel.HistoryViewModel

class HistoryFragment : Fragment() {

    companion object {
        @JvmStatic
        fun newInstance() = HistoryFragment()
    }

    private val adapter: HistoryAdapter = HistoryAdapter()
    private var _binding: HistoryFragmentBinding? = null
    private val binding: HistoryFragmentBinding get() = _binding!!
    private val viewModel: HistoryViewModel by lazy { ViewModelProvider(this).get(HistoryViewModel::class.java) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = HistoryFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.historyLiveData.observe(viewLifecycleOwner, Observer { renderData(it) })
        val recyclerView = binding.historyRecyclerView
        recyclerView.adapter = adapter
        viewModel.getAllHistory()

    }

    private fun renderData(appState: AppState?) {
        when (appState) {
            is AppState.Success -> {
                binding.historyRecyclerView.visibility = View.VISIBLE
                binding.includedLoadingLayout.loadingLayout.visibility = View.GONE
                adapter.setHistoryData(appState.weather)
            }
            is AppState.Loading ->{
                binding.historyRecyclerView.visibility = View.GONE
                binding.includedLoadingLayout.loadingLayout.visibility = View.GONE
            }
            is AppState.Error ->{
                binding.historyRecyclerView.visibility = View.VISIBLE
                binding.includedLoadingLayout.loadingLayout.visibility = View.GONE
                binding.historyRecyclerView.showSnakeBar(
                    "Ошибка",
                    "Перегрузить",
                    {
                        viewModel.getAllHistory()
                    })
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }
}
package ru.lachesis.weather_app.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.lachesis.weather_app.R
import ru.lachesis.weather_app.model.Weather
import java.util.*


class DayRecyclerAdapter() :
    RecyclerView.Adapter<DayRecyclerAdapter.DayViewHolder>() {

    private var dayForecastData: List<Weather> = listOf()

    fun setDayForecastData(data: List<Weather>){
        dayForecastData = data
        notifyDataSetChanged()
    }



    inner class DayViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(weather: Weather) {
            itemView.findViewById<TextView>(R.id.hour).text = weather.getDateString()
            itemView.findViewById<TextView>(R.id.hour_temperature).text = String.format(Locale.getDefault(),"Температура:  ${weather.temperature}")
        }


        var time: TextView? = null
        var temp: TextView? = null

        init {
            time = itemView.findViewById<TextView>(R.id.hour)
            temp = itemView.findViewById<TextView>(R.id.hour_temperature)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.hour_item, parent, false)
        return DayViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: DayViewHolder, position: Int) {

        holder.bind(dayForecastData[position])

    }

    override fun getItemCount(): Int {
        return dayForecastData.size
    }
}
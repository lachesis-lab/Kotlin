package ru.lachesis.weather_app.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import ru.lachesis.weather_app.R
import ru.lachesis.weather_app.model.Weather
import java.util.*


class CustomRecyclerAdapter() :
    RecyclerView.Adapter<CustomRecyclerAdapter.CustomViewHolder>() {

    private var dayForecastData: List<Weather> = listOf()

    fun setDayForecastData(data: List<Weather>){
        dayForecastData = data
        notifyDataSetChanged()
    }



    inner class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(weather: Weather) {
            itemView.findViewById<TextView>(R.id.hour).text = weather.getDateString()
            itemView.findViewById<TextView>(R.id.hour_temperature).text = String.format(Locale.getDefault(),"Температура:  ${weather.temperature}")
/*
            itemView.setOnClickListener {
                Toast.makeText(
                    itemView.context,
                    weather.city.city,
                    Toast.LENGTH_LONG
                ).show()
            }
*/
        }


        var time: TextView? = null
        var temp: TextView? = null

        init {
            time = itemView.findViewById<TextView>(R.id.hour)
            temp = itemView.findViewById<TextView>(R.id.hour_temperature)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.hour_item, parent, false)
        return CustomViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {

        holder.bind(dayForecastData[position])

    }

    override fun getItemCount(): Int {
        return dayForecastData.size
    }
}
package ru.lachesis.weather_app.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.lachesis.weather_app.R
import ru.lachesis.weather_app.model.Weather

class CitySelectAdapter(private var onItemViewClickListener: CitySelectFragment.OnItemViewClickListener?) :
    RecyclerView.Adapter<CitySelectAdapter.CitySelectViewHolder>() {
    private var weatherData: List<Weather> = listOf()

    fun setWeatherData(data:List<Weather>) {
        weatherData = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CitySelectViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.city_item,parent,false)
        return CitySelectViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CitySelectViewHolder, position: Int) {
        return holder.bind(weatherData[position])
    }

    override fun getItemCount(): Int {
        return weatherData.size
    }

    fun removeListener() {
        onItemViewClickListener = null
    }

    inner class CitySelectViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(weather: Weather) {
            itemView.findViewById<TextView>(R.id.city_item_name).text = weather.city.city
            itemView.setOnClickListener {
                onItemViewClickListener?.click(weather)
            }
        }

        var city: TextView? = null

        init {
            city = itemView.findViewById<TextView>(R.id.city_item_name)
        }


    }
}


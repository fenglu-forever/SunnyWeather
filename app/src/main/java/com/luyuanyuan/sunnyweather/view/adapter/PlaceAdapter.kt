package com.luyuanyuan.sunnyweather.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.luyuanyuan.sunnyweather.R
import com.luyuanyuan.sunnyweather.model.entity.Place
import com.luyuanyuan.sunnyweather.util.startActivity
import com.luyuanyuan.sunnyweather.view.activity.WeatherActivity
import com.luyuanyuan.sunnyweather.view.fragment.PlaceFragment

class PlaceAdapter(private val placeFragment: PlaceFragment, private val placeList: List<Place>) :
    RecyclerView.Adapter<PlaceAdapter.ViewHolder>() {
    inner class ViewHolder(rootView: View) : RecyclerView.ViewHolder(rootView) {
        val placeItem = rootView.findViewById<View>(R.id.placeItem)
        val tvName: TextView = rootView.findViewById(R.id.placeName)
        val tvAddress: TextView = rootView.findViewById(R.id.placeAddress)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val rootView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_place, parent, false)
        val holder = ViewHolder(rootView)
        holder.placeItem.setOnClickListener {
            placeFragment.savePlace(placeList[holder.adapterPosition])
        }
        return holder
    }

    override fun getItemCount() = placeList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val place = placeList[position]
        holder.tvName.text = place.name
        holder.tvAddress.text = place.address
    }
}
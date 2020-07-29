package com.goodweather.android.ui.place

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.goodweather.android.R
import com.goodweather.android.logic.model.Place

class PlaceAdapter(private val fragment: Fragment, private val placeList: List<Place>)
    : RecyclerView.Adapter<PlaceAdapter.PlaceViewHolder>() {

    inner class PlaceViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val placeName: TextView = view.findViewById(R.id.placeName)
        val placeAddress: TextView = view.findViewById(R.id.placeAddress)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceViewHolder {
        val convertView = LayoutInflater.from(parent.context).inflate(R.layout.place_item, parent, false)
        return PlaceViewHolder(convertView)
    }

    override fun getItemCount() = placeList.size

    override fun onBindViewHolder(holder: PlaceViewHolder, position: Int) {
        val place = placeList[position]
        holder.placeName.text = place.name
        holder.placeAddress.text = place.address
    }
}
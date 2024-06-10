package com.example.projekt_zpam

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class Adapter(private val measurements: List<MeasurementData>) :
    RecyclerView.Adapter<Adapter.ViewHolder>() {

        class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val textViewValue: TextView = itemView.findViewById(R.id.textViewlevel)
        val textViewMode: TextView = itemView.findViewById(R.id.textViewMode)
        val textViewTimestamp: TextView = itemView.findViewById(R.id.textViewTimestamp)
}
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycle_item, parent, false)
        return ViewHolder(itemView)
}

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val measurement = measurements[position]
        holder.textViewValue.text = "Glucose level: ${measurement.level} mg/dl"
        holder.textViewMode.text = "Mode: ${measurement.type}"
        holder.textViewTimestamp.text = "Date and time: ${measurement.formattedDate}"
}

    override fun getItemCount(): Int {
        return measurements.size
    }
}


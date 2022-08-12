package com.mixdor.covertpots.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mixdor.covertpots.R
import com.mixdor.covertpots.model.mPlanta

class PlantasViewHolder(view:View) : RecyclerView.ViewHolder(view){

    val serial = view.findViewById<TextView>(R.id.cardSerialPlanta)
    val nombre = view.findViewById<TextView>(R.id.cardNombrePlanta)
    val foto = view.findViewById<ImageView>(R.id.cardFotoPlanta)

    fun render(planta:mPlanta, onClickListener:(mPlanta)->Unit){
        serial.text = planta.id.toString()
        nombre.text = planta.nombre

        itemView.setOnClickListener { onClickListener(planta) }
    }

}

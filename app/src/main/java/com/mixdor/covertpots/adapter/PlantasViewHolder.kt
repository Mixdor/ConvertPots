package com.mixdor.covertpots.adapter

import android.graphics.Color
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.mixdor.covertpots.R
import com.mixdor.covertpots.model.mPlanta

class PlantasViewHolder(view:View) : RecyclerView.ViewHolder(view){

    val serial = view.findViewById<TextView>(R.id.cardSerialPlanta)
    val nombre = view.findViewById<TextView>(R.id.cardNombrePlanta)
    val foto = view.findViewById<ImageView>(R.id.cardFotoPlanta)
    val card = view.findViewById<MaterialCardView>(R.id.mCardPlanta)

    fun render(planta:mPlanta, onClickListener:(mPlanta)->Unit, onClickLong:(mPlanta)->Boolean, selec:MutableList<mPlanta>){
        serial.text = planta.id.toString()
        nombre.text = planta.nombre

        if(planta.checked){
            card.strokeWidth = 4
        }
        else{
            card.strokeWidth = 0
        }

        itemView.setOnClickListener {

            if(selec.isNotEmpty()){
                if(!planta.checked){
                    card.strokeWidth = 4
                    planta.checked = true
                    selec.add(planta)
                }
                else{
                    card.strokeWidth = 0
                    planta.checked = false
                    selec.remove(planta)
                }
            }

            onClickListener(planta)
        }
        itemView.setOnLongClickListener {
            if(!planta.checked){
                card.strokeWidth = 4
                planta.checked = true
                selec.add(planta)
            }
            else{
                card.strokeWidth = 0
                planta.checked = false
                selec.remove(planta)
            }

            onClickLong(planta)
        }
    }

}

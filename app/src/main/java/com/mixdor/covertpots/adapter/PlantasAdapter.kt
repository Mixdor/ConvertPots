package com.mixdor.covertpots.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mixdor.covertpots.R
import com.mixdor.covertpots.model.mPlanta

class PlantasAdapter(val listaPlantas:List<mPlanta>, val onClickListener:(mPlanta)->Unit, val onClickLong:(mPlanta)->Boolean) : RecyclerView.Adapter<PlantasViewHolder>(){

    private var listaSeleccionados: MutableList<mPlanta> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlantasViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        return PlantasViewHolder(layoutInflater.inflate(R.layout.card_planta,parent,false))

    }

    override fun onBindViewHolder(holder: PlantasViewHolder, position: Int) {

        val item = listaPlantas[position]
        holder.render(item, onClickListener,onClickLong,listaSeleccionados)

    }

    override fun getItemCount(): Int {
        return listaPlantas.size
    }


}
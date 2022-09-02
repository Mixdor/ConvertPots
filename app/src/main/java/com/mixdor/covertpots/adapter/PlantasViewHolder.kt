package com.mixdor.covertpots.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.mixdor.covertpots.databinding.CardPlantaBinding
import com.mixdor.covertpots.model.mPlanta

class PlantasViewHolder(view:View) : RecyclerView.ViewHolder(view){

    val binding = CardPlantaBinding.bind(view)

    fun render(planta:mPlanta, onClickListener:(mPlanta)->Unit, onClickLong:(mPlanta)->Boolean, selec:MutableList<mPlanta>){

        binding.cardSerialPlanta.text = planta.id.toString()
        binding.cardNombrePlanta.text = planta.nombre

        if(planta.checked){
            binding.mCardPlanta.strokeWidth = 4
        }
        else{
            binding.mCardPlanta.strokeWidth = 0
        }

        itemView.setOnClickListener {

            if(selec.isNotEmpty()){
                if(!planta.checked){
                    binding.mCardPlanta.strokeWidth = 4
                    planta.checked = true
                    selec.add(planta)
                }
                else{
                    binding.mCardPlanta.strokeWidth = 0
                    planta.checked = false
                    selec.remove(planta)
                }
            }

            onClickListener(planta)
        }
        itemView.setOnLongClickListener {
            if(!planta.checked){
                binding.mCardPlanta.strokeWidth = 4
                planta.checked = true
                selec.add(planta)
            }
            else{
                binding.mCardPlanta.strokeWidth = 0
                planta.checked = false
                selec.remove(planta)
            }

            onClickLong(planta)
        }
    }

}

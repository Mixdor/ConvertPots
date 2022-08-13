package com.mixdor.covertpots

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.mixdor.covertpots.adapter.PlantasAdapter
import com.mixdor.covertpots.model.mPlanta

class ActMorePlants : AppCompatActivity() {

    /*private var adapterPlants: AdpPlants? = null*/
    private var btnEdit: FloatingActionButton? = null
    private var btnDelete: FloatingActionButton? = null
    private var selecionados:MutableList<mPlanta> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_CovertPots)
        setContentView(R.layout.act_more_plants)


        val btnAgregar = findViewById<FloatingActionButton>(R.id.fBtnPlantAgregar)
        btnEdit = findViewById(R.id.fBtnPlantEditar)
        btnDelete = findViewById(R.id.fBtnPlantEliminar)

        actualizarValores()

    }


    private fun actualizarValores() {
        val recyclerPlants = findViewById<RecyclerView>(R.id.recyclerViewPlants)
        recyclerPlants.layoutManager = LinearLayoutManager(this)
        recyclerPlants.adapter = PlantasAdapter(getItems(), { itemSelected(it) }, { itemLongSelected(it)})
    }

    private fun getItems(): MutableList<mPlanta> {
        val itemLists : MutableList<mPlanta> = ArrayList()

        val s1 = 23
        val s2 = 56
        val s3 = 84
        val s4 = 14

        itemLists.add(mPlanta(16523, "Kotralia", s1, s2, s3, s4,false))
        itemLists.add(mPlanta(44618, "Castorila", s1, s2, s3, s4, false))
        itemLists.add(mPlanta(15143, "Andesita", s1, s2, s3, s4, false))
        itemLists.add(mPlanta(96318, "Viperesa", s1, s2, s3, s4, false))
        itemLists.add(mPlanta(94664, "Kotralia2", s1, s2, s3, s4,false))
        itemLists.add(mPlanta(41151, "Castorila2", s1, s2, s3, s4, false))
        itemLists.add(mPlanta(33445, "Andesita2", s1, s2, s3, s4, false))
        itemLists.add(mPlanta(10024, "Viperesa2", s1, s2, s3, s4, false))
        itemLists.add(mPlanta(47620, "Kotralia3", s1, s2, s3, s4,false))
        itemLists.add(mPlanta(75141, "Castorila3", s1, s2, s3, s4, false))
        itemLists.add(mPlanta(11146, "Andesita3", s1, s2, s3, s4, false))
        itemLists.add(mPlanta(10101, "Viperesa3", s1, s2, s3, s4, false))
        itemLists.add(mPlanta(14691, "Viperesa4", s1, s2, s3, s4, false))

        return itemLists
    }

    fun itemSelected(planta:mPlanta){

        if (selecionados.isEmpty()){

            //Acá va la ventana para ver toda la info de la planta seleccionada, la hare despues XD
            //Pendiente

        }
        else{

            if(selecionados.contains(planta)){
                selecionados.remove(planta)
            }
            else{
                selecionados.add(planta)
            }

            if(selecionados.size>=1){
                btnDelete?.visibility = View.VISIBLE
                if(selecionados.size==1){
                    btnEdit?.visibility = View.VISIBLE
                }
                else{
                    btnEdit?.visibility = View.GONE
                }
            }
            else{
                btnDelete?.visibility = View.GONE
                btnEdit?.visibility = View.GONE
            }

        }

    }

    fun itemLongSelected(planta:mPlanta): Boolean {
        //Toast.makeText(this,planta.id.toString(),Toast.LENGTH_SHORT).show()

        if(selecionados.contains(planta)){
            selecionados.remove(planta)
        }
        else{
            selecionados.add(planta)
        }

        if(selecionados.size>=1){
            btnDelete?.visibility = View.VISIBLE
            if(selecionados.size==1){
                btnEdit?.visibility = View.VISIBLE
            }
            else{
                btnEdit?.visibility = View.GONE
            }
        }
        else{
            btnDelete?.visibility = View.GONE
            btnEdit?.visibility = View.GONE
        }



        return true
    }

    /*private fun mostrarMenuSeleccion() {
        btnDelete?.visibility = View.VISIBLE
        if (adapterPlants?.itemSelectCount!! > 1) {
            btnEdit?.visibility = View.GONE
        } else {
            btnEdit?.visibility = View.VISIBLE
        }
    }

    private fun ocultarMenuSeleccion() {
        btnEdit?.visibility = View.GONE
        btnDelete?.visibility = View.GONE
    }*/
    
}
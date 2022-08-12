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
        recyclerPlants.adapter = PlantasAdapter(getItems()) { itemSelected(it) }
    }

    private fun getItems(): MutableList<mPlanta> {
        val itemLists : MutableList<mPlanta> = ArrayList()

        val id = 16743
        val nombre = "Kotralia"
        val s1 = 23
        val s2 = 56
        val s3 = 84
        val s4 = 14

        itemLists.add(mPlanta(id, nombre, s1, s2, s3, s4))
        itemLists.add(mPlanta(id, nombre, s1, s2, s3, s4))
        itemLists.add(mPlanta(id, nombre, s1, s2, s3, s4))
        itemLists.add(mPlanta(id, nombre, s1, s2, s3, s4))

        return itemLists
    }

    fun itemSelected(planta:mPlanta){
        Toast.makeText(this,planta.nombre,Toast.LENGTH_SHORT).show()
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
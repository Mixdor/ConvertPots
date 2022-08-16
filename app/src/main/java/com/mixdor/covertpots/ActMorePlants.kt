package com.mixdor.covertpots

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.mixdor.covertpots.adapter.PlantasAdapter
import com.mixdor.covertpots.fragment.FragDetallesPlanta
import com.mixdor.covertpots.fragment.FragEditarPlanta
import com.mixdor.covertpots.fragment.FragNuevaPlanta
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

        btnAgregar.setOnClickListener {
            showFragmentNuevaPlanta()
        }

        btnEdit?.setOnClickListener {
            showFragmentEditarPlanta(selecionados[0])
        }

        btnDelete?.setOnClickListener {

            MaterialAlertDialogBuilder(this)
                .setTitle("Remover planta")
                .setMessage("Esa seguro de remover esta planta")
                .setNegativeButton("Cancelar") { dialog, which ->

                }
                .setPositiveButton("Aceptar") { dialog, which ->

                }
                .show()


        }

    }

    private fun actualizarValores() {
        val recyclerPlants = findViewById<RecyclerView>(R.id.recyclerViewPlants)
        recyclerPlants.layoutManager = LinearLayoutManager(this)
        recyclerPlants.adapter = PlantasAdapter(getItems(), { itemSelected(it) }, { itemLongSelected(it)})
    }

    private fun getItems(): MutableList<mPlanta> {
        val itemLists : MutableList<mPlanta> = ArrayList()

        itemLists.add(mPlanta(16523, "Kotralia", 44, 27, 87, 15,false))
        itemLists.add(mPlanta(44618, "Castorila", 55, 96, 44, 66, false))
        itemLists.add(mPlanta(15143, "Andesita", 87, 15, 66, 44, false))
        itemLists.add(mPlanta(96318, "Viperesa", 96, 44, 15, 13, false))
        itemLists.add(mPlanta(94664, "Kotralia2", 77, 27, 66, 37,false))
        itemLists.add(mPlanta(41151, "Castorila2", 15, 44, 55, 77, false))
        itemLists.add(mPlanta(33445, "Andesita2", 87, 77, 15, 44, false))
        itemLists.add(mPlanta(10024, "Viperesa2", 27, 96, 87, 96, false))
        itemLists.add(mPlanta(47620, "Kotralia3", 66, 55, 13, 44,false))
        itemLists.add(mPlanta(75141, "Castorila3", 96, 87, 44, 55, false))
        itemLists.add(mPlanta(11146, "Andesita3", 13, 66, 27, 87, false))
        itemLists.add(mPlanta(10101, "Viperesa3", 27, 44, 87, 66, false))
        itemLists.add(mPlanta(14691, "Viperesa4", 87, 55, 44, 15, false))

        return itemLists
    }

    fun itemSelected(planta:mPlanta){

        if (selecionados.isEmpty()){
            
            showFragmentDetalles(planta)

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

    fun showFragmentDetalles(planta:mPlanta) {
        val fragmentManager = supportFragmentManager
        val newFragment = FragDetallesPlanta(planta)
        val isLargeLayout = resources.getBoolean(R.bool.large_layout)

        if (isLargeLayout) {
            // The device is using a large layout, so show the fragment as a dialog
            newFragment.show(fragmentManager, "dialog")
        } else {
            // The device is smaller, so show the fragment fullscreen
            val transaction = fragmentManager.beginTransaction()
            // For a little polish, specify a transition animation
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            // To make it fullscreen, use the 'content' root view as the container
            // for the fragment, which is always the root view for the activity
            transaction
                .add(android.R.id.content, newFragment)
                .addToBackStack(null)
                .commit()
        }
    }

    private fun showFragmentEditarPlanta(planta: mPlanta) {
        val fragmentManager = supportFragmentManager
        val newFragment = FragEditarPlanta(planta)
        val isLargeLayout = resources.getBoolean(R.bool.large_layout)

        if (isLargeLayout) {
            // The device is using a large layout, so show the fragment as a dialog
            newFragment.show(fragmentManager, "dialog")
        } else {
            // The device is smaller, so show the fragment fullscreen
            val transaction = fragmentManager.beginTransaction()
            // For a little polish, specify a transition animation
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            // To make it fullscreen, use the 'content' root view as the container
            // for the fragment, which is always the root view for the activity
            transaction
                .add(android.R.id.content, newFragment)
                .addToBackStack(null)
                .commit()
        }
    }

    private fun showFragmentNuevaPlanta() {
        val fragmentManager = supportFragmentManager
        val newFragment = FragNuevaPlanta()
        val isLargeLayout = resources.getBoolean(R.bool.large_layout)

        if (isLargeLayout) {
            // The device is using a large layout, so show the fragment as a dialog
            newFragment.show(fragmentManager, "dialog")
        } else {
            // The device is smaller, so show the fragment fullscreen
            val transaction = fragmentManager.beginTransaction()
            // For a little polish, specify a transition animation
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            // To make it fullscreen, use the 'content' root view as the container
            // for the fragment, which is always the root view for the activity
            transaction
                .add(android.R.id.content, newFragment)
                .addToBackStack(null)
                .commit()
        }
    }
    
}
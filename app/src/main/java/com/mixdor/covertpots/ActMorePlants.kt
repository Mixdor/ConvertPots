package com.mixdor.covertpots

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.mixdor.covertpots.adapter.PlantasAdapter
import com.mixdor.covertpots.databinding.ActMorePlantsBinding
import com.mixdor.covertpots.fragment.FragDetallesPlanta
import com.mixdor.covertpots.fragment.FragEditarPlanta
import com.mixdor.covertpots.fragment.FragNuevaPlanta
import com.mixdor.covertpots.model.mPlanta

class ActMorePlants : AppCompatActivity() {

    private var selecionados:MutableList<mPlanta> = ArrayList()
    private lateinit var binding: ActMorePlantsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_CovertPots)

        binding = ActMorePlantsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        actualizarValores()

        binding.fBtnPlantAgregar.setOnClickListener {
            showFragmentNuevaPlanta()
        }

        binding.fBtnPlantEditar.setOnClickListener {
            showFragmentEditarPlanta(selecionados[0])
        }

        binding.fBtnPlantEliminar.setOnClickListener {

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
        val itemLists : MutableList<mPlanta> = ArrayList()

        val database = Firebase.database
        val user = Firebase.auth.currentUser
        val uid = user?.uid
        val refPath = database.getReference("users/${uid}")

        refPath.child("plants").get()
            .addOnSuccessListener { it1 ->

                val mapa = it1.value as Map<String, HashMap<String, Any>>
                val claves = mapa.keys

                for (clave in claves){
                    val variable: HashMap<String, Any>? = mapa[clave]
                    //Log.i("getItems",variable.toString())
                    val objName = variable?.get("name").toString()
                    val objTemp = variable?.get("sTemp").toString()
                    val objIlum = variable?.get("sIlum").toString()
                    val objHumS = variable?.get("sHumS").toString()
                    val objHumA = variable?.get("sHumA").toString()
                    val objType = variable?.get("sHumA").toString()
                    val objStatus = variable?.get("sHumA").toString()
                    val objFav = variable?.get("sHumA").toString().toBoolean()

                    itemLists.add(
                        mPlanta(Integer.valueOf(clave),
                            objName,
                            objTemp.toInt(),
                            objIlum.toInt(),
                            objHumS.toInt(),
                            objHumA.toInt(),
                            false
                        ))
                }
                val recyclerPlants = findViewById<RecyclerView>(R.id.recyclerViewPlants)
                recyclerPlants.layoutManager = LinearLayoutManager(this)
                recyclerPlants.adapter = PlantasAdapter(itemLists, { itemSelected(it) }, { itemLongSelected(it)})
                Log.i("getItemsInterno", itemLists.toString())
            }
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
                binding.fBtnPlantEliminar.visibility = View.VISIBLE
                if(selecionados.size==1){
                    binding.fBtnPlantEditar.visibility = View.VISIBLE
                }
                else{
                    binding.fBtnPlantEditar.visibility = View.GONE
                }
            }
            else{
                binding.fBtnPlantEliminar.visibility = View.GONE
                binding.fBtnPlantEditar.visibility = View.GONE
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
            binding.fBtnPlantEliminar.visibility = View.VISIBLE
            if(selecionados.size==1){
                binding.fBtnPlantEditar.visibility = View.VISIBLE
            }
            else{
                binding.fBtnPlantEditar.visibility = View.GONE
            }
        }
        else{
            binding.fBtnPlantEliminar.visibility = View.GONE
            binding.fBtnPlantEditar.visibility = View.GONE
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
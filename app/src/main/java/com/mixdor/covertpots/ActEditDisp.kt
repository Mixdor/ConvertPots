package com.mixdor.covertpots

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.mixdor.covertpots.databinding.ActEditDispBinding

class ActEditDisp : AppCompatActivity() {

    private lateinit var binding: ActEditDispBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_CovertPots)

        binding = ActEditDispBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val bundle: Bundle? = intent.extras
        val serial: String? = bundle?.getString("serial")
        val pass: String? = bundle?.getString("pass")
        
        var tipo = -1

        binding.EditEdNumSerie.setText(serial)

        val prefer : SharedPreferences = this.getSharedPreferences("Ajustes", Context.MODE_PRIVATE)

        val database = Firebase.database
        val user = Firebase.auth.currentUser
        val uid = user?.uid

        val ref = database.getReference("users/${uid}/plants/${prefer.getInt("fav",-1)}")

        if (prefer.getInt("fav",-1) != -1){

            ref.child("name").get().addOnSuccessListener {
                binding.EditEdNombrePlanta.setText(it.value as String)
            }
            ref.child("type").get().addOnSuccessListener {
                binding.AtoCompTipoPlanta.setSelection(Integer.valueOf(it.value.toString()))
            }
        }

        binding.EditEdPass.setText(pass)

        binding.swCambiar.setOnCheckedChangeListener { _, checked ->
            if(checked){
                binding.LayoutEdPassNew.visibility = View.VISIBLE
            }
            else{
                binding.LayoutEdPassNew.visibility = View.GONE
            }
        }

        binding.AtoCompTipoPlanta.setOnItemClickListener { _, _, position, _ ->
            tipo = position
            //println("OnItem = $position")
        }

        binding.btnHechoEdit.setOnClickListener{

            ref.child("name").setValue(binding.EditEdNombrePlanta.text.toString())
                .addOnFailureListener {
                    Log.e("Firebase",it.toString())
                }
            ref.child("type").setValue(tipo)
                .addOnFailureListener {
                    Log.e("Firebase",it.toString())
                }

            val editor: SharedPreferences.Editor = prefer.edit()
            editor.putInt("fav",Integer.valueOf(binding.EditEdNumSerie.text.toString()))
            editor.apply()

            gotoMAin(Integer.valueOf(binding.EditEdNumSerie.text.toString()))

        }
    }

    override fun onResume() {
        super.onResume()

        val autoTipo = findViewById<AutoCompleteTextView>(R.id.AtoCompTipoPlanta)

        val uniTipo = resources.getStringArray(R.array.uniTipoPlanta)
        val adapterTemp = ArrayAdapter(this@ActEditDisp,R.layout.dropdown_item,uniTipo)
        autoTipo.setAdapter(adapterTemp)

    }

    private fun gotoMAin(numero:Int){
        val intent = Intent(this@ActEditDisp,ActMain::class.java).apply {
            putExtra("serial",numero)
        }
        startActivity(intent)
        finishAffinity()
    }

}
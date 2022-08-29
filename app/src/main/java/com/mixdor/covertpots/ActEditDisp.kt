package com.mixdor.covertpots

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.mixdor.covertpots.databinding.ActEditDispBinding

class ActEditDisp : AppCompatActivity() {

    private val dbFireStore = FirebaseFirestore.getInstance()
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

        if (prefer.getInt("fav",-1) != -1){
            dbFireStore.collection("dispositivo").document(serial.toString()).get()
                .addOnSuccessListener {
                    binding.EditEdNombrePlanta.setText(it.get("nombre") as String)
                    binding.AtoCompTipoPlanta.setSelection(it.get("tipoPlant") as Int)
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

            if(binding.swCambiar.isChecked){
                //println(tipo)
                if(binding.EditEdNombrePlanta.text!!.isNotEmpty() && tipo != -1 &&
                    binding.EditEdPass.text!!.isNotEmpty() && binding.EditEdPassNew.text!!.isNotEmpty()){

                    dbFireStore.collection("dispositivos").document(binding.EditEdNumSerie.text.toString()).get()
                        .addOnSuccessListener{
                            if (it.get("pass") as String? == binding.EditEdPass.text.toString()){

                                dbFireStore.collection("dispositivos").document(binding.EditEdNumSerie.text.toString())
                                    .update(mapOf("nombre" to binding.EditEdNombrePlanta.text.toString(),
                                        "tipoPlant" to tipo,
                                        "pass" to binding.EditEdPassNew.text.toString()
                                    ))

                                val editor: SharedPreferences.Editor = prefer.edit()
                                editor.putInt("fav",Integer.valueOf(binding.EditEdNumSerie.text.toString()))
                                editor.apply()

                                gotoMAin(Integer.valueOf(binding.EditEdNumSerie.text.toString()))
                            }
                            else{
                                showAlert()
                            }
                        }
                }
                else{
                    Toast.makeText(this,getString(R.string.faltanDatos), Toast.LENGTH_SHORT)
                        .show()
                }

            }
            else{
                if(binding.EditEdNombrePlanta.text!!.isNotEmpty() && tipo != -1 &&
                    binding.EditEdPass.text!!.isNotEmpty()){

                    dbFireStore.collection("dispositivos").document(binding.EditEdNumSerie.text.toString()).get()
                        .addOnSuccessListener{
                            if (it.get("pass") as String? == binding.EditEdPass.text.toString()){

                                dbFireStore.collection("dispositivos").document(binding.EditEdNumSerie.text.toString())
                                    .update(mapOf("nombre" to binding.EditEdNombrePlanta.text.toString(),
                                        "tipoPlant" to tipo
                                    ))

                                val editor: SharedPreferences.Editor = prefer.edit()
                                editor.putInt("fav",Integer.valueOf(binding.EditEdNumSerie.text.toString()))
                                editor.apply()

                                gotoMAin(Integer.valueOf(binding.EditEdNumSerie.text.toString()))
                            }
                            else{
                                showAlert()
                            }
                        }
                }
                else{
                    Toast.makeText(this,getString(R.string.faltanDatos), Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()

        val autoTipo = findViewById<AutoCompleteTextView>(R.id.AtoCompTipoPlanta)

        val uniTipo = resources.getStringArray(R.array.uniTipoPlanta)
        val adapterTemp = ArrayAdapter(this@ActEditDisp,R.layout.dropdown_item,uniTipo)
        autoTipo.setAdapter(adapterTemp)

    }

    private fun showAlert(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.error))
        builder.setMessage(getString(R.string.dispNoEncontrado))
        builder.setPositiveButton(getString(R.string.aceptar),null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun gotoMAin(numero:Int){
        val intent = Intent(this@ActEditDisp,ActMain::class.java).apply {
            putExtra("serial",numero)
        }
        startActivity(intent)
        finishAffinity()
    }

}
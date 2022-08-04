package com.mixdor.covertpots

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.firestore.FirebaseFirestore

class ActEditDisp : AppCompatActivity() {

    private val dbFireStore = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_CovertPots)
        setContentView(R.layout.act_edit_disp)

        val bundle: Bundle? = intent.extras
        val serial: String? = bundle?.getString("serial")
        val pass: String? = bundle?.getString("pass")
        
        var tipo = -1;

        val txtSerial = findViewById<TextInputEditText>(R.id.EditEdNumSerie)
        val txtNamePlant = findViewById<TextInputEditText>(R.id.EditEdNombrePlanta)
        val txtTipoPlant = findViewById<AutoCompleteTextView>(R.id.AtoCompTipoPlanta)
        val txtpass = findViewById<TextInputEditText>(R.id.EditEdPass)
        val txtpassNew = findViewById<TextInputEditText>(R.id.EditEdPassNew)

        val btnHecho = findViewById<Button>(R.id.btnHechoEdit)
        val swCambiarPass = findViewById<Switch>(R.id.swCambiar)
        val tFCambiarPAss = findViewById<TextInputLayout>(R.id.LayoutEdPassNew)

        txtSerial.setText(serial)

        val prefer : SharedPreferences = this.getSharedPreferences("Ajustes", Context.MODE_PRIVATE)

        if (prefer.getInt("fav",-1) != -1){
            dbFireStore.collection("dispositivo").document(serial.toString()).get()
                .addOnSuccessListener {
                    txtNamePlant.setText(it.get("nombre") as String)
                    txtTipoPlant.setSelection(it.get("tipoPlant") as Int)
                }
        }

        txtpass.setText(pass)

        swCambiarPass.setOnCheckedChangeListener { _, checked ->
            if(checked){
                tFCambiarPAss.visibility = View.VISIBLE
            }
            else{
                tFCambiarPAss.visibility = View.GONE
            }
        }

        txtTipoPlant.setOnItemClickListener { _, _, position, _ ->
            tipo = position
            //println("OnItem = $position")
        }

        btnHecho.setOnClickListener{

            if(swCambiarPass.isChecked){
                //println(tipo)
                if(txtNamePlant.text!!.isNotEmpty() && tipo != -1 &&
                    txtpass.text!!.isNotEmpty() && txtpassNew.text!!.isNotEmpty()){

                    dbFireStore.collection("dispositivos").document(txtSerial.text.toString()).get()
                        .addOnSuccessListener{
                            if (it.get("pass") as String? == txtpass.text.toString()){

                                dbFireStore.collection("dispositivos").document(txtSerial.text.toString())
                                    .update(mapOf("nombre" to txtNamePlant.text.toString(),
                                        "tipoPlant" to tipo,
                                        "pass" to txtpassNew.text.toString()
                                    ))

                                val editor: SharedPreferences.Editor = prefer.edit()
                                editor.putInt("fav",Integer.valueOf(txtSerial.text.toString()))
                                editor.apply()

                                gotoMAin(Integer.valueOf(txtSerial.text.toString()))
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
                if(txtNamePlant.text!!.isNotEmpty() && tipo != -1 &&
                    txtpass.text!!.isNotEmpty()){

                    dbFireStore.collection("dispositivos").document(txtSerial.text.toString()).get()
                        .addOnSuccessListener{
                            if (it.get("pass") as String? == txtpass.text.toString()){

                                dbFireStore.collection("dispositivos").document(txtSerial.text.toString())
                                    .update(mapOf("nombre" to txtNamePlant.text.toString(),
                                        "tipoPlant" to tipo
                                    ))

                                val editor: SharedPreferences.Editor = prefer.edit()
                                editor.putInt("fav",Integer.valueOf(txtSerial.text.toString()))
                                editor.apply()

                                gotoMAin(Integer.valueOf(txtSerial.text.toString()))
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
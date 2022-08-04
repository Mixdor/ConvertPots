package com.mixdor.covertpots

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.firestore.FirebaseFirestore

class ActVincular : AppCompatActivity() {

    private val dbFireStore = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_CovertPots)
        setContentView(R.layout.act_vincular)

        val bundle: Bundle? = intent.extras
        val correo: String? = bundle?.getString("correo")

        val btnVincular = findViewById<Button>(R.id.btnVincular)
        val editSerial = findViewById<TextInputEditText>(R.id.EditNumSerie)
        val editPass = findViewById<TextInputEditText>(R.id.EditVincularPass)

        btnVincular.setOnClickListener{

            if(editSerial.text!!.isNotEmpty() && editPass.text!!.isNotEmpty()){

                dbFireStore.collection("dispositivos").document(editSerial.text.toString()).get()
                    .addOnSuccessListener {
                        if (it.get("pass") as String? == editPass.text.toString()){

                            dbFireStore.collection("usuarios").document(correo.toString()).set(
                                mapOf(
                                    "fav" to Integer.valueOf(editSerial.text.toString()),
                                    "plantas" to mapOf(editSerial.text.toString() to editSerial.text.toString())
                                )
                            )

                            val intent = Intent(this@ActVincular,ActEditDisp::class.java).apply {
                                putExtra("serial",editSerial.text.toString())
                                putExtra("pass",editPass.text.toString())
                            }
                            startActivity(intent)

                        }
                        else{
                            showAlert()
                        }
                    }

            }
            else{
                Toast.makeText(this,getString(R.string.faltanDatosLogin), Toast.LENGTH_SHORT)
                    .show()
            }

        }

    }

    private fun showAlert(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.error))
        builder.setMessage(getString(R.string.dispNoEncontrado))
        builder.setPositiveButton(getString(R.string.aceptar),null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

}
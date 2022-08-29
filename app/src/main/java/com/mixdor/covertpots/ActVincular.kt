package com.mixdor.covertpots

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.mixdor.covertpots.databinding.ActVincularBinding

class ActVincular : AppCompatActivity() {

    private val dbFireStore = FirebaseFirestore.getInstance()
    private lateinit var binding: ActVincularBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_CovertPots)
        
        binding = ActVincularBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val bundle: Bundle? = intent.extras
        val correo: String? = bundle?.getString("correo")

        binding.btnVincular.setOnClickListener{

            if(binding.EditNumSerie.text!!.isNotEmpty() && binding.EditVincularPass.text!!.isNotEmpty()){

                dbFireStore.collection("dispositivos").document(binding.EditNumSerie.text.toString()).get()
                    .addOnSuccessListener {
                        if (it.get("pass") as String? == binding.EditVincularPass.text.toString()){

                            dbFireStore.collection("usuarios").document(correo.toString()).set(
                                mapOf(
                                    "fav" to Integer.valueOf(binding.EditNumSerie.text.toString()),
                                    "plantas" to mapOf(binding.EditNumSerie.text.toString() to binding.EditNumSerie.text.toString())
                                )
                            )

                            val intent = Intent(this@ActVincular,ActEditDisp::class.java).apply {
                                putExtra("serial",binding.EditNumSerie.text.toString())
                                putExtra("pass",binding.EditVincularPass.text.toString())
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
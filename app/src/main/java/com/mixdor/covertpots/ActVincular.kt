package com.mixdor.covertpots

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.mixdor.covertpots.databinding.ActVincularBinding

class ActVincular : AppCompatActivity() {

    private lateinit var binding: ActVincularBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_CovertPots)
        
        binding = ActVincularBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val database = Firebase.database
        val user = Firebase.auth.currentUser
        val uid = user?.uid

        val refPath = database.getReference("users/${uid}")

        binding.btnVincular.setOnClickListener{

            if(binding.EditNumSerie.text!!.isNotEmpty() && binding.EditVincularPass.text!!.isNotEmpty()){

                database.getReference("deviceSync/${binding.EditNumSerie.text.toString()}/passDefault")
                    .get().addOnSuccessListener {
                        Log.i("firebase", "Got value ${it.value}")
                        if(it.value as String == binding.EditVincularPass.text.toString()){

                            refPath.child("fav")
                                .setValue(Integer.valueOf(binding.EditNumSerie.text.toString()))

                            val prefer : SharedPreferences = this.getSharedPreferences("Ajustes", Context.MODE_PRIVATE)
                            prefer.edit()
                                .putInt("fav",Integer.valueOf(binding.EditNumSerie.text.toString()))
                                .apply()

                            val refPlant = refPath.child("plants/${binding.EditNumSerie.text.toString()}")
                            refPlant.child("fav")
                                .setValue(true)
                            refPlant.child("name")
                                .setValue("")
                            refPlant.child("sHumA")
                                .setValue(0)
                            refPlant.child("sHumS")
                                .setValue(0)
                            refPlant.child("sIlum")
                                .setValue(0)
                            refPlant.child("sTemp")
                                .setValue(0)
                            refPlant.child("status")
                                .setValue(0)
                            refPlant.child("type")
                                .setValue(0)

                            val intent = Intent(this@ActVincular,ActEditDisp::class.java).apply {
                                putExtra("serial",binding.EditNumSerie.text.toString())
                                putExtra("pass",binding.EditVincularPass.text.toString())
                            }
                            startActivity(intent)

                        }
                        else{
                            showAlert()
                        }
                    }.addOnFailureListener{
                        Log.e("firebase", "Error getting data", it)
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

    private fun gotoMAin(){
        val intent = Intent(this@ActVincular,ActMain::class.java).apply {
            putExtra("test","0")
        }
        startActivity(intent)
        finishAffinity()
    }

}
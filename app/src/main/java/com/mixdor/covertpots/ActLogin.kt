package com.mixdor.covertpots

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.mixdor.covertpots.databinding.ActLoginBinding

class ActLogin : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()
    private lateinit var binding: ActLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_CovertPots)

        binding = ActLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val bundle: Bundle? = intent.extras
        val correo: String? = bundle?.getString("correo")

        binding.EditLoginCorreo.setText(correo)

        binding.btnIniciarSesion.setOnClickListener{

            if(binding.EditLoginCorreo.text!!.isNotEmpty() && binding.EditLoginPass.text!!.isNotEmpty()){

                if(validarMail(binding.EditLoginCorreo.text.toString(),binding.LayoutCorreoLogin)){

                    FirebaseAuth.getInstance()
                        .signInWithEmailAndPassword(
                            binding.EditLoginCorreo.text.toString(),
                            binding.EditLoginPass.text.toString())
                        .addOnCompleteListener {
                            if(it.isSuccessful){

                                val userFire: FirebaseUser? = Firebase.auth.currentUser

                                if(userFire?.isEmailVerified == true){

                                    db.collection("usuarios").document(binding.EditLoginCorreo.text.toString())

                                    val prefer : SharedPreferences = this.getSharedPreferences("Ajustes", Context.MODE_PRIVATE)
                                    val editor: SharedPreferences.Editor = prefer.edit()
                                    editor.putString("correo",binding.EditLoginCorreo.text.toString())
                                    editor.apply()

                                    goToVincular(binding.EditLoginCorreo.text.toString())
                                }
                                else{
                                    Toast.makeText(this,getString(R.string.faltaVerifCorreo),Toast.LENGTH_SHORT)
                                        .show()
                                }
                            }
                            else{
                                showAlert()
                            }
                        }
                }
            }
            else{
                Toast.makeText(this,getString(R.string.faltanDatosLogin),Toast.LENGTH_SHORT)
                    .show()
            }

        }

        binding.EditLoginCorreo.addTextChangedListener ( object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                binding.LayoutCorreoLogin.error = ""
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        }
        )

        binding.tVOlvidoPass.setOnClickListener{

            if(binding.EditLoginCorreo.text!!.isNotEmpty()){
                val emailAddress = binding.EditLoginCorreo.text.toString()

                Firebase.auth.sendPasswordResetEmail(emailAddress)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Log.d(TAG, "Email sent.")
                        }
                    }
            }

        }

    }

    private fun validarMail(mail: String, layoutMail: TextInputLayout):Boolean {

        val emailPatron = "^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}\$".toRegex()
        val valido = mail.trim().matches(emailPatron)

        if(!valido){
            layoutMail.error = getString(R.string.correoInvalido)
        }
        else{
            layoutMail.error = ""
        }
        return valido
    }

    private fun showAlert(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.error))
        builder.setMessage(getString(R.string.correoInvalidoMsg))
        builder.setPositiveButton(getString(R.string.aceptar),null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun goToVincular(txtCorreo:String){
        val intent = Intent(this@ActLogin, ActVincular::class.java).apply {
            putExtra("correo",txtCorreo)
        }
        startActivity(intent)
        finishAffinity()
    }

}
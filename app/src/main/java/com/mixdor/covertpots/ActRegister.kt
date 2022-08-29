package com.mixdor.covertpots

 import android.content.ContentValues.TAG
 import android.content.Intent
 import android.os.Bundle
 import android.text.Editable
 import android.text.TextWatcher
 import android.util.Log
 import android.widget.Toast
 import androidx.appcompat.app.AlertDialog
 import androidx.appcompat.app.AppCompatActivity
 import com.google.android.material.textfield.TextInputLayout
 import com.google.firebase.auth.FirebaseAuth
 import com.google.firebase.auth.ktx.auth
 import com.google.firebase.ktx.Firebase
 import com.mixdor.covertpots.databinding.ActRegisterBinding


class ActRegister : AppCompatActivity() {

    private val myAuth = FirebaseAuth.getInstance()
    private lateinit var binding: ActRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_CovertPots)
        
        binding = ActRegisterBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.btnRegistrarse.setOnClickListener {
            if(binding.EditCorreoE.text!!.isNotEmpty() && binding.EditRegisterPass.text!!.isNotEmpty()){

                if(validarMail(binding.EditCorreoE.text.toString(),binding.LayoutCorreoE)){

                    myAuth.setLanguageCode("es")

                    myAuth
                        .createUserWithEmailAndPassword(
                            binding.EditCorreoE.text.toString(),
                            binding.EditRegisterPass.text.toString())
                        .addOnCompleteListener {
                            if(it.isSuccessful){

                                val userfire = Firebase.auth.currentUser
                                userfire!!.sendEmailVerification()
                                    .addOnCompleteListener { task ->
                                        if (task.isSuccessful) {
                                            Log.d(TAG, "Email sent.")
                                        }
                                    }

                                goToLogin(binding.EditCorreoE.text.toString())

                            }
                            else{
                                showAlert()
                            }
                        }
                }

            }
            else{
                Toast.makeText(this,getString(R.string.faltanDatosRegist),Toast.LENGTH_SHORT)
                    .show()
            }
        }

        binding.EditCorreoE.addTextChangedListener ( object : TextWatcher{
                override fun afterTextChanged(s: Editable?) {
                    binding.LayoutCorreoE.error = ""
                }
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            }
        )

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

    private fun goToLogin(txtCorreo:String){
        val intent = Intent(this@ActRegister, ActLogin::class.java).apply {
            putExtra("correo",txtCorreo)
        }
        startActivity(intent)
    }
}
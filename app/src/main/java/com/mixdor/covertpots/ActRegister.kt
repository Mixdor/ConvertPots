package com.mixdor.covertpots

 import android.content.ContentValues.TAG
 import android.content.Intent
 import android.os.Bundle
 import android.text.Editable
 import android.text.TextWatcher
 import android.util.Log
 import android.widget.Button
 import android.widget.Toast
 import androidx.appcompat.app.AlertDialog
 import androidx.appcompat.app.AppCompatActivity
 import com.google.android.gms.tasks.OnCompleteListener
 import com.google.android.material.textfield.TextInputEditText
 import com.google.android.material.textfield.TextInputLayout
 import com.google.firebase.auth.FirebaseAuth
 import com.google.firebase.auth.FirebaseUser
 import com.google.firebase.auth.ktx.auth
 import com.google.firebase.ktx.Firebase


class ActRegister : AppCompatActivity() {

    private val myAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_CovertPots)
        setContentView(R.layout.act_register)

        val btnRegister : Button = findViewById(R.id.btnRegistrarse)
        val editEmail : TextInputEditText = findViewById(R.id.EditCorreoE)
        val layoutEmail : TextInputLayout = findViewById(R.id.LayoutCorreoE)
        val editPass : TextInputEditText = findViewById(R.id.EditRegisterPass)

        btnRegister.setOnClickListener {
            if(editEmail.text!!.isNotEmpty() && editPass.text!!.isNotEmpty()){

                if(validarMail(editEmail.text.toString(),layoutEmail)){

                    myAuth.setLanguageCode("es")


                    myAuth
                        .createUserWithEmailAndPassword(
                            editEmail.text.toString(),
                            editPass.text.toString())
                        .addOnCompleteListener {
                            if(it.isSuccessful){

                                val userfire = Firebase.auth.currentUser
                                userfire!!.sendEmailVerification()
                                    .addOnCompleteListener { task ->
                                        if (task.isSuccessful) {
                                            Log.d(TAG, "Email sent.")
                                        }
                                    }

                                goToLogin(editEmail.text.toString())

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



        editEmail.addTextChangedListener ( object : TextWatcher{
                override fun afterTextChanged(s: Editable?) {
                    layoutEmail.error = ""
                }
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            }
        )

    }

    private fun validarMail(mail: String, layoutMail: TextInputLayout):Boolean {

        val emailPatron = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}\$".toRegex()
        val valido = mail.trim().matches(emailPatron)

        if(!valido){
            layoutMail.error = getString(R.string.correoInvalido)
        }
        else{
            layoutMail.error = ""
        }
        return valido;
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
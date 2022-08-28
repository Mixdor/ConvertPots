package com.mixdor.covertpots

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ActAjustes : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_CovertPots)
        setContentView(R.layout.act_ajustes)

        val prefer : SharedPreferences = this.getSharedPreferences("Ajustes", Context.MODE_PRIVATE)
        val myCorreo = prefer.getString("correo",getString(R.string.correoDefault))

        val textCorreo = findViewById<TextView>(R.id.tVCorreoCuenta)
        val btnLogout = findViewById<Button>(R.id.btnLogout)
        val btnAbout = findViewById<Button>(R.id.btnInfo)

        textCorreo.text = myCorreo

        btnAbout.setOnClickListener {
            val intent = Intent(this@ActAjustes, ActAbout::class.java)
            startActivity(intent)
        }

        btnLogout.setOnClickListener {

            //Limpia las preferencias
            val editor: SharedPreferences.Editor = prefer.edit()
            editor.clear().apply()

            //Cierra sesion
            Firebase.auth.signOut()

            val intent = Intent(this@ActAjustes,ActInicio::class.java)
            startActivity(intent)
            finishAffinity()

        }
    }

    override fun onResume() {
        super.onResume()

        val autoTemp = findViewById<AutoCompleteTextView>(R.id.AutoComplTemperatura)

        val uniTemp = resources.getStringArray(R.array.uniTemperatura)
        val adapterTemp = ArrayAdapter(this,R.layout.dropdown_item,uniTemp)
        autoTemp.setAdapter(adapterTemp)

        val prefer : SharedPreferences = this.getSharedPreferences("Ajustes", Context.MODE_PRIVATE)
        val myUnitempe = prefer.getInt("temp",0)
        autoTemp.setText(autoTemp.adapter.getItem(myUnitempe).toString(),false)

        autoTemp.setOnItemClickListener{ _, _, position, _ ->
            val editor: SharedPreferences.Editor = prefer.edit()
            editor.putInt("temp",position)
            editor.apply()
        }

    }
}
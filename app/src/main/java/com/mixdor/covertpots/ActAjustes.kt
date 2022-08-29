package com.mixdor.covertpots

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mixdor.covertpots.databinding.ActAjustesBinding

class ActAjustes : AppCompatActivity() {

    private lateinit var binding: ActAjustesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_CovertPots)

        binding = ActAjustesBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val prefer : SharedPreferences = this.getSharedPreferences("Ajustes", Context.MODE_PRIVATE)
        val myCorreo = prefer.getString("correo",getString(R.string.correoDefault))

        binding.tVCorreoCuenta.text = myCorreo

        binding.btnInfo.setOnClickListener {
            val intent = Intent(this@ActAjustes, ActAbout::class.java)
            startActivity(intent)
        }

        binding.btnLogout.setOnClickListener {

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

        val uniTemp = resources.getStringArray(R.array.uniTemperatura)
        val adapterTemp = ArrayAdapter(this,R.layout.dropdown_item,uniTemp)
        binding.AutoComplTemperatura.setAdapter(adapterTemp)

        val prefer : SharedPreferences = this.getSharedPreferences("Ajustes", Context.MODE_PRIVATE)
        val myUnitempe = prefer.getInt("temp",0)
        binding.AutoComplTemperatura.setText(binding.AutoComplTemperatura.adapter.getItem(myUnitempe).toString(),false)

        binding.AutoComplTemperatura.setOnItemClickListener{ _, _, position, _ ->
            val editor: SharedPreferences.Editor = prefer.edit()
            editor.putInt("temp",position)
            editor.apply()
        }

    }
}
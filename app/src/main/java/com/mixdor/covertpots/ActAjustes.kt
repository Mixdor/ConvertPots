package com.mixdor.covertpots

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*

class ActAjustes : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_CovertPots)
        setContentView(R.layout.act_ajustes)

        val prefer : SharedPreferences = this.getSharedPreferences("Ajustes", Context.MODE_PRIVATE)
        val myCorreo = prefer.getString("correo",getString(R.string.correoDefault))

        val btnFacebook = findViewById<ImageButton>(R.id.btnFace)
        val btnTwitter = findViewById<ImageButton>(R.id.btnTwitter)
        val btnInsta = findViewById<ImageButton>(R.id.btnInstagram)
        val btnYoutube = findViewById<ImageButton>(R.id.btnYoutube)
        val btnFigma = findViewById<ImageButton>(R.id.btnFigma)
        val bntGithub = findViewById<ImageButton>(R.id.btnGithub)


        val textCorreo = findViewById<TextView>(R.id.tVCorreoCuenta)
        val btnLogout = findViewById<Button>(R.id.btnLogout)

        textCorreo.text = myCorreo

        btnFacebook.setOnClickListener{
            Toast.makeText(this,"Facebook", Toast.LENGTH_SHORT).show()
        }

        btnTwitter.setOnClickListener{
            Toast.makeText(this,"Twitter", Toast.LENGTH_SHORT).show()
        }

        btnInsta.setOnClickListener{
            Toast.makeText(this,"Instagram", Toast.LENGTH_SHORT).show()
        }

        btnYoutube.setOnClickListener{
            Toast.makeText(this,"YouTube", Toast.LENGTH_SHORT).show()
        }

        btnFigma.setOnClickListener{
            Toast.makeText(this,"Figma", Toast.LENGTH_SHORT).show()
        }

        bntGithub.setOnClickListener{
            Toast.makeText(this,"GitHub", Toast.LENGTH_SHORT).show()
        }

        btnLogout.setOnClickListener {

            val editor: SharedPreferences.Editor = prefer.edit()
            editor.clear().apply()

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
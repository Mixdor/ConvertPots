package com.mixdor.covertpots

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.firebase.analytics.FirebaseAnalytics

class ActInicio : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_CovertPots)
        setContentView(R.layout.act_inicio)

        val prefer : SharedPreferences = this.getSharedPreferences("Ajustes", Context.MODE_PRIVATE)
        if(prefer.getString("correo","")!=""){
            if(prefer.getInt("fav",-1)!=-1){
                val intent = Intent(this@ActInicio, ActMain::class.java)
                startActivity(intent)
                finishAffinity()
            }
            else{
                val intent = Intent(this@ActInicio, ActVincular::class.java)
                startActivity(intent)
                finishAffinity()
            }

        }

        //Evento analytics
        val analytics : FirebaseAnalytics = FirebaseAnalytics.getInstance(this)
        val bundle = Bundle()
        bundle.putString("mensaje","Integracion con firebase desde android completa")
        analytics.logEvent("InitScreen",bundle)


        val btnRegister = findViewById<Button>(R.id.btnRegister)
        val btnLogin = findViewById<Button>(R.id.btnLogin)

        btnRegister.setOnClickListener {
            val intent = Intent(this@ActInicio, ActRegister::class.java)
            startActivity(intent)
        }

        btnLogin.setOnClickListener {
            val intent = Intent(this@ActInicio, ActLogin::class.java)
            startActivity(intent)
        }

    }
}
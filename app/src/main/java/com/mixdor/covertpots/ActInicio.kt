package com.mixdor.covertpots

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.analytics.FirebaseAnalytics
import com.mixdor.covertpots.databinding.ActInicioBinding

class ActInicio : AppCompatActivity() {

    private lateinit var binding: ActInicioBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_CovertPots)

        binding = ActInicioBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

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

        binding.btnRegister.setOnClickListener {
            val intent = Intent(this@ActInicio, ActRegister::class.java)
            startActivity(intent)
        }

        binding.btnLogin.setOnClickListener {
            val intent = Intent(this@ActInicio, ActLogin::class.java)
            startActivity(intent)
        }

    }
}
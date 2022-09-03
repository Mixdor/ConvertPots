package com.mixdor.covertpots

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
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
        if(prefer.getInt("fav",-1)!=-1){
            Log.i("Entro","Por preferncias")
            val intent = Intent(this@ActInicio, ActMain::class.java)
            startActivity(intent)
            finishAffinity()
        }
        else{
            val database = Firebase.database
            val user = Firebase.auth.currentUser
            val uid = user?.uid
            val refPath = database.getReference("users/${uid}")

            refPath.child("fav").get()
                .addOnSuccessListener {
                    if(it.exists()){
                        Log.i("Entro","Por realtime")
                        val intent = Intent(this@ActInicio, ActMain::class.java)
                        startActivity(intent)
                        finishAffinity()
                    }
                    else{
                        Log.i("Entro","Por Descarte")
                        val intent = Intent(this@ActInicio, ActVincular::class.java)
                        startActivity(intent)
                        finishAffinity()
                    }
                }
                .addOnFailureListener {
                    Log.e("Realtime DB",it.toString())
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
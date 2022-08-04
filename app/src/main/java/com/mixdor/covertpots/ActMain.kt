package com.mixdor.covertpots

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.core.content.ContextCompat
import com.google.firebase.firestore.FirebaseFirestore

class ActMain : AppCompatActivity() {

    private val dbFireStore = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_CovertPots)
        setContentView(R.layout.act_main)

        //val bundle: Bundle? = intent.extras
        //val serial: Int? = bundle?.getInt("serial")


        val btnMore = findViewById<ImageButton>(R.id.btn_more_plants)
        val btnConfig = findViewById<ImageButton>(R.id.btn_cofig)
        val btnRegar = findViewById<Button>(R.id.btnRegar)
        val btnFoto = findViewById<ImageButton>(R.id.btnFoto)
        val fotoPlant = findViewById<ImageView>(R.id.iViewPlantaGr)

        val textIlum = findViewById<TextView>(R.id.tVIlum)
        val textHumdS = findViewById<TextView>(R.id.tVHumedad)
        val textHumdA = findViewById<TextView>(R.id.tVHumAire)
        val textTemp = findViewById<TextView>(R.id.tVTemperatura)


        val prefer : SharedPreferences = this.getSharedPreferences("Ajustes", Context.MODE_PRIVATE)
        var fav : Int = -1
        if(prefer.getInt("fav",-1)!=-1){
            fav = prefer.getInt("fav",-1);
        }
        else{
            dbFireStore.collection("usuarios").document(prefer.getString("correo","").toString()).get()
                .addOnSuccessListener {
                    fav = it.get("fav") as Int
                }
        }

        dbFireStore.collection("dispositivos").document(fav.toString()).get()
            .addOnSuccessListener{
                textIlum.text = "${it.get("sIlumi").toString()} lux"
                textHumdS.text = it.get("sHumedadAir").toString()+"%"
                textHumdA.text = it.get("sHumedadSuelo").toString()+"%"
                textTemp.text = it.get("sTemp").toString()+"°"
            }


        btnMore.setOnClickListener{
            Toast.makeText(this,"Más Plantas",Toast.LENGTH_SHORT).show()
        }

        btnConfig.setOnClickListener{
            //Toast.makeText(this,"Configuracion",Toast.LENGTH_SHORT).show()
            val intent = Intent(this@ActMain,ActAjustes::class.java)
            startActivity(intent)
        }

        btnRegar.setOnClickListener{
            Toast.makeText(this,"Regando",Toast.LENGTH_SHORT).show()
        }

        btnFoto.setOnClickListener{
            Toast.makeText(this,"Foto",Toast.LENGTH_SHORT).show()
        }

        //Prueba de color
        fotoPlant.setOnClickListener{
            fotoPlant.setColorFilter(ContextCompat.getColor(this, R.color.statusAmarillo), android.graphics.PorterDuff.Mode.SRC_IN)
        }
    }
}
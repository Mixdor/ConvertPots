package com.mixdor.covertpots

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.ekn.gruzer.gaugelibrary.ArcGauge
import com.ekn.gruzer.gaugelibrary.Range
import com.ekn.gruzer.gaugelibrary.contract.ValueFormatter
import com.google.firebase.firestore.FirebaseFirestore

class ActMain : AppCompatActivity() {

    private val dbFireStore = FirebaseFirestore.getInstance()

    @SuppressLint("ResourceType")
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

        val gaugeHumSuelo = findViewById<ArcGauge>(R.id.halfGauge)
        val gaugeHumAire = findViewById<ArcGauge>(R.id.halfGauge2)
        val gaugeIlum = findViewById<ArcGauge>(R.id.halfGauge3)
        val gaugeTemp = findViewById<ArcGauge>(R.id.halfGauge4)

        val rangeHS1 = Range(); val rangeHS2 = Range(); val rangeHS3 = Range()
        rangeHS1.color = Color.parseColor("#7D7AF5"); rangeHS1.from = 0.0; rangeHS1.to = 45.0
        rangeHS2.color = Color.parseColor("#4AA632"); rangeHS2.from = 45.0; rangeHS2.to = 80.0
        rangeHS3.color = Color.parseColor("#F56B22"); rangeHS3.from = 80.0; rangeHS3.to = 100.0

        //add color ranges to gauge
        gaugeHumSuelo.addRange(rangeHS1)
        gaugeHumSuelo.addRange(rangeHS2)
        gaugeHumSuelo.addRange(rangeHS3)

        //set min max
        gaugeHumSuelo.minValue = 0.0
        gaugeHumSuelo.maxValue = 100.0

        gaugeHumSuelo.setFormatter(ValueFormatter {
            it.toInt().toString()+"%"
        })

        gaugeHumSuelo.setValueColor(ContextCompat.getColor(this, R.color.colorTexto))

        /////

        val rangeHA1 = Range(); val rangeHA2 = Range(); val rangeHA3 = Range()
        rangeHA1.color = Color.parseColor("#7D7AF5"); rangeHA1.from = 0.0; rangeHA1.to = 45.0
        rangeHA2.color = Color.parseColor("#4AA632"); rangeHA2.from = 45.0; rangeHA2.to = 80.0
        rangeHA3.color = Color.parseColor("#F56B22"); rangeHA3.from = 80.0; rangeHA3.to = 100.0

        //add color ranges to gauge
        gaugeHumAire.addRange(rangeHA1)
        gaugeHumAire.addRange(rangeHA2)
        gaugeHumAire.addRange(rangeHA3)

        //set min max
        gaugeHumAire.minValue = 0.0
        gaugeHumAire.maxValue = 100.0

        gaugeHumAire.setFormatter(ValueFormatter {
            it.toInt().toString()+"%"
        })

        gaugeHumAire.setValueColor(ContextCompat.getColor(this, R.color.colorTexto))

        ////////////

        val rangeI1 = Range(); val rangeI2 = Range(); val rangeI3 = Range()
        rangeI1.color = Color.parseColor("#7D7AF5"); rangeI1.from = 0.0; rangeI1.to = 45.0
        rangeI2.color = Color.parseColor("#4AA632"); rangeI2.from = 45.0; rangeI2.to = 80.0
        rangeI3.color = Color.parseColor("#F56B22"); rangeI3.from = 80.0; rangeI3.to = 100.0

        //add color ranges to gauge
        gaugeIlum.addRange(rangeI1)
        gaugeIlum.addRange(rangeI2)
        gaugeIlum.addRange(rangeI3)

        //set min max
        gaugeIlum.minValue = 0.0
        gaugeIlum.maxValue = 100.0

        gaugeIlum.setFormatter(ValueFormatter {
            it.toInt().toString()+" lux"
        })

        gaugeIlum.setValueColor(ContextCompat.getColor(this, R.color.colorTexto))

        ////////////

        val rangeT1 = Range(); val rangeT2 = Range(); val rangeT3 = Range()
        rangeT1.color = Color.parseColor("#7D7AF5"); rangeT1.from = 0.0; rangeT1.to = 45.0
        rangeT2.color = Color.parseColor("#4AA632"); rangeT2.from = 45.0; rangeT2.to = 80.0
        rangeT3.color = Color.parseColor("#F56B22"); rangeT3.from = 80.0; rangeT3.to = 100.0



        //add color ranges to gauge
        gaugeTemp.addRange(rangeT1)
        gaugeTemp.addRange(rangeT2)
        gaugeTemp.addRange(rangeT3)

        //set min max
        gaugeTemp.minValue = 0.0
        gaugeTemp.maxValue = 100.0

        gaugeTemp.setFormatter(ValueFormatter {
            it.toInt().toString()+"°"
        })

        gaugeTemp.setValueColor(ContextCompat.getColor(this, R.color.colorTexto))


        val prefer : SharedPreferences = this.getSharedPreferences("Ajustes", Context.MODE_PRIVATE)
        var fav : Int = -1
        if(prefer.getInt("fav",-1)!=-1){
            fav = prefer.getInt("fav",-1)
        }
        else{
            dbFireStore.collection("usuarios").document(prefer.getString("correo","").toString()).get()
                .addOnSuccessListener {
                    fav = it.get("fav") as Int
                }
        }

        dbFireStore.collection("dispositivos").document(fav.toString()).get()
            .addOnSuccessListener {
                textIlum.text = "${it.get("sIlumi").toString()} lux"
                textHumdS.text = it.get("sHumedadAir").toString() + "%"
                textHumdA.text = it.get("sHumedadSuelo").toString() + "%"
                textTemp.text = it.get("sTemp").toString() + "°"

                val valorHS = it.get("sHumedadSuelo").toString()
                val valorHA = it.get("sHumedadAir").toString()
                val valorI = it.get("sIlumi").toString()
                val valorT = it.get("sTemp").toString()

                gaugeHumSuelo.value = valorHS.toDouble()
                gaugeHumAire.value = valorHA.toDouble()
                gaugeIlum.value = valorI.toDouble()
                gaugeTemp.value = valorT.toDouble()
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
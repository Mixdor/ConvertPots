package com.mixdor.covertpots

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import com.ekn.gruzer.gaugelibrary.ArcGauge
import com.ekn.gruzer.gaugelibrary.Range
import com.ekn.gruzer.gaugelibrary.contract.ValueFormatter
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.google.firebase.storage.ktx.component1
import com.google.firebase.storage.ktx.component2
import java.io.File

class ActMain : AppCompatActivity() {

    private val dbFireStore = FirebaseFirestore.getInstance()

    private val startForProfileImageResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val resultCode = result.resultCode
            val data = result.data
            val fotoPlant = findViewById<ImageView>(R.id.imgViewPlant)

            when (resultCode) {
                Activity.RESULT_OK -> {

                    val user = Firebase.auth.currentUser
                    val uid = user?.uid

                    val fav = obtenerFav()
                    //Image Uri will not be null for RESULT_OK
                    val fileUri = data?.data!!
                    val nameFile = fileUri.lastPathSegment.toString()
                    val extensionFile: String = nameFile.substring(nameFile.lastIndexOf("."))

                    Log.i("File",uid.toString())

                    fotoPlant.setImageURI(fileUri)

                    val storage = Firebase.storage
                    val storageRef = storage.reference

                    val pathRef = storageRef.child("users/${uid.toString()}/plant$fav${extensionFile}")
                    val uploadTask = pathRef.putFile(fileUri)

                    // Register observers to listen for when the download is done or if it fails
                    uploadTask
                        .addOnFailureListener {
                            Toast.makeText(this, "Algo salio mal", Toast.LENGTH_SHORT).show()
                            Log.e("Error",it.toString())
                        }
                        .addOnSuccessListener { taskSnapshot ->
                            // taskSnapshot.metadata contains file metadata such as size, content-type, etc.
                        }
                }
                ImagePicker.RESULT_ERROR -> {
                    Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
                }
                else -> {
                    Toast.makeText(this, "Tarea Cancelada", Toast.LENGTH_SHORT).show()
                }
            }
        }

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
        val fotoPlant = findViewById<ImageView>(R.id.imgViewPlant)

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


        val fav = obtenerFav()

        dbFireStore.collection("dispositivos").document(fav.toString()).get()
            .addOnSuccessListener {

                val valorHS = it.get("sHumedadSuelo").toString()
                val valorHA = it.get("sHumedadAir").toString()
                val valorI = it.get("sIlumi").toString()
                val valorT = it.get("sTemp").toString()

                gaugeHumSuelo.value = valorHS.toDouble()
                gaugeHumAire.value = valorHA.toDouble()
                gaugeIlum.value = valorI.toDouble()
                gaugeTemp.value = valorT.toDouble()
            }


        val user = Firebase.auth.currentUser
        val uid = user?.uid

        val storage = Firebase.storage
        val storageRef = storage.reference

        val listRef = storage.reference.child("users/${uid.toString()}")

        // You'll need to import com.google.firebase.storage.ktx.component1 and
        // com.google.firebase.storage.ktx.component2
        listRef.listAll()
            .addOnSuccessListener { (items, prefixes) ->
                prefixes.forEach { _ ->

                }

                items.forEach { item ->
                    // All the items under listRef
                    val namefile: String = item.name.substring(0,item.name.lastIndexOf("."))
                    val exten: String = item.name.substring(item.name.lastIndexOf(".")+1)

                    if(namefile=="plant$fav"){
                        val pathString = item.path
                        val localFile = File.createTempFile("images", exten)

                        //Log.i("FireStorage",exten)
                        val pathRef = storageRef.child(pathString)

                        pathRef.getFile(localFile).addOnSuccessListener {
                            fotoPlant.setImageURI(localFile.toUri())
                        }.addOnFailureListener {
                            Toast.makeText(this, "Algo salio mal", Toast.LENGTH_SHORT).show()
                            Log.e("Error",it.toString())
                        }
                    }

                }
            }
            .addOnFailureListener {
                Toast.makeText(this, "Algo salio mal", Toast.LENGTH_SHORT).show()
                Log.e("Error",it.toString())
            }


        btnMore.setOnClickListener{
            val intent = Intent(this@ActMain,ActMorePlants::class.java)
            startActivity(intent)
        }

        btnConfig.setOnClickListener{
            val intent = Intent(this@ActMain,ActAjustes::class.java)
            startActivity(intent)
        }

        btnRegar.setOnClickListener{
            Toast.makeText(this,"Regando",Toast.LENGTH_SHORT).show()
        }

        btnFoto.setOnClickListener{

            //dispatchTakePictureIntent()

            ImagePicker.with(this)
                .cropSquare()	//Crop square image, its same as crop(1f, 1f)
                .compress(1024)			//Final image size will be less than 1 MB(Optional)
                .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                .createIntent { intent ->
                    startForProfileImageResult.launch(intent)
                }

            //Toast.makeText(this,"Foto",Toast.LENGTH_SHORT).show()
        }

    }

    fun obtenerFav():Int{

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

        return fav
    }

}
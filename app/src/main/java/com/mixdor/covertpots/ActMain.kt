package com.mixdor.covertpots

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import com.ekn.gruzer.gaugelibrary.Range
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.component1
import com.google.firebase.storage.ktx.component2
import com.google.firebase.storage.ktx.storage
import com.mixdor.covertpots.databinding.ActMainBinding
import java.io.File

class ActMain : AppCompatActivity() {

    private lateinit var binding: ActMainBinding

    private val startForProfileImageResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val resultCode = result.resultCode
            val data = result.data

            when (resultCode) {
                Activity.RESULT_OK -> {

                    val user = Firebase.auth.currentUser
                    val uid = user?.uid

                    //Image Uri will not be null for RESULT_OK
                    val fileUri = data?.data!!
                    val nameFile = fileUri.lastPathSegment.toString()
                    val extensionFile: String = nameFile.substring(nameFile.lastIndexOf("."))

                    Log.i("File",uid.toString())

                    binding.imgViewPlant.setImageURI(fileUri)

                    val storage = Firebase.storage
                    val storageRef = storage.reference
                    val database = Firebase.database
                    val referencia = database.getReference("users")

                    referencia.child(uid.toString()).child("fav").get()
                        .addOnSuccessListener {
                            val fav = it.value.toString().toInt()

                            val pathRef = storageRef.child("users/${uid.toString()}/plant$fav${extensionFile}")
                            val uploadTask = pathRef.putFile(fileUri)

                            // Register observers to listen for when the download is done or if it fails
                            uploadTask
                                .addOnFailureListener { it2 ->
                                    Toast.makeText(this, "Algo salio mal", Toast.LENGTH_SHORT).show()
                                    Log.e("Error",it2.toString())
                                }
                                .addOnSuccessListener { _ ->
                                    // taskSnapshot.metadata contains file metadata such as size, content-type, etc.
                                }
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

        binding = ActMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        //val bundle: Bundle? = intent.extras
        //val serial: Int? = bundle?.getInt("serial")

        val rangeHS1 = Range(); val rangeHS2 = Range(); val rangeHS3 = Range()
        rangeHS1.color = Color.parseColor("#7D7AF5"); rangeHS1.from = 0.0; rangeHS1.to = 45.0
        rangeHS2.color = Color.parseColor("#4AA632"); rangeHS2.from = 45.0; rangeHS2.to = 80.0
        rangeHS3.color = Color.parseColor("#F56B22"); rangeHS3.from = 80.0; rangeHS3.to = 100.0

        //add color ranges to gauge
        binding.gaugeFavHumS.addRange(rangeHS1)
        binding.gaugeFavHumS.addRange(rangeHS2)
        binding.gaugeFavHumS.addRange(rangeHS3)

        //set min max
        binding.gaugeFavHumS.minValue = 0.0
        binding.gaugeFavHumS.maxValue = 100.0

        binding.gaugeFavHumS.setFormatter {
            it.toInt().toString() + "%"
        }

        binding.gaugeFavHumS.valueColor = ContextCompat.getColor(this, R.color.colorTexto)

        /////

        val rangeHA1 = Range(); val rangeHA2 = Range(); val rangeHA3 = Range()
        rangeHA1.color = Color.parseColor("#7D7AF5"); rangeHA1.from = 0.0; rangeHA1.to = 45.0
        rangeHA2.color = Color.parseColor("#4AA632"); rangeHA2.from = 45.0; rangeHA2.to = 80.0
        rangeHA3.color = Color.parseColor("#F56B22"); rangeHA3.from = 80.0; rangeHA3.to = 100.0

        //add color ranges to gauge
        binding.gaugeFavHumA.addRange(rangeHA1)
        binding.gaugeFavHumA.addRange(rangeHA2)
        binding.gaugeFavHumA.addRange(rangeHA3)

        //set min max
        binding.gaugeFavHumA.minValue = 0.0
        binding.gaugeFavHumA.maxValue = 100.0

        binding.gaugeFavHumA.setFormatter {
            it.toInt().toString() + "%"
        }

        binding.gaugeFavHumA.valueColor = ContextCompat.getColor(this, R.color.colorTexto)

        ////////////

        val rangeI1 = Range(); val rangeI2 = Range(); val rangeI3 = Range()
        rangeI1.color = Color.parseColor("#7D7AF5"); rangeI1.from = 0.0; rangeI1.to = 45.0
        rangeI2.color = Color.parseColor("#4AA632"); rangeI2.from = 45.0; rangeI2.to = 80.0
        rangeI3.color = Color.parseColor("#F56B22"); rangeI3.from = 80.0; rangeI3.to = 100.0

        //add color ranges to gauge
        binding.gaugeFavIlum.addRange(rangeI1)
        binding.gaugeFavIlum.addRange(rangeI2)
        binding.gaugeFavIlum.addRange(rangeI3)

        //set min max
        binding.gaugeFavIlum.minValue = 0.0
        binding.gaugeFavIlum.maxValue = 100.0

        binding.gaugeFavIlum.setFormatter {
            it.toInt().toString() + " lux"
        }

        binding.gaugeFavIlum.valueColor = ContextCompat.getColor(this, R.color.colorTexto)

        ////////////

        val rangeT1 = Range(); val rangeT2 = Range(); val rangeT3 = Range()
        rangeT1.color = Color.parseColor("#7D7AF5"); rangeT1.from = 0.0; rangeT1.to = 45.0
        rangeT2.color = Color.parseColor("#4AA632"); rangeT2.from = 45.0; rangeT2.to = 80.0
        rangeT3.color = Color.parseColor("#F56B22"); rangeT3.from = 80.0; rangeT3.to = 100.0



        //add color ranges to gauge
        binding.gaugeFavTemp.addRange(rangeT1)
        binding.gaugeFavTemp.addRange(rangeT2)
        binding.gaugeFavTemp.addRange(rangeT3)

        //set min max
        binding.gaugeFavTemp.minValue = 0.0
        binding.gaugeFavTemp.maxValue = 100.0

        binding.gaugeFavTemp.setFormatter {
            it.toInt().toString() + "°"
        }

        binding.gaugeFavTemp.valueColor = ContextCompat.getColor(this, R.color.colorTexto)

        val database = Firebase.database
        val user = Firebase.auth.currentUser
        val uid = user?.uid

        val referencia = database.getReference("users")

        referencia.child(uid.toString()).child("fav").get()
            .addOnSuccessListener {
                val fav = it.value.toString().toInt()

                val ref = database.getReference("users/${uid}/plants/${fav}")

                ref.child("sHumA").get().addOnSuccessListener { it2 ->
                    val sensor= it2.value.toString().toDouble()
                    binding.gaugeFavHumA.value = sensor
                }
                ref.child("sHumS").get().addOnSuccessListener { it2 ->
                    val sensor= it2.value.toString()
                    binding.gaugeFavHumS.value = sensor.toDouble()
                }
                ref.child("sIlum").get().addOnSuccessListener { it2 ->
                    val sensor= it2.value.toString()
                    binding.gaugeFavIlum.value = sensor.toDouble()
                }
                ref.child("sTemp").get().addOnSuccessListener { it2 ->
                    val sensor= it2.value.toString()
                    binding.gaugeFavTemp.value = sensor.toDouble()
                }

                /////

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
                                    binding.imgViewPlant.setImageURI(localFile.toUri())
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

            }
            .addOnFailureListener {
                Log.e("Error",it.toString())
            }


        binding.btnMorePlants.setOnClickListener{
            val intent = Intent(this@ActMain,ActMorePlants::class.java)
            startActivity(intent)
        }

        binding.btnCofig.setOnClickListener{
            val intent = Intent(this@ActMain,ActAjustes::class.java)
            startActivity(intent)
        }

        binding.btnRegar.setOnClickListener{
            Toast.makeText(this,"Regando",Toast.LENGTH_SHORT).show()
        }

        binding.btnFoto.setOnClickListener{

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

}
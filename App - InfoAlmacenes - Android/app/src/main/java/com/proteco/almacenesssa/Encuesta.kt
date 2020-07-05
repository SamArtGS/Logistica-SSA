package com.proteco.almacenesssa

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.proteco.almacenesssa.ui.main.PagInicio
import kotlinx.android.synthetic.main.activity_encuesta.*

class Encuesta : AppCompatActivity() {

    lateinit var recuperado:Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_encuesta)
        var databd = Firebase.database
        database = databd.reference
        recuperado = intent

        cluesTE.setText(recuperado.getStringExtra("CLUES-SELECCIONADO"))

        database.child("almacenes-ssa").orderByChild("CLUES").equalTo(recuperado.getStringExtra("CLUES-SELECCIONADO")).addValueEventListener(object: ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    val message = dataSnapshot.value
                    println("jajaja"+message)
                }
            }
            override fun onCancelled(p0: DatabaseError) {
                //do whatever you need
            }
        })






        datosCorrectos.isChecked = true
        cluesTE.isEnabled = false
        direccionTE.isEnabled = false
        coloniaTE.isEnabled = false
        ciudadTE.isEnabled = false
        entidadTE.isEnabled = false

        datosCorrectos.setOnClickListener {
            if (datosCorrectos.isChecked){
                cluesTE.isEnabled = false
                direccionTE.isEnabled = false
                coloniaTE.isEnabled = false
                ciudadTE.isEnabled = false
            }else{
                cluesTE.isEnabled = true
                direccionTE.isEnabled = true
                coloniaTE.isEnabled = true
                ciudadTE.isEnabled = true
            }
        }
        db.collection("info_almacenes")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    println("${document.id} => ${document.data}")
                }
            }
            .addOnFailureListener { exception ->
                println("Error getting documents.${exception}")
            }
        foto1.setOnClickListener {
            val callCameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            if(callCameraIntent.resolveActivity(packageManager)!=null){
                startActivityForResult(callCameraIntent,CAMERA_REQUEST_CODE_1)
            }
        }
        foto2.setOnClickListener {
            val callCameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            if(callCameraIntent.resolveActivity(packageManager)!=null){
                startActivityForResult(callCameraIntent,CAMERA_REQUEST_CODE_2)
            }
        }
        foto3.setOnClickListener {
            val callCameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            if(callCameraIntent.resolveActivity(packageManager)!=null){
                startActivityForResult(callCameraIntent,CAMERA_REQUEST_CODE_3)
            }
        }
        enviar.setOnClickListener {
            if (!TextUtils.isEmpty(cluesTE.text.toString())
                && !TextUtils.isEmpty(direccionTE.text.toString())
                && !TextUtils.isEmpty(coloniaTE.text.toString())
                && !TextUtils.isEmpty(ciudadTE.text.toString())
                && !TextUtils.isEmpty(entidadTE.text.toString())
                && !TextUtils.isEmpty(encargadoTE.text.toString())
                && !TextUtils.isEmpty(telefonoTE.text.toString())
                && !TextUtils.isEmpty(emailTE.text.toString())
                && !TextUtils.isEmpty(metrosCuadradosTE.text.toString())
                && !TextUtils.isEmpty(cargaTE.text.toString())
            ) {
                var almace = AlamacenItem(
                    cluesTE.text.toString(),
                    direccionTE.text.toString(),
                    coloniaTE.text.toString(),
                    ciudadTE.text.toString(),
                    entidadTE.text.toString(),
                    encargadoTE.text.toString(),
                    telefonoTE.text.toString(),
                    emailTE.text.toString(),
                    metrosCuadradosTE.text.toString(),
                    cargaTE.text.toString()
                )
                mandarInfoAFirebase(almace)
            }else{
                Toast.makeText(this,"Debes rellenar todos los campos", Toast.LENGTH_SHORT).show()
            }
        }
    }
    val db = Firebase.firestore
    private lateinit var database: DatabaseReference


    override fun onResume() {
        super.onResume()
    }


    private fun mandarInfoAFirebase(almacenItem: AlamacenItem){
        val almach = HashMap<String, Any>()
        almach["clues"] = almacenItem.clues
        almach["direccion"] = almacenItem.direccion
        almach["colonia"] = almacenItem.colonia
        almach["ciudad"] = almacenItem.ciudad
        almach["estado"] = almacenItem.estado
        almach["encargado"] = almacenItem.encargado
        almach["telefono"] = almacenItem.telefono
        almach["email"] = almacenItem.email
        almach["metrosCuadrados"] = almacenItem.metrosCuadrados
        almach["cargaDiaria"] = almacenItem.cargaDiaria
        db.collection("info_almacenes").document(almacenItem.clues)
            .set(almach)
            .addOnSuccessListener {
                Toast.makeText(this,"Carga de datos bien", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this,MainActivity::class.java))
            }
            .addOnFailureListener { e ->  Toast.makeText(this,"Error de carga", Toast.LENGTH_SHORT).show() }
    }

    data class StudentInfo(var studentList: ArrayList<AlamacenItem> = arrayListOf())

    data class AlamacenItem(
        var clues: String,
        var direccion: String,
        var colonia: String,
        var ciudad: String,
        var estado: String,
        var encargado: String,
        var telefono: String,
        var email: String,
        var metrosCuadrados:String,
        var cargaDiaria: String)

    val CAMERA_REQUEST_CODE_1:Int=0
    val CAMERA_REQUEST_CODE_2:Int=1
    val CAMERA_REQUEST_CODE_3:Int=2

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            CAMERA_REQUEST_CODE_1 ->{
                if(resultCode== Activity.RESULT_OK && data !=null){
                    imageView1.setImageBitmap(data.extras?.get("data") as Bitmap)
                }
            }
            CAMERA_REQUEST_CODE_2 ->{
                if(resultCode== Activity.RESULT_OK && data !=null){
                    imageView2.setImageBitmap(data.extras?.get("data") as Bitmap)
                }
            }
            CAMERA_REQUEST_CODE_3 ->{
                if(resultCode== Activity.RESULT_OK && data !=null){
                    imageView3.setImageBitmap(data.extras?.get("data") as Bitmap)
                }
            }
            else -> {
                Toast.makeText(this,"Cámara tuvo problemas para tomar la foto", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

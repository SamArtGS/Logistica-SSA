package com.proteco.almacenesssa

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.widget.Toast
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_encuesta.*

class Encuesta : AppCompatActivity() {
    private var database = Firebase.database.getReference("Almacenes")

    lateinit var recuperado:Intent
    lateinit var lake: String
    var realizado : String? = null
    private lateinit var cliente : FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_encuesta)

        recuperado = intent
        println("Recuperado"+recuperado.getStringExtra("ID-seleccionado"))
        database.orderByChild("CLUES").equalTo(recuperado.getStringExtra("ID-seleccionado"))
            .addValueEventListener(object: ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (ds in dataSnapshot.children){
                        lake = ds.key!!
                        realizado = ds.child("REALIZADO").getValue(String::class.java)
                        cluesTE.setText(ds.child("CLUES").getValue(String::class.java))
                        NombreUnidadTE.setText(ds.child("NOMBREUNIDAD").getValue(String::class.java))
                        rfcTE.setText(ds.child("RFC").getValue(String::class.java))
                        direccionTE.setText(ds.child("DIRECCION").getValue(String::class.java))
                        localidadTE.setText(ds.child("NOMBRE-LOCALIDAD").getValue(String::class.java))
                        municipioTE.setText(ds.child("NOMBREMUNICIPIO").getValue(String::class.java))
                        entidadTE.setText(ds.child("NOMBREENTIDAD").getValue(String::class.java))
                        cpostalTE.setText(ds.child("CODIGOPOSTAL").getValue(String::class.java))
                        tipoTE.setText(ds.child("ESTRATOUNIDAD").getValue(String::class.java))
                        fechainicioTE.setText(ds.child("FECHAINICIOPERACION").getValue(String::class.java))
                        fechaultimoTE.setText(ds.child("FEULMOVIMIENTO").getValue(String::class.java))
                        latitudTE.setText(ds.child("LATITUD").getValue(String::class.java))
                        longitudTE.setText(ds.child("LONGITUD").getValue(String::class.java))
                        encargadoTE.setText(ds.child("ENCARGADO").getValue(String::class.java))
                        telefonoTE.setText(ds.child("TELEFONO").getValue(String::class.java))
                        emailTE.setText(ds.child("EMAIL").getValue(String::class.java))
                        metrosCuadradosTE.setText(ds.child("METROSCUADRADOS").getValue(String::class.java))
                        numEmpleadosTE.setText(ds.child("NUMEMPLEADOS").getValue(String::class.java))
                    }
                    if (realizado == "1"){
                        datosCorrectos.isChecked = true
                        cluesTE.isEnabled = false
                        NombreUnidadTE.isEnabled = false
                        rfcTE.isEnabled = false
                        direccionTE.isEnabled = false
                        localidadTE.isEnabled = false
                        municipioTE.isEnabled = false
                        entidadTE.isEnabled = false
                        cpostalTE.isEnabled = false
                        tipoTE.isEnabled = false
                        fechainicioTE.isEnabled = false
                        fechaultimoTE.isEnabled = false
                        latitudTE.isEnabled = false
                        longitudTE.isEnabled = false
                        encargadoTE.isEnabled = false
                        telefonoTE.isEnabled = false
                        emailTE.isEnabled = false
                        metrosCuadradosTE.isEnabled = false
                        numEmpleadosTE.isEnabled = false
                        datosCorrectos.isClickable = false
                        foto1.isClickable = false
                        foto2.isClickable = false
                        foto3.isClickable = false
                        enviar.isClickable = false

                    }else {
                        datosCorrectos.isChecked = true
                        cluesTE.isEnabled = false
                        NombreUnidadTE.isEnabled = false
                        rfcTE.isEnabled = false
                        direccionTE.isEnabled = false
                        localidadTE.isEnabled = false
                        municipioTE.isEnabled = false
                        entidadTE.isEnabled = false
                        cpostalTE.isEnabled = false
                        tipoTE.isEnabled = false
                        fechainicioTE.isEnabled = false
                        fechaultimoTE.isEnabled = false
                        latitudTE.isEnabled = false
                        longitudTE.isEnabled = false
                        encargadoTE.isEnabled = true
                        telefonoTE.isEnabled = true
                        emailTE.isEnabled = true
                        metrosCuadradosTE.isEnabled = true
                        numEmpleadosTE.isEnabled = true
                    }


                }
            }
            override fun onCancelled(p0: DatabaseError) {
                Toast.makeText(this@Encuesta,"No se pudo realizar el fetchData",Toast.LENGTH_SHORT).show()
            }
        })
        datosCorrectos.setOnClickListener {
            if (datosCorrectos.isChecked){
                NombreUnidadTE.isEnabled = false
                rfcTE.isEnabled = false
                direccionTE.isEnabled = false
                cpostalTE.isEnabled = false
                tipoTE.isEnabled = false
            }else{
                NombreUnidadTE.isEnabled = true
                rfcTE.isEnabled = true
                direccionTE.isEnabled = true
                cpostalTE.isEnabled = true
                tipoTE.isEnabled = true
            }
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
                && !TextUtils.isEmpty(localidadTE.text.toString())
                && !TextUtils.isEmpty(municipioTE.text.toString())
                && !TextUtils.isEmpty(entidadTE.text.toString())
                && !TextUtils.isEmpty(encargadoTE.text.toString())
                && !TextUtils.isEmpty(telefonoTE.text.toString())
                && !TextUtils.isEmpty(emailTE.text.toString())
                && !TextUtils.isEmpty(metrosCuadradosTE.text.toString())
                && !TextUtils.isEmpty(numEmpleadosTE.text.toString())
            ) {
                var progressBar = ProgressDialog(this)
                progressBar.setMessage("Actualizando registro")
                progressBar.show()
                database.child(lake).child("NOMBREUNIDAD").setValue(NombreUnidadTE.text.toString())
                database.child(lake).child("RFC").setValue(rfcTE.text.toString())
                database.child(lake).child("DIRECCION").setValue(direccionTE.text.toString())
                database.child(lake).child("CODIGOPOSTAL").setValue(cpostalTE.text.toString())
                database.child(lake).child("ESTRATOUNIDAD").setValue(tipoTE.text.toString())
                database.child(lake).child("ESTRATOUNIDAD").setValue(tipoTE.text.toString())
                database.child(lake).child("LATITUD").setValue(latitudTE.text.toString())
                database.child(lake).child("LONGITUD").setValue(longitudTE.text.toString())

                database.child(lake).child("ENCARGADO").setValue(encargadoTE.text.toString())
                database.child(lake).child("TELEFONO").setValue(telefonoTE.text.toString())
                database.child(lake).child("EMAIL").setValue(emailTE.text.toString())
                database.child(lake).child("METROSCUADRADOS").setValue(metrosCuadradosTE.text.toString())
                database.child(lake).child("NUMEMPLEADOS").setValue(numEmpleadosTE.text.toString())
                database.child(lake).child("REALIZADO").setValue("1")

                progressBar.hide()
                startActivity(Intent(this,MainActivity::class.java))
            }else{
                Toast.makeText(this,"Debes rellenar todos los campos", Toast.LENGTH_SHORT).show()
            }
        }
        }




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

    }

    data class AlamacenItem(
        var clues: String = "",
        var direccion: String = "",
        var colonia: String = "",
        var ciudad: String = "",
        var estado: String = "",
        var encargado: String = "",
        var telefono: String = "",
        var email: String = "",
        var metrosCuadrados:String = "",
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

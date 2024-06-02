package com.example.proyectofinal

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.RadioButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.proyectofinal.databinding.ActivityAuthBinding
import com.example.proyectofinal.databinding.ActivityReservarEntradaBinding
import com.google.firebase.firestore.FirebaseFirestore

class ReservarEntradaActivity : AppCompatActivity(){

    lateinit var enlace4:ActivityReservarEntradaBinding

    private val bbdd = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        enlace4 = ActivityReservarEntradaBinding.inflate(layoutInflater)
        setContentView(enlace4.root)
    }

    override fun onStart() {
        super.onStart()

        //Controlo el pasar del cardView1 al cardView2
        enlace4.btnSiguiente.setOnClickListener {
            enlace4.cardView1.visibility = View.GONE
            enlace4.cardView2.visibility = View.VISIBLE
        }

        //Controlo el pasar del cardView2 al cardView3
        enlace4.btnSiguiente2.setOnClickListener {
            enlace4.cardView2.visibility = View.GONE
            enlace4.cardView3.visibility = View.VISIBLE
            datosParaConfirmar()
        }

        //Boton confirmar reserva
        enlace4.btnConfirmarReserva.setOnClickListener {
            //Llamo a la funcion guardar datos
            guardarDatos()
            Toast.makeText(applicationContext, "Entrada confirmada correctamente", Toast.LENGTH_SHORT).show()
            finish()
        }

        //Imagen clicable para volver a la pantalla del menu principal
        enlace4.imagenReservarEntradas.setOnClickListener {
            Toast.makeText(applicationContext, "No se ha finalizado la compra de la entrada", Toast.LENGTH_SHORT).show()
            finish()
        }

        //Array para guardar las opciones del spinner
        val tiposEntradas = arrayOf("Entrada Preferente", "Entrada Fondo", "Entrada Gol Norte", "Entrada Gol Sur")
        enlace4.tipoEntrada.adapter = ArrayAdapter<String>(this,android.R.layout.simple_list_item_activated_1, tiposEntradas)
    }

    //En esta funcion controlo los datos que proporciona el usuario
    private fun datosParaConfirmar(){
        enlace4.nombreCompradorResumen.text = enlace4.nombreApellidosComprador.text.toString()
        enlace4.dniCompradorResumen.text = enlace4.dniComprador.text.toString()
        enlace4.correoCompradorResumen.text = enlace4.correoElectronicoComprador.text.toString()
        enlace4.eventoResumen.text = enlace4.descripcionEvento.text.toString()
        enlace4.tipoEntradaResumen.text = enlace4.tipoEntrada.selectedItem.toString()
        enlace4.direccionCompradorResumen.text = enlace4.direccionComprador.text.toString()

        //Controlamos los datos de los radioButtons
        val seleccionarIdRadioButton = enlace4.grupoRadioBotones.checkedRadioButtonId
        val seleccionarTipoBoton = enlace4.grupoRadioBotones.findViewById<RadioButton>(seleccionarIdRadioButton)

        enlace4.tuEquipoResumen.text = seleccionarTipoBoton.text.toString()
    }

    private fun guardarDatos(){
        bbdd.collection("reservas").document(enlace4.correoCompradorResumen.text.toString()).set(
            hashMapOf("nombreCompradorBD" to enlace4.nombreCompradorResumen.text.toString(),
                "dniCompradorBD" to enlace4.dniCompradorResumen.text.toString(),
                "direccionCompradorBD" to enlace4.direccionCompradorResumen.text.toString(),
                "eventoCompradorBD" to enlace4.eventoResumen.text.toString(),
                "tuEquipoBD" to enlace4.tuEquipoResumen.text.toString(),
                "tipoEntradaBD" to enlace4.tipoEntradaResumen.text.toString())
        )
    }
}
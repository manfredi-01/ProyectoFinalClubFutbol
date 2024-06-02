package com.example.proyectofinal

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.proyectofinal.databinding.ActivityMostrarReservasBinding
import com.google.firebase.firestore.FirebaseFirestore

class MostrarReservasActivity : AppCompatActivity() {
    lateinit var enlace5: ActivityMostrarReservasBinding

    private val bbdd = FirebaseFirestore.getInstance()

    private val reservas = ArrayList<Reserva>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enlace5 = ActivityMostrarReservasBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(enlace5.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        
        enlace5.btnBuscarReserva.setOnClickListener {
            recuperarEntradas()
        }

        enlace5.btnEliminarReserva.setOnClickListener {
            eliminarReserva()
        }

        enlace5.imagenMostrarReservas.setOnClickListener {
            finish()
        }

    }

    private fun recuperarEntradas(){
        bbdd.collection("reservas").document(enlace5.txtEmailBuscar.text.toString()).get().addOnSuccessListener {
            enlace5.idCompradorEntrada.setText(it.get("dniCompradorBD") as String?)
            enlace5.idNombre.setText(it.get("nombreCompradorBD") as String?)
            enlace5.idEvento.setText(it.get("eventoCompradorBD") as String?)
            enlace5.idTipoEntrada.setText(it.get("tipoEntradaBD") as String?)
        }
    }

    private fun eliminarReserva(){
        bbdd.collection("reservas").document(enlace5.txtEmailBuscar.text.toString()).delete()
        Toast.makeText(this,"Borrado completado correctamente", Toast.LENGTH_SHORT).show()
        recuperarEntradas()
    }
}
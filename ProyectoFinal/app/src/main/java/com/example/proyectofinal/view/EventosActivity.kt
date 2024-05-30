package com.example.proyectofinal.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.proyectofinal.HomeActivity
import com.example.proyectofinal.R
import com.example.proyectofinal.databinding.ActivityEventosBinding
import com.example.proyectofinal.databinding.ActivityHomeBinding
import com.example.proyectofinal.viewmodel.ListaViewModel
import com.google.firebase.auth.FirebaseAuth

class EventosActivity : AppCompatActivity(){
    private lateinit var lista: ListaViewModel
    lateinit var enlace3: ActivityEventosBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enlace3 = ActivityEventosBinding.inflate(layoutInflater)
        setContentView(enlace3.root)

        val bundle:Bundle? = intent.extras
        val email:String? = bundle?.getString("email")
        val provider:String? = bundle?.getString("provider")
        setup(email?: "", provider?:"")

        lista = ViewModelProvider(this).get(ListaViewModel::class.java)

        //Observamo los cambios
        lista.mostrarTostada.observe(this, Observer { message ->
            message?.let {
                mostrarTostada(it)
            }
        })
    }

    override fun onStart() {
        super.onStart()
        enlace3.imagenEventos.setOnClickListener {
            val inicio= Intent(this,HomeActivity::class.java)
            //finishAffinity()
            startActivity(inicio)
        }
    }

    private fun mostrarTostada(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
    }

    private fun setup(email:String, provider:String){
        title = "Inicio"
        enlace3.emailEventos.text = email
        enlace3.proveedorEventos.text = provider
    }
}
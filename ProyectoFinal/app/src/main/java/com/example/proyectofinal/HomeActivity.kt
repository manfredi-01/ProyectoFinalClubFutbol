package com.example.proyectofinal

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.proyectofinal.databinding.ActivityHomeBinding
import com.example.proyectofinal.view.EventosActivity
import com.google.firebase.auth.FirebaseAuth

enum class ProviderType{
    USUARIO
}

class HomeActivity : AppCompatActivity() {
    lateinit var enlace2: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        enlace2 = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(enlace2.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val bundle:Bundle? = intent.extras
        val email:String? = bundle?.getString("email")
        val provider:String? = bundle?.getString("provider")
        setup(email?: "", provider?:"")
    }

    override fun onStart() {
        super.onStart()
        enlace2.btnEnviarCorreo.setOnClickListener {
            var direccion: String="rmanfredi2001@gmail.com"
            var asunto: String="Entradas"
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:rmanfredi2001@gmail.com")
                putExtra(Intent.EXTRA_EMAIL, direccion)
                putExtra(Intent.EXTRA_SUBJECT, asunto)
            }
            startActivity(intent)
        }

        enlace2.btnProximosPartidos.setOnClickListener {
            val siguiente= Intent(this,EventosActivity::class.java)
            startActivity(siguiente)
        }
    }

    private fun setup(email:String, provider:String){
        title = "Inicio"
        enlace2.emailTextView.text = email
        enlace2.ProviderTextView.text = provider

        enlace2.logOutButton.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            onBackPressed()
        }
    }
}
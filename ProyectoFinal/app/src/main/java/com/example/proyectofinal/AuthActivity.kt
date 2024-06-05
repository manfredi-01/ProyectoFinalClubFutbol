package com.example.proyectofinal

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.proyectofinal.databinding.ActivityAuthBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import java.security.Provider

class AuthActivity : AppCompatActivity() {
    lateinit var enlace:ActivityAuthBinding

    private val GOOGLE_SIGN_IN = 100

    private val bbdd = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        enlace = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(enlace.root)

        //
        val analytics:FirebaseAnalytics = FirebaseAnalytics.getInstance(this)
        val bundle = Bundle()
        bundle.putString("message", "Integracion de Firebase completa")
        analytics.logEvent("InitScreen", bundle)
        //

        //Setup
        //setup()
        sesion()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onStart() {
        super.onStart()

        enlace.authLayout.visibility = View.VISIBLE


        enlace.singUpButton.setOnClickListener {
            registrarNuevo()
        }

        //Boton para entrar a la aplicacion
        enlace.logInButton.setOnClickListener {
            logIn()
        }

        enlace.btnGoogle.setOnClickListener {
            //Configuracion de autenticacion con google
            val googleConf = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()

            val googleClient = GoogleSignIn.getClient(this, googleConf)
            googleClient.signOut()

            startActivityForResult(googleClient.signInIntent, GOOGLE_SIGN_IN)
        }
    }

    private fun registrarNuevo(){
        var contraseña : String
        var correo : String

        if (enlace.emailEditText.text.isNotEmpty() && enlace.passwordEditText.text.isNotEmpty()){

            bbdd.collection("usuarios").document(enlace.emailEditText.text.toString()).get().addOnSuccessListener {
                correo = ((it.get("correoBD") as String?).toString())
                contraseña = ((it.get("contraseñaBD") as String?).toString())

                //Comprobamos que no se repita ningun usuario
                if(correo==enlace.emailEditText.text.toString() && contraseña==enlace.passwordEditText.text.toString()){
                    Toast.makeText(this,"Nombre de usuario ya registrado", Toast.LENGTH_SHORT).show()
                }else{
                    bbdd.collection("usuarios").document(enlace.emailEditText.text.toString()).set(
                        hashMapOf("correoBD" to enlace.emailEditText.text.toString(),
                            "contraseñaBD" to enlace.passwordEditText.text.toString())
                    )
                    showHome(enlace.emailEditText.text.toString(), ProviderType.USUARIO)
                    /*Toast.makeText(this,"Usuario registrado correctamente", Toast.LENGTH_SHORT).show()
                    val homeIntent = Intent(this, HomeActivity::class.java)
                    startActivity(homeIntent)*/
                }
            }
        }else{
            showAlertCamposVacios()
        }

    }

    private fun logIn(){
        var contraseña : String
        var correo : String

        if (enlace.emailEditText.text.isNotEmpty() && enlace.passwordEditText.text.isNotEmpty()){

            bbdd.collection("usuarios").document(enlace.emailEditText.text.toString()).get().addOnSuccessListener {
                correo = ((it.get("correoBD") as String?).toString())
                contraseña = ((it.get("contraseñaBD") as String?).toString())

                //Comprobamos si el valor de los campos coincide con nuestra base de datos
                if(correo==enlace.emailEditText.text.toString() && contraseña==enlace.passwordEditText.text.toString()){
                    showHome(enlace.emailEditText.text.toString(), ProviderType.USUARIO)

                    /*val homeIntent = Intent(this, HomeActivity::class.java)
                    startActivity(homeIntent)*/
                }else{
                    Toast.makeText(this,"Usuario o contraseña incorrecto", Toast.LENGTH_SHORT).show()
                }

            }
        }else{
            showAlertCamposVacios()
        }

    }

    private fun sesion(){
        val prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE)
        val email = prefs.getString("email", null)
        val provider = prefs.getString("provider", null)

        if(email !=null && provider != null){
            enlace.authLayout.visibility = View.INVISIBLE
            showHome(email, ProviderType.valueOf(provider))
        }
    }

    /*private fun setup(){
        title = "Atenticacion"

        enlace.singUpButton.setOnClickListener {
            if (enlace.emailEditText.text.isNotEmpty() && enlace.passwordEditText.text.isNotEmpty()){
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(enlace.emailEditText.text.toString(), enlace.passwordEditText.toString()).addOnCompleteListener {
                    if(it.isSuccessful){
                        //Para viajar a la pantalla de menu
                        showHome(it.result?.user?.email ?: "", ProviderType.USUARIO)
                    }else{
                      showAlert()
                    }
                }
            }
        }

        ////////////////////////
        //PARA INTENTAR FALSEAR
        //val homeIntent = Intent(this, HomeActivity::class.java)
        ////////////////////////

        //Boton para entrar a la aplicacion
        enlace.logInButton.setOnClickListener {
            if (enlace.emailEditText.text.isNotEmpty() && enlace.passwordEditText.text.isNotEmpty()){

                ////////////////////////
                //PARA INTENTAR FALSEAR
                //startActivity(homeIntent)
                ////////////////////////

                showHome(enlace.emailEditText.text.toString(), ProviderType.USUARIO)
                FirebaseAuth.getInstance().signInWithEmailAndPassword(enlace.emailEditText.text.toString(), enlace.passwordEditText.toString()).addOnCompleteListener {
                    if(it.isSuccessful){
                        showHome(it.result?.user?.email ?: "", ProviderType.USUARIO)
                    }else{
                        showAlert()
                    }
                }
            }
        }

        enlace.btnGoogle.setOnClickListener {
            //Configuracion de autenticacion con google
            val googleConf = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))*//**//**//**//**//**//**//**//**//**//*
                .requestEmail()
                .build()

            val googleClient = GoogleSignIn.getClient(this, googleConf)
            googleClient.signOut()

            startActivityForResult(googleClient.signInIntent, GOOGLE_SIGN_IN)
        }
    }*/

    private fun showAlert(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Se ha producido un error autenticando al usuario")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    //Aviso de campos vacios
    private fun showAlertCamposVacios(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Se ha producido un error, debe de completar todos los campos")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun showHome(email:String, provider:ProviderType){
        val homeIntent = Intent(this, HomeActivity::class.java).apply {
            putExtra("email", email)
            putExtra("provider", provider.name)
        }
        startActivity(homeIntent)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == GOOGLE_SIGN_IN){
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)

            try {
                val account = task.getResult(ApiException::class.java)

                if(account != null){
                    val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                    FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener {
                        if(it.isSuccessful){
                            showHome(account.email?:"", ProviderType.GOOGLE)
                        }else{
                            showAlert()
                        }
                    }
                }
            }catch (e : ApiException){
                showAlert()
            }


        }
    }
}
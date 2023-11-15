package com.adndevelopersoftware.viajeroconductor.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import com.adndevelopersoftware.viajeroconductor.databinding.ActivityMainBinding
import com.adndevelopersoftware.viajeroconductor.providers.AuthProvider

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    val authProvider = AuthProvider()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

        binding.btnRegister.setOnClickListener { goToRegister() }
        binding.btnLogin.setOnClickListener { login() }
    }

    private fun login(){
        val email = binding.txtFieldEmail.text.toString().trim()
        val password = binding.txtFieldPassword.text.toString().trim()
        if(isValidForm(email, password)){
            authProvider.login(email, password).addOnCompleteListener {
                if (it.isSuccessful){
                    goToMap()
                } else {
                    Toast.makeText(this, "Usuario y/o contraseña incorrecto", Toast.LENGTH_LONG).show()
                    Log.d("Firebase","Error en login: ${it.exception.toString()}")
                }
            }
        }
    }

    private fun goToMap(){
        val i = Intent(this, MapActivity::class.java)
        i.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(i)
    }

    private fun isValidForm(email: String, password: String): Boolean{
        if(email.isEmpty()){
            Toast.makeText(this, "Ingresa un correo electronico", Toast.LENGTH_SHORT).show()
            return false
        }
        if(password.isEmpty()){
            Toast.makeText(this, "Ingresa una contraseña", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun goToRegister(){
        val i = Intent(this, RegisterActivity::class.java)
        startActivity(i)
    }

    override fun onStart() {
        super.onStart()
        if (authProvider.existSession()){
            goToMap()
        }
    }
}
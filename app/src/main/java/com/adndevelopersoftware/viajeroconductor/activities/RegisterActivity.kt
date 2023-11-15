package com.adndevelopersoftware.viajeroconductor.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.adndevelopersoftware.viajeroconductor.databinding.ActivityRegisterBinding
import com.adndevelopersoftware.viajeroconductor.models.Driver
import com.adndevelopersoftware.viajeroconductor.providers.AuthProvider
import com.adndevelopersoftware.viajeroconductor.providers.DriverProvider

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private val authProvider = AuthProvider()
    private val driverProvider = DriverProvider()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

        binding.btnGoToLogin.setOnClickListener { goToLogin() }
        binding.btnRegister.setOnClickListener { registrer() }
    }

    private fun registrer(){
        val nombre = binding.textFieldName.text.toString().trim()
        val apellido = binding.textFieldLastname.text.toString().trim()
        val email = binding.textFieldEmail.text.toString().trim()
        val telefono = binding.textFieldPhone.text.toString().trim()
        val password = binding.textFieldPassword.text.toString().trim()
        val confirmPassword = binding.textFieldConfirmPassword.text.toString().trim()
        if(isValidPassword(nombre,apellido, email, telefono, password, confirmPassword)){
            authProvider.register(email, password).addOnCompleteListener {
                if (it.isSuccessful){
                    val conductor = Driver(
                        id = authProvider.getId(),
                        name =nombre,
                        lastname = apellido,
                        email = email,
                        phone = telefono
                    )
                    driverProvider.create(conductor).addOnCompleteListener {
                        if (it.isSuccessful) {
                            goToMap()
                        } else {
                            Toast.makeText(this@RegisterActivity, "Error guardando usuario", Toast.LENGTH_SHORT).show()
                            Log.d("Firebase","Error en el guardado: ${it.exception.toString()}")
                        }
                    }
                } else {
                    Toast.makeText(this@RegisterActivity, "Error en el registro: ${it.exception.toString()}", Toast.LENGTH_SHORT).show()
                    Log.d("Firebase","Error en el registro: ${it.exception.toString()}")
                }
            }
        }
    }

    private fun goToMap(){
        val i = Intent(this, MapActivity::class.java)
        i.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(i)
    }

    private fun isValidPassword(nombre: String, apellido: String, email: String, telefono: String, password: String, confirmPassword: String): Boolean{
        if(nombre.isEmpty()){
            Toast.makeText(this, "Debes ingresar un nombre", Toast.LENGTH_SHORT).show()
            return false
        }
        if(apellido.isEmpty()){
            Toast.makeText(this, "Debes ingresar un apellido", Toast.LENGTH_SHORT).show()
            return false
        }
        if(email.isEmpty()){
            Toast.makeText(this, "Debes ingresar un email", Toast.LENGTH_SHORT).show()
            return false
        }
        if(telefono.isEmpty()){
            Toast.makeText(this, "Debes ingresar un telefono", Toast.LENGTH_SHORT).show()
            return false
        }
        if(password.isEmpty()){
            Toast.makeText(this, "Debes ingresar una contraseña", Toast.LENGTH_SHORT).show()
            return false
        }
        if(password.length < 6){
            Toast.makeText(this, "La contraseña debe ser de al menos 6 digitos", Toast.LENGTH_LONG).show()
            return false
        }
        if(!password.equals(confirmPassword)){
            Toast.makeText(this, "Las contraseñas debe ser iguales", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun goToLogin(){
        val i = Intent(this, MainActivity::class.java)
        startActivity(i)
    }
}
package com.example.conexionabackend

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.conexionabackend.data.RegisterRequest
import kotlinx.coroutines.launch
import java.io.IOException
import com.example.conexionabackend.network.RetrofitClient


class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val editUsername = findViewById<EditText>(R.id.editTextUsername)
        val editPassword = findViewById<EditText>(R.id.editTextPassword)
        val btnRegister = findViewById<Button>(R.id.buttonRegister)

        btnRegister.setOnClickListener {
            val username = editUsername.text.toString().trim()
            val password = editPassword.text.toString().trim()

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            registerUser(username, password)
        }
    }

    private fun registerUser(username: String, password: String) {
        lifecycleScope.launch {
            try {
                val response = RetrofitClient.instance.register(RegisterRequest(username, password))
                if (response.isSuccessful) {
                    val apiResponse = response.body()
                    Toast.makeText(this@RegisterActivity, "Éxito: ${apiResponse?.message}", Toast.LENGTH_LONG).show()
                    // Opcional: Volver al Login tras registro exitoso
                    finish()
                } else {
                    val errorBody = response.errorBody()?.string()
                    Toast.makeText(this@RegisterActivity, "Error en registro: $errorBody", Toast.LENGTH_LONG).show()
                }
            } catch (e: IOException) {
                Toast.makeText(this@RegisterActivity, "Error de red: ${e.message}", Toast.LENGTH_LONG).show()
            } catch (e: Exception) {
                Toast.makeText(this@RegisterActivity, "Error inesperado: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }
}

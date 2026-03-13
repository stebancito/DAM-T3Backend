package com.example.conexionabackend

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.conexionabackend.data.LoginRequest
import com.example.conexionabackend.data.TokenManager
import kotlinx.coroutines.launch
import java.io.IOException
import com.example.conexionabackend.network.RetrofitClient
import com.google.gson.Gson

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Inicializar TokenManager si no se ha hecho
        TokenManager.init(this)

        val editUsername = findViewById<EditText>(R.id.editTextUsername)
        val editPassword = findViewById<EditText>(R.id.editTextPassword)
        val btnLogin = findViewById<Button>(R.id.buttonLogin)

        btnLogin.setOnClickListener {
            val username = editUsername.text.toString().trim()
            val password = editPassword.text.toString().trim()

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            loginUser(username, password)
        }
    }

    private fun loginUser(username: String, password: String) {
        lifecycleScope.launch {
            try {
                val response = RetrofitClient.instance.login(LoginRequest(username, password))
                if (response.isSuccessful) {
                    val loginResponse = response.body()
                    loginResponse?.let {
                        // Guardar el token
                        TokenManager.saveToken(it.token)

                        // Convertir el objeto a JSON (respuesta cruda)
                        val rawJson = Gson().toJson(it)

                        // Ir a WelcomeActivity con el username y la respuesta cruda
                        val intent = Intent(this@LoginActivity, WelcomeActivity::class.java)
                        intent.putExtra("USERNAME", username)
                        intent.putExtra("RAW_RESPONSE", rawJson)
                        startActivity(intent)
                        finish()
                    }
                } else {
                    // Manejar error HTTP
                    val errorBody = response.errorBody()?.string()
                    Toast.makeText(this@LoginActivity, "Login fallido: $errorBody", Toast.LENGTH_LONG).show()
                }
            } catch (e: IOException) {
                Toast.makeText(this@LoginActivity, "Error de red: ${e.message}", Toast.LENGTH_LONG).show()
            } catch (e: Exception) {
                Toast.makeText(this@LoginActivity, "Error inesperado: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }
}

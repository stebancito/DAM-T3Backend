package com.example.conexionabackend

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.content.Intent
import android.widget.Button
import android.widget.TextView
import com.example.conexionabackend.data.TokenManager

class WelcomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        val textUsername = findViewById<TextView>(R.id.textUsername)
        val textRawResponse = findViewById<TextView>(R.id.textRawResponse)
        val buttonLogout = findViewById<Button>(R.id.buttonLogout)

        // Obtener datos del Intent
        val username = intent.getStringExtra("USERNAME") ?: "Invitado"
        val rawResponse = intent.getStringExtra("RAW_RESPONSE") ?: "No hay respuesta"

        textUsername.text = "Bienvenido, $username"
        textRawResponse.text = "Respuesta cruda:\n$rawResponse"

        buttonLogout.setOnClickListener {
            // Limpiar token y volver a MainActivity
            TokenManager.clearToken()
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }
}
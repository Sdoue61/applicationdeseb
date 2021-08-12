package com.example.applicationdeseb

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class start : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        val next = findViewById<Button>(R.id.btnext)
        val nameuser = findViewById<EditText>(R.id.nom)

            next.setOnClickListener {
                if (nameuser.text.isNotEmpty()) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
            else {
                Toast.makeText(this, "Entrer votre nom", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
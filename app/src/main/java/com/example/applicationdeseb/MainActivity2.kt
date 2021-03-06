package com.example.applicationdeseb

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity2 : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        auth = Firebase.auth

        val bt = findViewById<Button>(R.id.button)
        val user = findViewById<EditText>(R.id.textView2)
        val pwd = findViewById<EditText>(R.id.textView3)
        val retourlogin = findViewById<Button>(R.id.loginback)

        bt.alpha = 0f
        bt.translationY = 50F
        bt.animate().alpha(1f).translationYBy(-50F).duration = 1500

        retourlogin.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        bt.setOnClickListener {
            val email = user.text.toString()
            val password = pwd.text.toString()
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(
                            baseContext, "Enregistrement Ok.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    else {
                        Toast.makeText(
                            baseContext, "Erreur.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }
    }
}
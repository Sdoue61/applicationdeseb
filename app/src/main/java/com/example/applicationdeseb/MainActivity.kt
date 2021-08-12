package com.example.applicationdeseb

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        auth = Firebase.auth
        val bt = findViewById<Button>(R.id.bt_login)
        val user = findViewById<EditText>(R.id.user)
        val pwd = findViewById<EditText>(R.id.pwd)
        val inscription = findViewById<Button>(R.id.btinscription)

        bt.alpha = 0f
        bt.translationY = 50F
        bt.animate().alpha(1f).translationYBy(-50F).duration = 1500

        inscription.setOnClickListener {
            val intent = Intent(this, MainActivity2::class.java)
            startActivity(intent)
        }

        bt.setOnClickListener {
            val email = user.text.toString()
            val password = pwd.text.toString()
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(baseContext, "Authentification Reussie.",
                            Toast.LENGTH_SHORT).show()
                        val intentnext = Intent(this, MainActivity3::class.java)
                        startActivity(intentnext)
                    }
                    else {
                        Toast.makeText(baseContext, "Erreur",
                            Toast.LENGTH_SHORT).show()
                    }
                }
        }

    }
}
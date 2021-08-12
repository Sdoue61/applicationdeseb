package com.example.applicationdeseb

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity3 : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)
        auth = Firebase.auth

        val user = auth.currentUser
        val tvEmail = findViewById<TextView>(R.id.tvidentifiant)
        tvEmail.text = user?.uid

        val btretourconnecter = findViewById<Button>(R.id.btretourpage1)
        val btdeconnecter = findViewById<Button>(R.id.btdeconnecter)
        val btverifiermail = findViewById<Button>(R.id.btverif)
        val btrefresh = findViewById<Button>(R.id.btactualiser)

        btretourconnecter.setOnClickListener {
            val retourintent = Intent(this,MainActivity::class.java)
            startActivity(retourintent)
        }
        btdeconnecter.setOnClickListener {
            auth.signOut()
        }

        btrefresh.setOnClickListener {
            val refreshintent = Intent(this,MainActivity3::class.java)
            startActivity(refreshintent)
        }
        if (user != null){
            val emailVerified = user.isEmailVerified

            if (emailVerified){
                btverifiermail.visibility = View.GONE
            }
            else{
                btverifiermail.setOnClickListener {
                    user.sendEmailVerification()
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(this,"Mail envoyé",Toast.LENGTH_LONG).show()
                            }
                        }
                }
            }
        }

    }
}
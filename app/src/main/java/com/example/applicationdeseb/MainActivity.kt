package com.example.applicationdeseb

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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
        val nomuser = intent.getStringExtra("User_Name")
        val username = findViewById<TextView>(R.id.nomuser)
        username.text = nomuser

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
                        intent.putExtra("User_Name", username.text.toString())
                        startActivity(intentnext)
                    }
                    else {
                        Toast.makeText(baseContext, "Erreur",
                            Toast.LENGTH_SHORT).show()
                    }
                }
        }

        val resetpwd = findViewById<Button>(R.id.oubliepwd)
        resetpwd.setOnClickListener{
                val user2 = Firebase.auth.currentUser!!
                user2.delete()
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(baseContext, "Account Bye Bye",
                                Toast.LENGTH_SHORT).show()
                        }
                    }
        }
            // Check if user is signed in (non-null) and update UI accordingly.
            val currentUser = auth.currentUser
            if(currentUser != null){
                val accueilIntent = Intent(this,MainActivity3::class.java)
                startActivity(accueilIntent)
            }
    }
}
package com.example.applicationdeseb

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.applicationdeseb.databinding.ActivityMain4Binding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase

class MainActivity4 : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityMain4Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain4Binding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        auth = Firebase.auth
        val currentUser : FirebaseUser = auth.currentUser!!
        binding.ETEmail.setText(currentUser.email)
        if (currentUser.displayName.isNullOrEmpty()) {
            binding.TVUsername.text = "Veuillez entrez un nom d'utilisateur"
        }
        else{
            binding.ETUsername.setText(currentUser.displayName)
            binding.TVUsername.visibility = View.GONE
        }
        binding.BTUpdate.setOnClickListener {
            val email = binding.ETEmail.text.toString()
            if(email != currentUser.email){
                currentUser.updateEmail(email)
            }
            val username = binding.ETUsername.text.toString()
            if (username != currentUser.displayName){
                val profileUpdates = userProfileChangeRequest {
                    displayName = username
                }
                currentUser.updateProfile(profileUpdates).addOnSuccessListener {
                    Toast.makeText(
                        baseContext,
                        "Profile mis à jour",
                        Toast.LENGTH_SHORT
                    ).show()
                    binding.TVUsername.visibility = View.GONE
                }.addOnFailureListener {
                    Toast.makeText(
                        baseContext,
                        "Erreur",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }




    }

}
package com.example.applicationdeseb

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.applicationdeseb.databinding.ActivityQuizBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class QuizActivity : AppCompatActivity() {
    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityQuizBinding

    private var cpt = 0 // Compteur de bonne réponse
    var listQuestion = arrayListOf<Question>()
    var qencours = 0 //Index de la question en cours dans listQuestion de 0 à listQuestion.size = au nombre de questions

    class Question(
        val Enonce: String,
        val Reponse: ArrayList<String>,
        var bonnereponse: Int,
    )

    class Score(
        val username: String,
        //val userId: String, // Identifiant unique de l'user connecté
        val score: Int //Le compteur de bonne réponse va être stocker dans cette variable
    )

    private fun toastcpt(reponse: Boolean){
        if(reponse){
            Toast.makeText(this,
                "Bonne reponse", Toast.LENGTH_SHORT).show()
            cpt ++
            binding.tvautre.text = cpt.toString()
        }
        else{
            Toast.makeText(this,
                "Mauvaise reponse", Toast.LENGTH_SHORT).show()
        }
        qencours ++
        if (qencours<listQuestion.size)
        {
            chargeQuestion(listQuestion[qencours])
        }
        else {
            val currentUser = auth.currentUser
            val userId = currentUser!!.uid
            val username = currentUser.displayName.toString()
            val newScore = Score(username, cpt)


            val refScore = database.child("Scores").child(userId)
            refScore.child("score").get().addOnSuccessListener {
                if (it.value != null){
                        if (it.value.toString().toInt() < cpt ) {
                            refScore.setValue(newScore)
                        }
                }
                else{
                    refScore.setValue(newScore)
                }
            }

            val builder = AlertDialog.Builder(this)
            builder.setTitle("Bravo $username Votre Score est de $cpt")
            builder.setCancelable(false) // Oblige le clique sur un bouton
            builder.setPositiveButton("Oui") { _, _ ->
                qencours = 0
                cpt = 0
                binding.tvautre.text = cpt.toString()
                chargeQuestion(listQuestion[qencours])
            }
            builder.show()
        }
    }

    fun chargeQuestion(q:Question){
        binding.tvenonce.text = q.Enonce
        binding.btrep1.text = q.Reponse[0]
        binding.btrep2.text = q.Reponse[1]
        binding.btrep3.text = q.Reponse[2]
        binding.btrep4.text = q.Reponse[3]

        binding.btrep1.setOnClickListener { toastcpt(binding.btrep1.text == q.Reponse[q.bonnereponse] ) }
        binding.btrep2.setOnClickListener { toastcpt(binding.btrep2.text == q.Reponse[q.bonnereponse] ) }
        binding.btrep3.setOnClickListener { toastcpt(binding.btrep3.text == q.Reponse[q.bonnereponse] ) }
        binding.btrep4.setOnClickListener { toastcpt(binding.btrep4.text == q.Reponse[q.bonnereponse] ) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val tvCpt = findViewById<TextView>(R.id.tvautre)
        tvCpt.text = ""
        auth = Firebase.auth

        database = Firebase.database("https://applicationdeseb-default-rtdb.firebaseio.com/").reference
        val listeQuestionReference = database.child("ListeQuestion")
        listeQuestionReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(listequestion: DataSnapshot) {
                for (question in listequestion.children) {
                    val tableauReponse = arrayListOf<String>()
                    for (reponse in question.child("Reponse").children) {
                        tableauReponse.add(reponse.value.toString())
                    }
                    val q=Question(question.child("Enonce").value.toString(),
                        tableauReponse,question.child("BonneReponse").value.toString().toInt())
                    listQuestion.add(q)
                }
                chargeQuestion(listQuestion[qencours])
                binding.LQLoading1.visibility = View.GONE
                binding.LQLoading2.visibility = View.GONE
                binding.LQQuestion.visibility = View.VISIBLE
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        }
        )

    }
}
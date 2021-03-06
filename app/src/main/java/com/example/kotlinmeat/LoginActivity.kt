package com.example.kotlinmeat

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlinmeat.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity: AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

       override fun onCreate(savedInstanceState: Bundle?){
       super.onCreate(savedInstanceState)
       binding = ActivityLoginBinding.inflate(layoutInflater)
       setContentView(binding.root)

       binding.loginButton.setOnClickListener{
           val email = binding.emailTexteditLog.text.toString()
           val password = binding.passwordTexteditLog.text.toString()

           Log.d("Login","Attemp to login with email/pw: $email/***")


            FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password)
                .addOnCompleteListener{
                    if(!it.isSuccessful) {return@addOnCompleteListener}
                Log.d("Login","User with uid: ${it.result?.user?.uid} has successfully logged in")
                    val intent = Intent(this,MainScreenActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                }
                .addOnFailureListener{
                    Log.d("Login", "Failed to log in: ${it.message}")
                }
       }



       binding.backToRegTextLogin.setOnClickListener{
           Log.d("Login","Try to get back to registration")
           finish()
       }
}
}
package com.example.smelldc

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.example.smelldc.ui.profile.User

import com.google.firebase.auth.FirebaseAuth

class RegistrationActivity : AppCompatActivity() {

    private var emailTV: EditText? = null
    private var passwordTV: EditText? = null
    private var regBtn: Button? = null
    private var progressBar: ProgressBar? = null

    private var mAuth: FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        mAuth = FirebaseAuth.getInstance()

        initializeUI()

        regBtn!!.setOnClickListener { registerNewUser() }
    }

    private fun registerNewUser() {
        progressBar!!.visibility = View.VISIBLE

        val email: String
        val password: String
        email = emailTV!!.text.toString()
        password = passwordTV!!.text.toString()

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(applicationContext, "Please enter email...", Toast.LENGTH_LONG).show()
            return
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(applicationContext, "Please enter password!", Toast.LENGTH_LONG).show()
            return
        }

        mAuth!!.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(applicationContext, "Registration successful!", Toast.LENGTH_LONG).show()
                    progressBar!!.visibility = View.GONE

                    val intent = Intent(this@RegistrationActivity, LoginActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(applicationContext, "Registration failed: " + task.exception?.message, Toast.LENGTH_LONG).show()
                    progressBar!!.visibility = View.GONE
                }
            }
    }

    private fun initializeUI() {
        emailTV = findViewById(R.id.email)
        passwordTV = findViewById(R.id.password)
        regBtn = findViewById(R.id.register)
        progressBar = findViewById(R.id.progressBar)
    }
}

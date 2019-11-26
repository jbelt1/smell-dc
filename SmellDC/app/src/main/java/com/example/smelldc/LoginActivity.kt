package com.example.smelldc

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class LoginActivity : AppCompatActivity() {
    private var mDatabaseReference: DatabaseReference? = null
    private var mDatabase: FirebaseDatabase? = null
    private var userEmail: EditText? = null
    private var userPassword: EditText? = null
    private var loginBtn: Button? = null
    private var progressBar: ProgressBar? = null

    private var mAuth: FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mDatabase = FirebaseDatabase.getInstance()
        mDatabaseReference = mDatabase!!.reference!!.child("Users")
        mAuth = FirebaseAuth.getInstance()

        initializeUI()

        loginBtn!!.setOnClickListener { loginUserAccount() }
    }

    private fun loginUserAccount() {
        progressBar!!.visibility = View.VISIBLE

        val email = userEmail?.text.toString()
        val password = userPassword?.text.toString()

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            return
        }

        mAuth?.signInWithEmailAndPassword(email, password)?.addOnCompleteListener {
                task ->
            if (task.isSuccessful) {
                val intent = Intent(this, DashboardActivity::class.java)

                intent.putExtra("userID", task.result?.user?.uid)
                intent.putExtra("name", task.result?.user?.displayName)

                startActivity(intent)
            } else {
                Toast.makeText(applicationContext, "Login failed: " + task.exception?.message, Toast.LENGTH_LONG).show()
                progressBar!!.visibility = View.GONE
            }
        }

    }

    private fun initializeUI() {
        userEmail = findViewById(R.id.email)
        userPassword = findViewById(R.id.password)

        loginBtn = findViewById(R.id.login)
        progressBar = findViewById(R.id.progressBar)
    }
}

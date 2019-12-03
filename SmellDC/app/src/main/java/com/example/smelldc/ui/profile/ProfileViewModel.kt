package com.example.smelldc.ui.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.smelldc.ui.map.MapViewModel
import com.example.smelldc.ui.map.Report
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ProfileViewModel : ViewModel() {

    private val db = FirebaseFirestore.getInstance()
    private val user: MutableLiveData<User> by lazy {
        MutableLiveData<User>().also {
            loadUser()
        }
    }

    fun getUser(): LiveData<User> {
        return user
    }

    private fun loadUser() {
        val currentFirebaseUser = FirebaseAuth.getInstance().currentUser
        db.collection("users")
            .document(currentFirebaseUser!!.email.toString())
            .get()
            .addOnSuccessListener { result ->
                user.value = result.toObject(User::class.java)
            }
            .addOnFailureListener { exception ->
                Log.w("Smell-DC", "Error getting users.", exception)
            }
    }

}
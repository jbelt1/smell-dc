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
            loadUsers()
        }
    }

    fun getUser(): LiveData<User> {
        return user
    }

    private fun loadUsers() {
        val currentFirebaseUser = FirebaseAuth.getInstance().currentUser
        db.collection("reports")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    var currUser = document.toObject(User::class.java)
                    if (currUser == currentFirebaseUser?.uid) {
                      user.value = currUser
                    }
                }
            }
            .addOnFailureListener { exception ->
                Log.w("Smell-DC", "Error getting users.", exception)
            }
    }

}
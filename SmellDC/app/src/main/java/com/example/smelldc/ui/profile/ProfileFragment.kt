package com.example.smelldc.ui.profile

import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.smelldc.DashboardActivity
import com.example.smelldc.R
import com.example.smelldc.ui.map.Report
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.GeoPoint

class ProfileFragment : Fragment() {

    private lateinit var notificationsViewModel: ProfileViewModel
    private var mSubmitBtn: Button? = null
    private val db = FirebaseFirestore.getInstance()
    private var mFirstName: EditText? = null
    private var mLastName: EditText? = null
    private var mAge: EditText? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        notificationsViewModel =
                ViewModelProviders.of(this).get(ProfileViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_profile, container, false)

        val currentFirebaseUser = FirebaseAuth.getInstance().currentUser
        mSubmitBtn = root.findViewById<Button>(R.id.submitProfileBtn) as Button
        mFirstName = root.findViewById<EditText>(R.id.firstName) as EditText
        mLastName = root.findViewById<EditText>(R.id.lastName) as EditText
        mAge = root.findViewById<EditText>(R.id.age) as EditText
        mAge?.setText(currentFirebaseUser?.uid.toString())

        mSubmitBtn?.setOnClickListener {
            val activity = activity as DashboardActivity
            activity.getCurrentLocation()?.addOnSuccessListener { location : Location? ->
                // Got last known location. In some rare situations this can be null.
                val user = User(mFirstName?.text.toString(), mLastName?.text.toString(), mAge?.text.toString().toInt())

                db.collection("users").document("A").set(user)
                    .addOnSuccessListener {
                        Log.i("SMELL-DC", "Document updated")
                    }
                    .addOnFailureListener { e ->
                        Log.i("SMELL-DC", "Error adding document", e)
                    }

                // save report data to db and update global data structure
                Toast.makeText(activity.applicationContext, "Profile updated!", Toast.LENGTH_LONG).show()
            }
        }

        return root
    }
}
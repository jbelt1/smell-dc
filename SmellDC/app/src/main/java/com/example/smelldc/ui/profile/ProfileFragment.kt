package com.example.smelldc.ui.profile

import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
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

    private lateinit var profileViewModel: ProfileViewModel
    private var mSubmitBtn: Button? = null
    private val db = FirebaseFirestore.getInstance()
    private var mFirstName: EditText? = null
    private var mLastName: EditText? = null
    private var mAge: EditText? = null
    private var progress: ProgressBar? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        profileViewModel =
                ViewModelProviders.of(this).get(ProfileViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_profile, container, false)

        val currentFirebaseUser = FirebaseAuth.getInstance().currentUser
        mSubmitBtn = root.findViewById(R.id.submitProfileBtn) as Button
        mFirstName = root.findViewById(R.id.firstName) as EditText
        mLastName = root.findViewById(R.id.lastName) as EditText
        mAge = root.findViewById(R.id.age) as EditText
        progress = root.findViewById(R.id.progressBar) as ProgressBar
        mSubmitBtn?.setOnClickListener {
            val activity = activity as DashboardActivity

                // Got last known location. In some rare situations this can be null.
                val user = User(mFirstName?.text.toString(), mLastName?.text.toString(),
                    mAge?.text.toString().toInt(), currentFirebaseUser!!.email)

                db.collection("users")
                    .document(currentFirebaseUser.email.toString()).set(user)
                    .addOnSuccessListener {
                        Log.i("SMELL-DC", "Document updated")
                    }
                    .addOnFailureListener { e ->
                        Log.i("SMELL-DC", "Error adding document", e)
                    }

                // save report data to db and update global data structure
                Toast.makeText(activity.applicationContext, "Profile updated!", Toast.LENGTH_LONG).show()
        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        profileViewModel.getUser().observe(this, Observer<User>{user ->
            mFirstName!!.setText(user.firstName)
            mLastName!!.setText(user.lastName)
            mAge!!.setText(user.age.toString())
            progress!!.visibility = View.GONE
        })
    }
}
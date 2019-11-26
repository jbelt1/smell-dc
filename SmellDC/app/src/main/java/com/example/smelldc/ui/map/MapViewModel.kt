package com.example.smelldc.ui.map

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore

class MapViewModel : ViewModel() {

    private val db = FirebaseFirestore.getInstance()
    private val reports: MutableLiveData<List<Report>> by lazy {
            MutableLiveData<List<Report>>().also {
                loadReports()
            }
        }

    fun getReports(): LiveData<List<Report>> {
        return reports
    }

    private fun loadReports() {
        val currReports = ArrayList<Report>()
        db.collection("reports")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    var report = document.toObject(Report::class.java)
                    currReports.add(report)
                }
                reports.value = currReports
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }
    }

    companion object {
        private val TAG ="report"
    }
}
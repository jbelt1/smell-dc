package com.example.smelldc.ui.report

import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.smelldc.DashboardActivity
import com.example.smelldc.R
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class ReportFragment : Fragment() {

    private lateinit var dashboardViewModel: ReportViewModel

    private var mAirQuality: SeekBar? = null
    private var mSymptoms: EditText? = null
    private var mActivity: EditText? = null
    private var mSendToHealthDpt: CheckBox? = null
    private var mSubmitBtn: Button? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel =
                ViewModelProviders.of(this).get(ReportViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_report, container, false)

        mSubmitBtn = root.findViewById<Button>(R.id.submitBtn) as Button
        mSubmitBtn?.setOnClickListener {
            val activity = activity as DashboardActivity
            activity.getCurrentLocation()?.addOnSuccessListener { location : Location? ->
                // Got last known location. In some rare situations this can be null.

                // save report data to db and update global data structure
                Log.i("SMELL-DC", "Latitude: " + location?.latitude)
            }
        }

        return root
    }

}
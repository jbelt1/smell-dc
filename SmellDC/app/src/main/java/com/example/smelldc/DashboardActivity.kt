package com.example.smelldc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.SeekBar

class DashboardActivity : AppCompatActivity() {

    private var mAirQuality: SeekBar? = null
    private var mSymptoms: EditText? = null
    private var mActivity: EditText? = null
    private var mSendToHealthDpt: CheckBox? = null
    private var mSubmitBtn: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        mAirQuality = findViewById(R.id.seekBar)
        mSymptoms = findViewById(R.id.symptoms)
        mActivity = findViewById(R.id.activity)
        mSendToHealthDpt = findViewById(R.id.checkBox)
        mSubmitBtn = findViewById(R.id.submitBtn)
    }

}

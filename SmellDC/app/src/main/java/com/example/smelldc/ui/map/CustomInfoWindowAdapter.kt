package com.example.smelldc.ui.map
import com.example.smelldc.R
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker

class CustomInfoWindowAdapter(context: Context?) : GoogleMap.InfoWindowAdapter {

    private val mWindow = LayoutInflater.from(context)
        .inflate(R.layout.custom_info_window, null)
    private val mContents = LayoutInflater.from(context)
        .inflate(R.layout.custom_info_contents, null)

    private fun renderWindowText(marker: Marker, view: View) {
        val title = marker.title
        val tvTitle = view.findViewById<TextView>(R.id.title)

        if (title != ""){
            tvTitle.text = title
        }

        val snippet = marker.snippet
        val tvSnippet = view.findViewById<TextView>(R.id.snippet)

        if (snippet != ""){
            tvSnippet.text = snippet
        }
    }

    override fun getInfoWindow(marker: Marker): View {
        renderWindowText(marker, mWindow)
        return mWindow
    }

    override fun getInfoContents(marker: Marker): View {
        renderWindowText(marker, mContents)
        return mContents
    }

}
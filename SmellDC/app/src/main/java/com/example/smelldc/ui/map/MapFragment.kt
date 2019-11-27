package com.example.smelldc.ui.map

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.smelldc.R
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.CameraPosition

class MapFragment : Fragment(), OnMapReadyCallback {

    private lateinit var mapViewModel: MapViewModel
    private lateinit var mGoogleMap: GoogleMap
    private lateinit var mView: View
    private lateinit var mMapView: MapView


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mapViewModel =
                ViewModelProviders.of(this).get(MapViewModel::class.java)
        mView = inflater.inflate(R.layout.fragment_map, container, false)

        Log.i("report", "in MapFragment")
        mapViewModel.getReports().observe(this, Observer<List<Report>>{reports ->
            Log.i("report", "current list of reports" + reports.toString())
        })
        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mMapView = mView.findViewById(R.id.map) as MapView
        if (mMapView != null) {
            mMapView.onCreate(null)
            mMapView.onResume()
            mMapView.getMapAsync(this)
        }
    }

    override fun onMapReady(map: GoogleMap) {
        MapsInitializer.initialize(context)

        mGoogleMap = map

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        mGoogleMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }
}
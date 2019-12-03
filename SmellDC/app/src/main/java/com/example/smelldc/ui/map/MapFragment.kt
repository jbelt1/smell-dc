package com.example.smelldc.ui.map

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.smelldc.R
import com.google.android.gms.maps.*
import com.google.android.gms.maps.GoogleMap.OnMapClickListener
import com.google.android.gms.maps.model.*

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
        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val myLayout = mView.findViewById(R.id.mapcontainer) as LinearLayout
        val options = GoogleMapOptions()
        options.mapType(GoogleMap.MAP_TYPE_NORMAL).liteMode(true)
        val dc = LatLng(38.9072, -77.0369)
        options.camera(CameraPosition.fromLatLngZoom(dc,12.2f))
        mMapView = MapView(context, options)
        mMapView.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT)
        myLayout.addView(mMapView)
        mMapView.onCreate(null)
        mMapView.onResume()
        mMapView.getMapAsync(this)
    }

    override fun onMapReady(map: GoogleMap) {
        mapViewModel.getReports().observe(this, Observer<List<Report>>{reports ->
            MapsInitializer.initialize(context)
            mGoogleMap = map
            mGoogleMap.setOnMapClickListener { null
            }
            mGoogleMap.setInfoWindowAdapter(CustomInfoWindowAdapter(context))
            for (report in reports) {
                Log.i("report", "current report " + report)
                val color = when(report.quality) {
                    1 -> BitmapDescriptorFactory.HUE_RED
                    2 -> BitmapDescriptorFactory.HUE_ORANGE
                    3 -> BitmapDescriptorFactory.HUE_YELLOW
                    4 -> BitmapDescriptorFactory.HUE_GREEN
                    5 -> BitmapDescriptorFactory.HUE_BLUE
                    else -> BitmapDescriptorFactory.HUE_CYAN
                }

                mGoogleMap.addMarker(MarkerOptions()
                    .position(LatLng(report.location.latitude,
                        report.location.longitude))
                    .title(report.odor)
                    .snippet("Symptoms: ${report.symptoms} \n Activity: ${report.activity} \n Air Quality: ${report.quality}")
                    .icon(BitmapDescriptorFactory.defaultMarker(color)))
            }
        })

    }
}
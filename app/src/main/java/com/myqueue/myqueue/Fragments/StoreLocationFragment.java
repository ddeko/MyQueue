package com.myqueue.myqueue.Fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.myqueue.myqueue.Activities.BookActivity;
import com.myqueue.myqueue.Activities.ProfileActivity;
import com.myqueue.myqueue.R;
import com.myqueue.myqueue.Views.CustomMapView;

/**
 * Created by 高橋六羽 on 2016/03/22.
 */
public class StoreLocationFragment extends BaseFragment implements View.OnClickListener{

    private Button updatebtn;

    private EditText streetName;
    private EditText number;
    private EditText city;

    public String Address;

    Bundle saveInstanceState;

    private GoogleMap gMap; // Might be null if Google Play services APK is not available.
    private CustomMapView mapView;
    private double currentLatitude;
    private double currentLongitude;


    @Override
    public void onCreate(Bundle savedInstanceState) {

        currentLatitude = -7.7663509;
        currentLongitude = 110.405084;



    }

    private void setupActionBar() {
        ProfileActivity mainActivity = (ProfileActivity)getActivity();
        mainActivity.setDefaultActionbarIcon();
        mainActivity.setRightIcon(0);
        mainActivity.setActionBarTitle(getPageTitle());
    }

    @Override
    public void onClick(View v) {
        if(v == updatebtn)
        {
            Toast.makeText(getActivity(), "Shop Address Updated", Toast.LENGTH_SHORT).show();
//            LocationPickerFragment locPic = new LocationPickerFragment() {
//                @Override
//                protected void onLocationSelected(String street, String country, Double latitude, Double longitude) {
//                    lastCountry = currentCountry;
//                    currentCountry = country;
//
//                    currentStreet = street;
//                    currentLongitude = longitude;
//                    currentLatitude = latitude;
//                    isBackFromLocationPicker = true;
//                }
//            };
//
//            replaceFragment(R.id.fragment_container, locPic, true);
//            getFragmentManager().executePendingTransactions();
//            locPic.setInitialLocation(currentStreet, currentCountry, currentLatitude, currentLongitude);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    private void initMap(View view) {
        try {
            MapsInitializer.initialize(getActivity());
        }
        catch(Exception e) {

        }

        switch(GooglePlayServicesUtil.isGooglePlayServicesAvailable(getActivity())) {
            case ConnectionResult.SUCCESS:
                mapView = (CustomMapView)view.findViewById(R.id.store_map);
                mapView.onCreate(saveInstanceState);

                // Gets to GoogleMap from the MapView and does initialization stuff
                if(mapView != null) {
                    gMap = mapView.getMap();
                    gMap.getUiSettings().setMyLocationButtonEnabled(false);
                    gMap.setMyLocationEnabled(false);

                    gMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
                        @Override
                        public View getInfoWindow(Marker marker) {
                            ContextThemeWrapper cw = new ContextThemeWrapper(getActivity(), R.style.marker_style);
                            LayoutInflater inflater = (LayoutInflater) cw.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
                            return inflater.inflate(R.layout.marker_layout, null);
                        }

                        @Override
                        public View getInfoContents(Marker marker) {
                            return null;
                        }
                    });

                }
                break;

            case ConnectionResult.SERVICE_MISSING:
                Toast.makeText(getActivity(), "GooglePlay Service is missing", Toast.LENGTH_SHORT).show();
                break;

            case ConnectionResult.SERVICE_VERSION_UPDATE_REQUIRED:
                Toast.makeText(getActivity(), "GooglePlay Service require a latest version", Toast.LENGTH_SHORT).show();
                break;

            default:
                Toast.makeText(getActivity(), GooglePlayServicesUtil.isGooglePlayServicesAvailable(getActivity()), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void initView(View view) {

        initMap(view);

        updatebtn = (Button)view.findViewById(R.id.btnUpdateStore);

        updatebtn.setOnClickListener(this);

    }

    @Override
    public void setUICallbacks() {

    }

    @Override
    public void updateUI() {

        setupActionBar();

        gMap.getUiSettings().setScrollGesturesEnabled(true);

        if(currentLatitude != 0 && currentLongitude != 0) {
            gMap.clear();


            Marker marker = gMap.addMarker(new MarkerOptions()
                    .title("Shop Location")
                    .position(new LatLng(currentLatitude, currentLongitude))
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.pin_red)));


            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(new LatLng(currentLatitude, currentLongitude))
                    .zoom(14)
                    .tilt(30)
                    .build();

            gMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

            marker.showInfoWindow();
        }
    }

    @Override
    public String getPageTitle() {
        return "Shop Address";
    }

    @Override
    public int getFragmentLayout() {
        return R.layout.fragment_store_location;
    }
}

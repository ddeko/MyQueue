package com.myqueue.myqueue.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
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
import com.myqueue.myqueue.R;
import com.myqueue.myqueue.Views.CustomMapView;

/**
 * Created by 高橋六羽 on 2016/03/22.
 */
public class DetailShopFragment extends Fragment implements View.OnClickListener{

    private FloatingActionButton toBookbtn;
    private ImageView coverImage;
    private TextView addressText;
    private TextView nameText;

    Bundle saveInstanceState;

    private GoogleMap gMap; // Might be null if Google Play services APK is not available.
    private CustomMapView mapView;
    private double currentLatitude;
    private double currentLongitude;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_detailshop, container, false);

        setupActionBar();

        coverImage = (ImageView)v.findViewById(R.id.shopdetailpic);
        addressText = (TextView)v.findViewById(R.id.txtAlamatShop);
        nameText = (TextView)v.findViewById(R.id.txtNamaShop);
        toBookbtn = (FloatingActionButton)v.findViewById(R.id.btnBook);
        toBookbtn.setOnClickListener(this);

        addressText.setText(((BookActivity) getActivity()).getResponseInfo().getAddress() + " " + ((BookActivity) getActivity()).getResponseInfo().getNumber());
        nameText.setText(((BookActivity) getActivity()).getResponseInfo().getUser().get(0).getName());
        Glide.with(getContext()).load(((BookActivity) getActivity()).getResponseInfo().getUser().get(0).getCoverphoto()).into(coverImage);
        coverImage.setScaleType(ImageView.ScaleType.FIT_XY);

        currentLatitude = Double.valueOf(((BookActivity) getActivity()).getResponseInfo().getLatitude());
        currentLongitude = Double.valueOf(((BookActivity) getActivity()).getResponseInfo().getLongitude());

        initMap(v);

        gMap.getUiSettings().setScrollGesturesEnabled(true);

        if(currentLatitude != 0 && currentLongitude != 0) {
            gMap.clear();

            /*
            Marker marker = gMap.addMarker(new MarkerOptions()
                    .title("Shop Location")
                    .position(new LatLng(currentLatitude, currentLongitude))
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.kfclogo)));
            */

            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(new LatLng(currentLatitude, currentLongitude))
                    .zoom(14)
                    .tilt(30)
                    .build();

            gMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

            Marker marker = gMap.addMarker(new MarkerOptions().position(new LatLng(currentLatitude, currentLongitude)).title("Shop Location"));

            marker.showInfoWindow();
        }

        return v;
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



    private void setupActionBar() {
        BookActivity mainActivity = (BookActivity)getActivity();
        mainActivity.setDefaultActionbarIcon();
    }

    @Override
    public void onClick(View v) {
        if(v==toBookbtn)
        {
            BookScreenFragment myf = new BookScreenFragment();

            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.fragment_container, myf);
            transaction.addToBackStack("Detail");
            transaction.commit();

        }
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

}

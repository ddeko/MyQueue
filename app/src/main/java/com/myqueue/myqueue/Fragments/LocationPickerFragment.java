package com.myqueue.myqueue.Fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.myqueue.myqueue.APIs.TaskGetReverseRoute;
import com.myqueue.myqueue.Callbacks.OnActionbarListener;
import com.myqueue.myqueue.R;
import com.myqueue.myqueue.Views.CustomMapView;


/**
 * Created by dedeeko on 8/6/15.
 */
public abstract class LocationPickerFragment extends BaseFragment {
    private CustomMapView mapView;
    private GoogleMap mMap;

    private EditText searchStreetTxt;
    private RelativeLayout pickLocBtn;
    private TextView streetName;
    private ProgressBar searchProgressBar;

    private LatLng currentLocation;
    private String currentCountry;
    private String currentStreet;
    private boolean isMapDrag;
    private boolean isFirstTime;

    private AsyncTask<Void, Void, Void> dragTask;

    public void setInitialLocation(String street, String country, Double latitude, Double longitude){
        this.currentStreet = street;
        this.currentCountry = country;
        this.currentLocation = new LatLng(latitude, longitude);
        this.isFirstTime = false;

        updateUI();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isMapDrag = false;
        isFirstTime = true;
    }

    @Override
    public void initView(View view) {
        initMap(view);

        searchStreetTxt = (EditText) view.findViewById(R.id.add_location_search_text);
        pickLocBtn = (RelativeLayout) view.findViewById(R.id.add_location_btn);
        streetName = (TextView) view.findViewById(R.id.add_location_street_name);
        searchProgressBar = (ProgressBar) view.findViewById(R.id.add_location_progress_bar);
    }

    // initialize map view
    private void initMap(View view){
        try {
            MapsInitializer.initialize(getActivity());
        }
        catch(Exception e) {

        }

        switch(GooglePlayServicesUtil.isGooglePlayServicesAvailable(getActivity())) {
            case ConnectionResult.SUCCESS:
                mapView = (CustomMapView)view.findViewById(R.id.add_store_map);
                mapView.onCreate(saveInstanceState);

                // Gets to GoogleMap from the MapView and does initialization stuff
                if(mapView != null) {
                    mMap = mapView.getMap();
                    mMap.getUiSettings().setMyLocationButtonEnabled(false);
                    mMap.setMyLocationEnabled(false);
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

    @Override
    public void setUICallbacks() {
        pickLocBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!currentStreet.isEmpty()) {
                    if(searchProgressBar.getVisibility() == View.GONE) {
                        currentLocation = mMap.getCameraPosition().target;
                        onLocationSelected(currentStreet, currentCountry, currentLocation.latitude, currentLocation.longitude);
                        getActivity().onBackPressed();
                    }
                    else
                        Toast.makeText(getActivity(), "Please wait while acquiring the address", Toast.LENGTH_LONG).show();
                }
                else
                    Toast.makeText(getActivity(), "Cannot acquire address", Toast.LENGTH_LONG).show();
            }
        });

        streetName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!currentStreet.isEmpty()) {
                    if(searchProgressBar.getVisibility() == View.GONE) {
                        currentLocation = mMap.getCameraPosition().target;
                        onLocationSelected(currentStreet, currentCountry, currentLocation.latitude, currentLocation.longitude);
                        getActivity().onBackPressed();
                    }
                    else
                        Toast.makeText(getActivity(), "Please wait while acquiring the address", Toast.LENGTH_LONG).show();
                }
                else
                    Toast.makeText(getActivity(), "Cannot acquire address", Toast.LENGTH_LONG).show();
            }
        });

        searchStreetTxt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if(!searchStreetTxt.getText().toString().isEmpty()) {
//                        SearchLocationDialog addressSelector = new SearchLocationDialog() {
//                            @Override
//                            protected void setLocation(APILocationResult model) {
//                                currentLocation = model.getLocation();
//
//                                CameraPosition cameraPosition = new CameraPosition.Builder()
//                                        .target(currentLocation)
//                                        .zoom(18)
//                                        .tilt(30)
//                                        .build();
//
//                                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
//
//                                currentCountry = model.getCountry_name();
//                                currentStreet = model.getStreetName();
//
//                                if(!currentStreet.isEmpty()) {
//                                    searchProgressBar.setVisibility(View.GONE);
//                                    streetName.setVisibility(View.VISIBLE);
//                                    streetName.setText(currentStreet);
//                                }
//                                else
//                                    onSearchLocation(currentLocation.latitude, currentLocation.longitude);
//                            }
//                        };

                    }

                    return true;
                }

                return false;
            }
        });

        mapView.setListener(new CustomMapView.OnTouchListener() {
            @Override
            public void onTouchUp() {
                isMapDrag = false;

                dragTask = new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected Void doInBackground(Void... params) {
                        try {
                            Thread.sleep(3000);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void result) {
                        super.onPostExecute(result);

                        if (!isMapDrag) {
                            LatLng center = mMap.getCameraPosition().target;
                            onSearchLocation(center.latitude, center.longitude);
                        }
                    }

                    @Override
                    protected void onCancelled() {
                        super.onCancelled();

                        dragTask = null;
                    }
                };

                dragTask.execute();
            }

            @Override
            public void onTouchDown() {
                searchProgressBar.setVisibility(View.VISIBLE);
                streetName.setVisibility(View.GONE);
                isMapDrag = true;

                if (dragTask != null)
                    dragTask.cancel(true);
            }
        });

        getBaseActivity().setActionbarListener(new OnActionbarListener() {
            @Override
            public void onLeftIconClick() {
                getActivity().onBackPressed();
            }

            @Override
            public void onRightIconClick() {

            }
        });

    }

    @Override
    public String getPageTitle() {
        return "Pick Location";
    }

    @Override
    public int getFragmentLayout() {
        return R.layout.fragment_location_picker;
    }

    @Override
    public void updateUI() {
        if(!isFirstTime) {
            setupActionBar();

            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(currentLocation)
                    .zoom(18)
                    .tilt(30)
                    .build();

            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            mMap.getUiSettings().setZoomControlsEnabled(false);

            if(!currentStreet.isEmpty()) {
                streetName.setText(currentStreet);
                searchProgressBar.setVisibility(View.GONE);
                streetName.setVisibility(View.VISIBLE);
            }
            else {
                searchProgressBar.setVisibility(View.VISIBLE);
                streetName.setVisibility(View.GONE);
                onSearchLocation(currentLocation.latitude, currentLocation.longitude);
            }
        }
    }

    private void onSearchLocation(final double latitude, final double longitude){

        new TaskGetReverseRoute(getActivity()) {
            @Override
            protected void onSearchSuccess(String routeName, String countryName) {
                currentCountry = countryName;
                currentStreet = routeName;

                searchProgressBar.setVisibility(View.GONE);
                streetName.setVisibility(View.VISIBLE);
                streetName.setText(currentStreet);

                currentLocation = new LatLng(latitude, longitude);
            }

            @Override
            protected void onSearchFailed(String message) {
                Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
                searchProgressBar.setVisibility(View.GONE);
                streetName.setVisibility(View.VISIBLE);
                streetName.setText("Cannot acquire address");
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new LatLng(latitude, longitude));
    }

    private void setupActionBar() {
        getBaseActivity().setDefaultActionbarIcon();
        getBaseActivity().setRightIcon(0);
        getBaseActivity().setLeftIcon(R.drawable.back);
        getBaseActivity().setActionBarTitle(getPageTitle());
    }

    protected abstract void onLocationSelected(String street,String country,Double latitude,Double longitude);
}

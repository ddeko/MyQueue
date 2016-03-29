package com.myqueue.myqueue.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
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
import com.myqueue.myqueue.APIs.TaskAddShop;
import com.myqueue.myqueue.APIs.TaskEditShop;
import com.myqueue.myqueue.APIs.TaskGetUser;
import com.myqueue.myqueue.Activities.HomeActivity;
import com.myqueue.myqueue.Activities.ProfileActivity;
import com.myqueue.myqueue.Callbacks.OnActionbarListener;
import com.myqueue.myqueue.Models.APIAddShopRequest;
import com.myqueue.myqueue.Models.APIBaseResponse;
import com.myqueue.myqueue.Models.APIEditShopRequest;
import com.myqueue.myqueue.Models.APILoginRequest;
import com.myqueue.myqueue.Models.APILoginResponse;
import com.myqueue.myqueue.Models.Shop;
import com.myqueue.myqueue.Models.User;
import com.myqueue.myqueue.Preferences.SessionManager;
import com.myqueue.myqueue.R;
import com.myqueue.myqueue.Views.CustomMapView;

import java.util.HashMap;

/**
 * Created by 高橋六羽 on 2016/03/22.
 */
public class StoreLocationFragment extends BaseFragment implements View.OnClickListener{


    private boolean isBackFromLocationPicker;

    private Button updatebtn;

    private EditText streetName;
    private EditText number;
    private EditText city;
    TextInputLayout streetNameInput;
    TextInputLayout numberInput;

    private SessionManager sessions;
    private HashMap<String,String> shopdata;
    private HashMap<String,String> userdata;
    User loginuser;
    Shop loginshopdata;
    private Boolean isFirsttime;

    Bundle saveInstanceState;

    private GoogleMap gMap; // Might be null if Google Play services APK is not available.
    private CustomMapView mapView;
    private double currentLatitude;
    private double currentLongitude;
    private String currentCity;
    private String currentStreet;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sessions = new SessionManager(getActivity());
        shopdata = sessions.getShopDetails();
        userdata = sessions.getUserDetails();

        currentLatitude = shopdata.get(SessionManager.KEY_LATITUDE)!=null? Double.valueOf(shopdata.get(SessionManager.KEY_LATITUDE)) : 0;
        currentLongitude = shopdata.get(SessionManager.KEY_LATITUDE)!=null? Double.valueOf(shopdata.get(SessionManager.KEY_LATITUDE)) : 0;

        isBackFromLocationPicker = false;
        isFirsttime = shopdata.get(SessionManager.KEY_ADDRESS)==null;

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
            if(checkFields()) {

                if(isFirsttime) {

                    APIAddShopRequest request = new APIAddShopRequest();
                    request.setAddress(currentStreet);
                    request.setLatitude(String.valueOf(currentLatitude));
                    request.setLongitude(String.valueOf(currentLongitude));
                    request.setNumber(number.getText().toString());
                    request.setUserid(userdata.get(SessionManager.KEY_USERID));

                    TaskAddShop addShop = new TaskAddShop(getActivity()) {

                        @Override
                        public void onResult(APIBaseResponse response, String statusMessage, boolean isSuccess) {

                            if (isSuccess) {

                                redirectTo();

                            } else {
                                Toast.makeText(getActivity(), statusMessage, Toast.LENGTH_SHORT).show();
                            }

                        }
                    };
                    addShop.execute(request);
                }
                else
                {
                    APIEditShopRequest request = new APIEditShopRequest();
                    request.setAddress(currentStreet);
                    request.setLatitude(String.valueOf(currentLatitude));
                    request.setLongitude(String.valueOf(currentLongitude));
                    request.setNumber(number.getText().toString());
                    request.setUserid(userdata.get(SessionManager.KEY_USERID));
                    request.setUserid(shopdata.get(SessionManager.KEY_ISFULL));

                    TaskEditShop editShop = new TaskEditShop(getActivity()) {

                        @Override
                        public void onResult(APIBaseResponse response, String statusMessage, boolean isSuccess) {

                            if (isSuccess) {

                                redirectTo();

                            } else {
                                Toast.makeText(getActivity(), statusMessage, Toast.LENGTH_SHORT).show();
                            }

                        }
                    };
                    editShop.execute(request);
                }
            }

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

        streetName = (EditText)view.findViewById(R.id.txtStreetNameStore);
        number = (EditText)view.findViewById(R.id.txtHouseNumberStore);
        city = (EditText)view.findViewById(R.id.txtCity);
        streetNameInput = (TextInputLayout) view.findViewById(R.id.streetNameWrapper);
        numberInput = (TextInputLayout)view.findViewById(R.id.houseNumberWrapper);

        updatebtn = (Button)view.findViewById(R.id.btnUpdateStore);

        updatebtn.setOnClickListener(this);
        streetName.setOnClickListener(this);

        if(shopdata.get(SessionManager.KEY_ADDRESS)!=null)
            streetName.setText(shopdata.get(SessionManager.KEY_ADDRESS));
        if(shopdata.get(SessionManager.KEY_NUMBER)!=null)
            number.setText(shopdata.get(SessionManager.KEY_NUMBER));

    }

    @Override
    public void setUICallbacks() {
        streetName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocationPickerFragment locPic = new LocationPickerFragment() {
                    @Override
                    protected void onLocationSelected(String street, String city, Double latitude, Double longitude) {
                        currentStreet = street;
                        currentLongitude = longitude;
                        currentLatitude = latitude;
                        currentCity = city;
                        isBackFromLocationPicker = true;
                    }
                };

                replaceFragment(R.id.fragment_container, locPic, true);
                getFragmentManager().executePendingTransactions();
                locPic.setInitialLocation(currentStreet, currentCity, currentLatitude, currentLongitude);
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
                    .zoom(15)
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

    private boolean checkFields()
    {
        if (!validateNumber()) {
            return false;
        }

        if (!validateStreet()) {
            return false;
        }

        return true;
    }

    private boolean validateStreet() {
        if (streetName.getText().toString().trim().isEmpty()) {
            streetNameInput.setError("Please choose your Street");
            return false;
        } else {
            streetNameInput.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateNumber() {
        if (number.getText().toString().trim().isEmpty()) {
            numberInput.setError("Please fill in your House Number");
            requestFocus(streetName);
            return false;
        } else {
            numberInput.setErrorEnabled(false);
        }

        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

        if(isBackFromLocationPicker) {
            isBackFromLocationPicker = false;
            streetName.setText(currentStreet);
        }
    }

    private void redirectTo()
    {
        APILoginRequest request = new APILoginRequest();
        request.setEmail(userdata.get(SessionManager.KEY_EMAIL));
        request.setPassword(userdata.get(SessionManager.KEY_PASSWORD));

        TaskGetUser getUser = new TaskGetUser(getActivity()) {

            @Override
            public void onResult(APILoginResponse response, String statusMessage, boolean isSuccess) {

                if(isSuccess)
                {
                    Intent i = new Intent();
                    loginuser = response.getUser().get(0);
                    if(response.getShop().size()!=0)

                        loginshopdata = response.getShop().get(0);

                    sessions.createLoginSession(loginuser);
                    if(loginuser.getIsowner().equalsIgnoreCase("1")) {
                        sessions.setShopData(loginshopdata);

                        if(isFirsttime) {
                            isFirsttime=false;
                            i = new Intent(getActivity(), HomeActivity.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(i);
                        }
                    }

                    Toast.makeText(getActivity(), "Shop Address Updated", Toast.LENGTH_SHORT).show();

                }
                else
                {
                    Toast.makeText(getActivity(), statusMessage, Toast.LENGTH_SHORT).show();
                }

            }
        };
        getUser.execute(request);
    }


}

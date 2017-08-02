package com.example.richardssurface.mff;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.richardssurface.mff.Utilities.CustomDialogTool;
import com.example.richardssurface.mff.Utilities.JSONParsingTool;
import com.example.richardssurface.mff.Utilities.RestDataReceiver;
import com.example.richardssurface.mff.Utilities.Student;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;
import java.util.List;

import static com.example.richardssurface.mff.R.array.range_radius_spinner_array;

public class MapFragment extends Fragment implements GoogleMap.OnMarkerClickListener {

    MapView mMapView;
    private GoogleMap googleMap;
    private Student currentUser;
    private List<Student> userFriends;
    private Double rangeRadius = 99999.99;
    private Spinner range_choose;
    private boolean tag;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_maps, container, false);
        range_choose = (Spinner) rootView.findViewById(R.id.range_choose);
        SubscriptionFragment.populateDatainSpinner(range_choose, range_radius_spinner_array, getActivity());

        range_choose.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                rangeRadius = Double.parseDouble(range_choose.getSelectedItem().toString());

                mMapView.getMapAsync(new OnMapReadyCallback() {
                    @Override
                    public void onMapReady(GoogleMap mMap) {
                        googleMap = mMap;

                        googleMap.clear();
                        // For dropping a marker at a point on the Map
                        currentUser = LoginPage.getCurrentUser();
                        LatLng chadStonePosition = new LatLng(Double.parseDouble(MainFragment.getLatitude()), Double.parseDouble(MainFragment.getLongitude()));
                        googleMap.addMarker(new MarkerOptions().position(chadStonePosition).title(currentUser.getFName()).snippet("I am here!"));
                        //TODO bug here
                        if(tag){
                            userFriends = SearchFriendFragment.getFriends();
                        }else{
                            userFriends = AllFriendsFragment.getFriendObjectListArray();
                        }


                        if (userFriends != null) {
                            new AsyncTask<Void, Void, List<HashMap<String, String>>>() {

                                @Override
                                protected List<HashMap<String, String>> doInBackground(Void... params) {
                                    return RestDataReceiver.findNearbyFriends(currentUser, Double.parseDouble(MainFragment.getLatitude()), Double.parseDouble(MainFragment.getLongitude()), rangeRadius);
                                }

                                @Override
                                protected void onPostExecute(List<HashMap<String, String>> dataset) {
                                    showFriendinMap(dataset, userFriends);
                                }
                            }.execute();
                        } else {
                            Toast.makeText(getActivity(), "You don't have any friends to show, go and get some!",
                                    Toast.LENGTH_LONG).show();
                        }

                        // For zooming automatically to the location of the marker
                        CameraPosition cameraPosition = new CameraPosition.Builder().target(chadStonePosition).zoom(12).build();
                        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                rangeRadius = 5.0;
                range_choose.setSelection(4);
            }
        });
        mMapView = (MapView) rootView.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume();

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    public void showFriendinMap(List<HashMap<String, String>> dataset, List<Student> userFriends) {

        for (HashMap hm : dataset) {
            double friendLatitude = Double.parseDouble(hm.get("currentLatitude").toString());
            double friendLongitude = Double.parseDouble(hm.get("currentLongtitude").toString());
            String fName = (String) hm.get("fName");
            String lName = (String) hm.get("lName");
            for (Student s : userFriends) {
                if (s.getFName().equals(fName) && s.getLName().equals(lName)) {
                    LatLng friendposition = new LatLng(friendLatitude, friendLongitude);
                    googleMap.addMarker(new MarkerOptions().position(friendposition).title(fName + " " + lName).snippet(fName + " is here! Tap to show detail").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
                }
            }
        }
    }

    @Override
    public boolean onMarkerClick(final Marker marker) {

        String fName = marker.getTitle();
        for(Student student : userFriends){
            if (student.getFName().equals(fName) ) {
                CustomDialogTool.viewFriendDetailDialog(getActivity(),student,"Friend Information", JSONParsingTool.parseStudentDialogInfo(student));
            }
        }
        return true;
    }

    public boolean isTag() {
        return tag;
    }

    public void setTag(boolean tag) {
        this.tag = tag;
    }

    //    public boolean isANeededFriend(Student student, List<Student> filter){
//        for(Student s : filter){
//            if(student.getFName().equals(s.getFName()) && student.getLName().equals(s.getLName())){
//                return true;
//            }
//        }
//        return false;
//    }
}

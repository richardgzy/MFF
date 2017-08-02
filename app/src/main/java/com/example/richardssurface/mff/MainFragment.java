package com.example.richardssurface.mff;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.richardssurface.mff.Utilities.JSONParsingTool;
import com.example.richardssurface.mff.Utilities.Student;
import com.example.richardssurface.mff.Utilities.WeatherHttpClient;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Richard's Surface on 4/25/2017.
 */

public class MainFragment extends Fragment {
    View vMain;
    TextView tv_welcome;
    TextView tv_login_info;
    TextView tv_weatherinfo;
    TextView tv_current_time;
    private boolean loginState;
    private Student currentUser;
    static private final String latitude = "-37.8830";//hardcode value
    static private final String longitude = "145.0930";//hardcode value
    private String userCurrentTemp;
    private String userCurrentWeather;
    private String userCurrentLocation;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        vMain = inflater.inflate(R.layout.fragment_main, container, false);
        tv_welcome = (TextView) vMain.findViewById(R.id.tv_welcome);
        tv_login_info = (TextView) vMain.findViewById(R.id.tv_login_info);
        tv_weatherinfo = (TextView) vMain.findViewById(R.id.tv_weatherinfo);
        tv_current_time = (TextView) vMain.findViewById(R.id.tv_current_time);

        //current date and time
        String currentDateString = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String currentTimeString = new SimpleDateFormat("HH:MM:ss").format(new Date());

        tv_current_time.setText(currentDateString + "," + currentTimeString);

        //validate user first
        loginState = LoginPage.isCurrentLoginState();
        if(!loginState){
            currentUser = null;
            tv_login_info.setText(getResources().getString(R.string.tag_feedback_for_new_user));
            Fragment fragment = new LoginPage();
            FragmentManager fragmentManager = getActivity().getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.content_frame, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }else{
            currentUser = LoginPage.getCurrentUser();
            String welcome_message_after_log_in =getResources().getString(R.string.welcome_message_after_log_in);
            tv_login_info.setText(String.format(welcome_message_after_log_in, currentUser.getFName()));
        }

        // get weather and location information

        new AsyncTask<Void, Void, ArrayList<String>>() {

            @Override protected ArrayList<String> doInBackground (Void...params){
                WeatherHttpClient whc = new WeatherHttpClient();
                String response = whc.getWeatherData(latitude,longitude);

                return JSONParsingTool.parseWeatherinfo(response);
            }

            @Override protected void onPostExecute (ArrayList<String> result){
                userCurrentTemp = result.get(0);
                userCurrentWeather = result.get(1);
                userCurrentLocation = result.get(2);

                tv_weatherinfo.setText("Tempreature: " + userCurrentTemp + " Kelvin\r\n" +
                        "Current Weather: " + userCurrentWeather + "\r\n" +
                        "Your Location: " + userCurrentLocation);
            }
        }.execute();

        return vMain;
    }

    static public String getLatitude() {
        return latitude;
    }

    static public String getLongitude() {
        return longitude;
    }
}

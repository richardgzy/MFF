package com.example.richardssurface.mff;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Richard's Surface on 4/25/2017.
 */

public class MainFragment extends Fragment {
    View vMain;
    TextView tv_welcome;
    TextView tv_weatherinfo;
    private boolean loginState;
    private String currentUser;
    private String latitude = "-37.8830";
    private String longitude = "145.0930";
    private String userCurrentTemp;
    private String userCurrentWeather;
    private String userCurrentLocation;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        vMain = inflater.inflate(R.layout.activity_main, container, false);
        tv_welcome = (TextView) vMain.findViewById(R.id.tv_welcome);
        tv_weatherinfo = (TextView) vMain.findViewById(R.id.tv_weatherinfo);

        //validate user first
        loginState = LoginPage.isCurrentLoginState();
        if(!loginState){
            currentUser = null;
            tv_welcome.setText(getResources().getString(R.string.tag_feedback_for_new_user));
        }else{
            currentUser = LoginPage.getCurrentUser();
            tv_welcome.setText("Welcome, " + currentUser + "!");
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

                tv_weatherinfo.setText(userCurrentTemp + userCurrentWeather + userCurrentLocation);
            }
        }.execute();

        return vMain;
    }
}

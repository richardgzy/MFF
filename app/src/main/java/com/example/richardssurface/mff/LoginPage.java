package com.example.richardssurface.mff;

/**
 * Created by Richard's Surface on 4/24/2017.
 */

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class LoginPage extends Fragment implements View.OnClickListener {
    private View vLogin;
    private EditText etUserName, etPassword;
    private Button bSubmit;
    private TextView tvFeedback;
    private static String currentUser = "";
    private static boolean currentLoginState;

    private static final String BASE_URI = "http://192.168.1.5:8080/MFF/webresources";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Set Variables and listener
        vLogin = inflater.inflate(R.layout.login_page, container, false);
        etUserName = (EditText) vLogin.findViewById(R.id.et_username);
        etPassword = (EditText) vLogin.findViewById(R.id.et_password);
        bSubmit = (Button) vLogin.findViewById(R.id.b_login);
        tvFeedback = (TextView) vLogin.findViewById(R.id.tv_feedback);
        bSubmit.setOnClickListener(this);
        return vLogin;
    }

    /**
     * When the submit button is clicked
     */
    @Override
    public void onClick(View v) {
        // Gather user input
        String userName = etUserName.getText().toString();
        String password = etPassword.getText().toString();
        // Validate user input
        //TODO validate with the info in database
        String methodPath = "/mffrest.student/findByMonashEmail/";

        String fullUrl = BASE_URI + methodPath + userName;
        HttpURLConnection conn = null;
        String textResult = "";

        try {
            URL url = new URL(fullUrl);
            //open the connection
            conn = (HttpURLConnection) url.openConnection();
            //set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            //set the connection method to GET
            conn.setRequestMethod("GET");
            //add http headers to set your response type to json
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            //Read the response
            Scanner inStream = new Scanner(conn.getInputStream());
            //read the input steream and store it as string
            while (inStream.hasNextLine()) {
                textResult += inStream.nextLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }

        //TODO parse textResult to JSONObject
        JsonObject jo = null;
        boolean loginVaild = validateLogin(userName, password, jo);

        if (userName.isEmpty()) {
            etUserName.setError("Please Enter your Username");
            return;
        }
        if (password.isEmpty()) {
            etPassword.setError("Please Enter your Password");
            return;
        }
        if (textResult.isEmpty()){
            etPassword.setError("Sorry, username does not exist, please try your Monash email address");
            return;
        }
        if (!loginVaild){
            etPassword.setError("Sorry, this seems not like a vaild matching password");
            return;
        }

        // Store user input to JSONObject
        JSONObject joUnit = new JSONObject();
        try {
            joUnit.put("userName", userName);
            joUnit.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // Add created unit to file
        SharedPreferences spMyUnits = getActivity().getSharedPreferences("myUnits", Context.MODE_PRIVATE);
        String sMyUnits = spMyUnits.getString("myUnits", null);
        JSONArray jaMyUnits = null;
        // If myUnits has not been set, create one.
        if (sMyUnits == null) {
            jaMyUnits = new JSONArray();
        }
        // If exist, parse it
        else {
            try {
                jaMyUnits = new JSONArray(sMyUnits);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        // Append joUnit to jaMyUnits and save back to file
        jaMyUnits.put(joUnit);
        SharedPreferences.Editor eMyUnits = spMyUnits.edit();
        eMyUnits.putString("myUnits", jaMyUnits.toString());
        eMyUnits.apply();
        // Feedback
        String feedback = userName + " " + password + " has been recorded.";
        tvFeedback.setText(feedback);

        //clear the text
        etUserName.setText("");
        etPassword.setText("");
    }

    public boolean validateLogin (String username, String password, JsonObject jsonResult){
        currentLoginState = false;
        //TODO VALIDATE keys here
        String tempUsername = jsonResult.get("Monash_Email").toString();
        String tempPassword = jsonResult.get("password").toString();

        if (username.equals(tempUsername) && password.equals(tempPassword)){
            currentLoginState = true;
            currentUser = tempUsername;
        }
        return currentLoginState;
    }

    public void logOut(){
        currentUser = null;
        currentLoginState = false;
    }

    public static String getCurrentUser() {
        return currentUser;
    }

    public static boolean isCurrentLoginState() {
        return currentLoginState;
    }
}

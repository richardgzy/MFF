package com.example.richardssurface.mff;

/**
 * Created by Richard's Surface on 4/24/2017.
 */

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.JsonObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
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
        final String userName = etUserName.getText().toString();
        if (userName.isEmpty()) {
            etUserName.setError("Please Enter your Username");
            return;
        }

        final String password = etPassword.getText().toString();
        if (password.isEmpty()) {
            etPassword.setError("Please Enter your Password");
            return;
        }
        //hasing password
        final String hashedPassword = HashTool.md5Hashing(password);

        // Validate user input using asyncTask
        new AsyncTask<Void, Void, String>() {
            @Override protected String doInBackground (Void...params){

                //using the rest service findByAnyTwoAttribute to verify the username and hashed password
                String methodPath = "/mffrest.student/findByAnyTwoAttribute";
                String fullUrl = BASE_URI + methodPath + "/monashEmail/" + userName + "/password/" + hashedPassword;
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

                return textResult;
            }

            @Override protected void onPostExecute (String textResult){
                ArrayList<HashMap> result = JSONParsingTool.parseStudentinfo(textResult);
                if(result.size() == 1){
                    // login successful
                    currentUser = result.get(0).get("FName").toString();
                    currentLoginState = true;
                }
                else{
                    //login failed
                    currentUser = null;
                    currentLoginState = false;
                }

                if(currentLoginState && currentUser != null){

                    String welcome_message_after_log_in = getResources().getString(R.string.welcome_message_after_log_in);
                    tvFeedback.setText(String.format(welcome_message_after_log_in, currentUser));
                }else{
                    etPassword.setError("Sorry, the combination does not exist, please try your Monash email address");
                }
            }
        }.execute();


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

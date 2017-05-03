package com.example.richardssurface.mff.Utilities;

import android.app.Activity;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Richard's Surface on 4/29/2017.
 */

public class HttpPostClient {

    //TODO PLEASE CHANGE THE IP ADDRESS HERE WHEN CHANGING NETWORK ENVIRONEMNT
    static final String myIpAddress = "118.139.50.151";
    static final String BASE_URI = "http://" + myIpAddress + ":8080/MFF/webresources";
    //TODO change parameter here
    String subscribeParameters = "username=" + "&password=" + "yourusername" + "yourpassword";

    public static void subscribe(String userData, String parameters) {
        HttpURLConnection connection;
        OutputStreamWriter request = null;
        String response = null;
        try {
            URL url = new URL(BASE_URI);
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestMethod("POST");

            request = new OutputStreamWriter(connection.getOutputStream());
            request.write(parameters);
            request.flush();
            request.close();
            String line = "";
            InputStreamReader isr = new InputStreamReader(connection.getInputStream());
            BufferedReader reader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            // Response from server after login process will be stored in response variable.
            response = sb.toString();
            // You can perform UI operations here
//            Toast.makeText(getActivity(),"Message from Server: \n"+ response, Toast.LENGTH_LONG).show();

            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void subcribe2(final Activity activity, final Student student) {
        try {
            RequestParams params = new RequestParams();
            //fit student info into params
            params.put("f_name", student.getFName());
            params.put("l_name", student.getLName());
            params.put("dob", student.getMonashEmail());
            params.put("gender", student.getGender());
            params.put("study_mode", student.getStudyMode());
            params.put("course", student.getCourse());
            params.put("address", student.getAddress());
            params.put("subrub", student.getSubrub());
            params.put("nationality", student.getNationality());
            params.put("native_language", student.getNativeLanguage());
            params.put("favourite_sport", student.getFavouriteSport());
            params.put("favourite_movie", student.getFavouriteMovie());
            params.put("favourite_unit", student.getFavouriteUnit());
            params.put("email", student.getMonashEmail());
            params.put("password", student.getPassword());
            params.put("subscription_date", student.getSubscriptionDate());
            params.put("subscription_time", student.getSubscriptionTime());

            //using asychttpclient (httpclient in Asyc task)
            AsyncHttpClient asyclient = new AsyncHttpClient();
            asyclient.get(BASE_URI, params, new AsyncHttpResponseHandler() {
                // When the response returned by REST has Http response code '200'
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] bytes) {
                    // Hide Progress Dialog
                    try {
                        //TODO make bytes into response?
                        String response = bytes.toString();
                        // JSON Object
                        JSONObject obj = new JSONObject(response);
                        // When the JSON response has status boolean value assigned with true
                        if (obj.getBoolean("status")) {
                            Toast.makeText(activity, "You are successfully logged in!", Toast.LENGTH_LONG).show();
                            // TODO navigate to main screen?
                        }
                        // Else display error message
                        else{
                            Toast.makeText(activity, "Somthing wrong with the network connection here, no HTTP response status received", Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        Toast.makeText(activity, "Error Occured: Server's JSON response might be invalid!", Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }
                }
                // When the response returned by REST has Http response code other than '200'
                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] bytes, Throwable error) {
                    // Hide Progress Dialog
                    // prgDialog.hide();
                    // When Http response code is '404'
                    if (statusCode == 404) {
                        Toast.makeText(activity, "Requested resource not found", Toast.LENGTH_LONG).show();
                    }
                    // When Http response code is '500'
                    else if (statusCode == 500) {
                        Toast.makeText(activity, "Something went wrong at server end", Toast.LENGTH_LONG).show();
                    }
                    // When Http response code other than 404, 500
                    else{
                        Toast.makeText(activity, "Unexpected Error occcured: Most common Error: Device might not be connected to Internet or remote server is not up and running", Toast.LENGTH_LONG).show();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

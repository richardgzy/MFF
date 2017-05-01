package com.example.richardssurface.mff;

import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

/**
 * Created by Richard's Surface on 4/29/2017.
 */

public class HttpPostClient {
    //TODO change parameter here
    String parameters = "username="+ "yourusername" +"&password="+ "yourpassword";

    public static String subscribe(HashMap<String, Object> userData, String parameters){
        HttpURLConnection connection;
        OutputStreamWriter request = null;

        URL url = null;
        String response = null;
        try
        {
            url = new URL("your login URL");
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
            while ((line = reader.readLine()) != null)
            {
                sb.append(line + "\n");
            }
            // Response from server after login process will be stored in response variable.
            response = sb.toString();
            // You can perform UI operations here
//            Toast.makeText(getActivity(),"Message from Server: \n"+ response, Toast.LENGTH_LONG).show();

            reader.close();

        }catch(IOException e){
            e.printStackTrace();
        }
    }

}

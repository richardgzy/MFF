package com.example.richardssurface.mff;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Richard's Surface on 4/24/2017.
 */

public class WeatherHttpClient {

    private static String BASE_URL = "http://api.openweathermap.org/data/2.5/forecast?id=524901&APPID=";
    private static String IMG_URL = "http://openweathermap.org/img/w/";
    private static String API_key = "7ad5f53ef8c181faf755dfb347058f8d";


    public String getWeatherData(String latitude, String longitude) {
        HttpURLConnection con = null;
        InputStream is = null;

        String query = "&cities=true&layer=temperature&lat=" + latitude + "&lon=" + longitude;

        try {
            con = (HttpURLConnection) (new URL(BASE_URL + API_key + query)).openConnection();
            con.setRequestMethod("GET");
            con.setDoInput(true);
            con.setDoOutput(true);
            con.connect();

            //read the response
            StringBuffer buffer = new StringBuffer();
            is = con.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line = null;
            while ((line = br.readLine()) != null)
                buffer.append(line);

            is.close();
            con.disconnect();
            return buffer.toString();
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (Throwable t) {
            }

            try {
                con.disconnect();
            } catch (Throwable t) {
            }
        }
        return null;
    }

    public byte[] getImage(String code) {
        HttpURLConnection con = null;
        InputStream is = null;
        try {
            con = (HttpURLConnection) (new URL(IMG_URL + code)).openConnection();
            con.setRequestMethod("GET");
            con.setDoInput(true);
            con.setDoOutput(true);
            con.connect();

            // Let's read the response
            is = con.getInputStream();
            byte[] buffer = new byte[1024];
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            while (is.read(buffer) != -1)
                baos.write(buffer);

            return baos.toByteArray();
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (Throwable t) {
            }
            try {
                con.disconnect();
            } catch (Throwable t) {
            }
        }
        return null;
    }
}

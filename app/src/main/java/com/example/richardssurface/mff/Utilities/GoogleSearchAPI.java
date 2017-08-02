package com.example.richardssurface.mff.Utilities;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by Richard's Surface on 5/4/2017.
 */

public class GoogleSearchAPI {
    static final String API_key = "AIzaSyAABKsQey-XPjwR-wtUdWPtf7VqS-MXS7k";
    static final String SEARCH_ID_cx = "000982308662190301302:pr_dja_ie7i";

    public static String searchGoogleMovieAPIinIMDB(String keyword) {

        keyword = keyword.replace(" ", "+");
        HttpURLConnection conn = null;
        String strResponse = "";
        final String fullUrl = "https://www.googleapis.com/customsearch/v1?key=" + API_key + "&cx=" + SEARCH_ID_cx + "&q=" + keyword + "&num=5";

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
            //read the input stream and store it as string
            while (inStream.hasNextLine()) {
                strResponse += inStream.nextLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }

        return strResponse;
    }

    public static String getMovieDescription(String keyWord){
        String snippet = "No Result Found";
        String strResponse = GoogleSearchAPI.searchGoogleMovieAPIinIMDB(keyWord);
        try{
            JSONObject json = new JSONObject(strResponse);
            JSONArray jsonarray = json.getJSONArray("items");
            if (jsonarray != null && jsonarray.length() > 0) {
                snippet = jsonarray.getJSONObject(0).getString("snippet");

                System.out.println(snippet);
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        return snippet;
    }

    public static String getMovieImage(String keyWord){
        String imageLink = "";
        String strResponse = GoogleSearchAPI.searchGoogleMovieAPIinIMDB(keyWord);
        try{
            JSONObject json = new JSONObject(strResponse);
            JSONArray jsonarray = json.getJSONArray("items");
            if (jsonarray != null && jsonarray.length() > 0) {
                JSONObject pagemap = jsonarray.getJSONObject(0).getJSONObject("pagemap");
                JSONArray movie = pagemap.getJSONArray("movie");
                JSONObject firstMovie = movie.getJSONObject(0);
                imageLink = firstMovie.getString("image");

            }
        }catch(Exception e){
            e.printStackTrace();
        }

        return imageLink;
    }
}

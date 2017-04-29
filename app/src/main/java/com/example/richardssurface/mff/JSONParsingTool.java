package com.example.richardssurface.mff;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by Richard's Surface on 4/29/2017.
 */

public class JSONParsingTool {

    public static ArrayList<String> parseWeatherinfo(String input){
        ArrayList<String> resultArray = new ArrayList<>();

        try{
            JSONObject jo = new JSONObject(input);

            //extract temperature
            JSONArray listJaar = (JSONArray) jo.get("list");
            int count = (Integer) jo.get("cnt");
            JSONObject jo2 = (JSONObject) listJaar.get(0);
            JSONObject jo3 = (JSONObject) jo2.get("main");
            String tempweNeed = jo3.get("temp").toString();

            //extract weather
            JSONObject jo4 = (JSONObject) listJaar.get(count-1);
            JSONArray weatherJaar = (JSONArray) jo4.get("weather");
            JSONObject jo5 = (JSONObject) weatherJaar.get(0);
            String weatherweNeed = jo5.get("main").toString();

            //extract location name
            JSONObject jo6 = (JSONObject) jo.get("city");
            String placeName = jo6.get("name").toString();
            String countryName = jo6.get("country").toString();
            String locationName = placeName + ", " + countryName;

            //add to result list
            resultArray.add(tempweNeed);
            resultArray.add(weatherweNeed);
            resultArray.add(locationName);

        }catch(Exception e){
            e.printStackTrace();
        }
        return resultArray;
    }

    public static ArrayList<HashMap> parseStudentinfo(String input){
        ArrayList<HashMap> result = new ArrayList<>();
        HashMap<String, Object> studentMap = new HashMap<>();
        try{
            JSONArray jaar = new JSONArray(input);
            int count = jaar.length();
            for (int i = 0; i < count; i++){
                JSONObject jo = jaar.getJSONObject(i);

                //init variables
                String FName = jo.get("FNAME").toString();
                studentMap.put("FName", FName);
                String LName = jo.get("LNAME").toString();
                studentMap.put("LName", LName);
                String address = jo.get("address").toString();
                studentMap.put("address", address);
                String course = jo.get("course").toString();
                studentMap.put("course", course);
                String currentJob = jo.get("currentJob").toString();
                studentMap.put("currentJob", currentJob);
                Date dob = (Date) jo.get("address");
                studentMap.put("address", address);
                String favouriteMovie = jo.get("favouriteMovie").toString();
                studentMap.put("favouriteMovie", favouriteMovie);
                String favouriteSport = jo.get("favouriteSport").toString();
                studentMap.put("favouriteSport", favouriteSport);
                String favouriteUnit = jo.get("favouriteUnit").toString();
                studentMap.put("favouriteUnit", favouriteUnit);
                String gender = jo.get("gender").toString();
                studentMap.put("gender", gender);
                String userName = jo.get("monashEmail").toString();
                studentMap.put("monashEmail", userName);
                String nationality = jo.get("nationality").toString();
                studentMap.put("nationality", nationality);
                String nativeLanguage = jo.get("nativeLanguage").toString();
                studentMap.put("nativeLanguage", nativeLanguage);
                String password = jo.get("password").toString();
                studentMap.put("password", password);
                int sid = (int) jo.get("sid");
                studentMap.put("sid", sid);
                String studyMode = jo.get("studyMode").toString();
                studentMap.put("studyMode", studyMode);
                String subrub = jo.get("subrub").toString();
                studentMap.put("subrub", subrub);
                Date subscriptionDate = (Date) jo.get("subscriptionDate");
                studentMap.put("subscriptionDate", subscriptionDate);
                String subscriptionTime = jo.get("subscriptionTime").toString();
                studentMap.put("subscriptionTime", subscriptionTime);

                result.add(studentMap);
                studentMap.clear();
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return result;
    }
}

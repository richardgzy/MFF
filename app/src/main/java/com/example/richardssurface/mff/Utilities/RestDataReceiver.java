package com.example.richardssurface.mff.Utilities;

import com.github.mikephil.charting.data.PieEntry;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by Richard's Surface on 5/3/2017.
 */

public class RestDataReceiver {

    //TODO PLEASE CHANGE THE IP ADDRESS HERE WHEN NETWORK ENVIRONEMNT CHANGED
    static final String myIpAddress = "118.139.52.205";
    private static final String BASE_URI = "http://" + myIpAddress + ":8080/MFF/webresources";

    public static String httpGetConnection(String fullUrl){
        String textResult = "";
        HttpURLConnection conn = null;

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
                textResult += inStream.nextLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }

        return textResult;
    }

    public static List<Student> RestLogin(String userName, String hashedPassword){
        //using the rest service findByAnyTwoAttribute to verify the username and hashed password
        String methodPath = "/mffrest.student/findByAnyTwoAttribute";
        String fullUrl = BASE_URI + methodPath + "/monashEmail/password/" + userName + "/" + hashedPassword;
        String textResult = httpGetConnection(fullUrl);

        return JSONParsingTool.parseInfoForLogin(textResult);
    }

    public static List<Student> getAllUserFriends(Student currentUser){
        ArrayList<Student> result = new ArrayList<>();
        int currentUserSid = currentUser.getSid();

        String methodPath = "/mffrest.studentfriendrelation/findUserFriends/";
        String fullUrl = BASE_URI + methodPath + currentUser.getSid();
        String textResult = httpGetConnection(fullUrl);

        //parse text result and find friends
        return JSONParsingTool.parseFriendInfo(textResult);
    }

    public static List<Student> searchFriendsUsingCriteria(Student currentUser, String criteria) {
        ArrayList<Student> result = new ArrayList<>();
        int currentUserSid = currentUser.getSid();

        String methodPath = "/mffrest.student/findFriendsUsingThreeCommonAttributes2";
        String fullUrl = BASE_URI + methodPath + "/" + currentUser.getSid() + criteria;
        String textResult = httpGetConnection(fullUrl);

        //parse text result and find friends
        return JSONParsingTool.parseFriendInfo2(textResult);
    }

    public static List<PieEntry> getPopularUnits(){

        String methodPath = "/mffrest.student/findPopularUnit";
        String fullUrl = BASE_URI + methodPath;
        String textResult = httpGetConnection(fullUrl);

        HashMap<String, Integer> hm = JSONParsingTool.parsePopularUnitInfo(textResult);
        List<PieEntry> result = new ArrayList<>();
        int frequencySum = 0;
        int frequency = 0;
        String unitName = "";

//        compute sum
        Iterator it1 = hm.entrySet().iterator();
        while (it1.hasNext()) {
            Map.Entry pair = (Map.Entry)it1.next();
            frequency = (Integer) pair.getValue();
            frequencySum += frequency;
        }
//        set pie entry
        Iterator it2 = hm.entrySet().iterator();
        while (it2.hasNext()) {
            Map.Entry pair = (Map.Entry)it2.next();
            frequency = (Integer) pair.getValue();
            unitName = pair.getKey().toString();
            PieEntry pe = new PieEntry((float)frequency/frequencySum, unitName);
            result.add(pe);
        }

        return result;
    }

    public static List<HashMap<String, String>> findNearbyFriends(Student currentUser, Double latitude, Double longitude, Double rangeRadius) {
        ArrayList<Student> result = new ArrayList<>();
        int currentUserSid = currentUser.getSid();

        String methodPath = "/mffrest.studentlocation/findNearByFriends/";
        String fullUrl = BASE_URI + methodPath + latitude.toString() + "/" + longitude.toString() + "/" + currentUser.getSid();
        String textResult = httpGetConnection(fullUrl);

        //parse text result
        return JSONParsingTool.parseNearByFriends(textResult,rangeRadius.toString());
    }
}

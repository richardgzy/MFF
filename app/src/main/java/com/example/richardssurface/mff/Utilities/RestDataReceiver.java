package com.example.richardssurface.mff.Utilities;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Richard's Surface on 5/3/2017.
 */

public class RestDataReceiver {

    //TODO PLEASE CHANGE THE IP ADDRESS HERE WHEN CHANGING NETWORK ENVIRONEMNT
    static final String myIpAddress = "118.139.50.151";
    private static final String BASE_URI = "http://" + myIpAddress + ":8080/MFF/webresources";

    public static List<Student> getAllUserFriends(Student currentUser){
        ArrayList<Student> result = new ArrayList<>();
        int currentUserSid = currentUser.getSid();

        String methodPath = "/mffrest.studentfriendrelation/findUserFriends/";
        String fullUrl = BASE_URI + methodPath + "/" +currentUser.getSid();
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
            //read the input stream and store it as string
            while (inStream.hasNextLine()) {
                textResult += inStream.nextLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }

        //parse text result and find friends
        return JSONParsingTool.parseFriendInfo(textResult);
    }

    public static List<Student> searchFriendsUsingCriteria(Student currentUser, String criteria) {
        ArrayList<Student> result = new ArrayList<>();
        int currentUserSid = currentUser.getSid();

        String methodPath = "/mffrest.student/findFriendsUsingThreeCommonAttributes2";
        String fullUrl = BASE_URI + methodPath + "/" + currentUser.getSid() + criteria;
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
            //read the input stream and store it as string
            while (inStream.hasNextLine()) {
                textResult += inStream.nextLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }

        //parse text result and find friends
        return JSONParsingTool.parseFriendInfo(textResult);
    }
}

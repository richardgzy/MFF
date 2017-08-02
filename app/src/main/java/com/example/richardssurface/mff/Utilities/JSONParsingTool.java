package com.example.richardssurface.mff.Utilities;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Richard's Surface on 4/29/2017.
 */

public class JSONParsingTool {

    public static ArrayList<String> parseWeatherinfo(String input) {
        ArrayList<String> resultArray = new ArrayList<>();

        try {
            JSONObject jo = new JSONObject(input);

            //extract temperature
            JSONArray listJaar = (JSONArray) jo.get("list");
            int count = (Integer) jo.get("cnt");
            JSONObject jo2 = (JSONObject) listJaar.get(0);
            JSONObject jo3 = (JSONObject) jo2.get("main");
            String tempweNeed = jo3.get("temp").toString();

            //extract weather
            JSONObject jo4 = (JSONObject) listJaar.get(count - 1);
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

        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultArray;
    }

    public static ArrayList<Student> parseInfoForLogin(String input) {
        ArrayList<Student> result = new ArrayList<>();
        try {
            JSONArray jaar = new JSONArray(input);
            int count = jaar.length();
            for (int i = 0; i < count; i++) {
                String studentInfoString = jaar.getJSONObject(i).toString();
                Student student = parseSingleStudentinfo(studentInfoString);
                result.add(student);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static Student parseSingleStudentinfo(String input) {
        try {
            JSONObject jo = new JSONObject(input);
            Student student = new Student();

            //init variables
            String FName = jo.get("FName").toString();
            student.setFName(FName);

            String LName = jo.get("LName").toString();
            student.setLName(LName);

            String address = jo.get("address").toString();
            student.setAddress(address);

            String course = jo.get("course").toString();
            student.setCourse(course);

            String currentJob = jo.get("currentJob").toString();
            student.setCurrentJob(currentJob);

            String dobString = jo.get("dob").toString();
            student.setDob(dobString);

            String favouriteMovie = jo.get("favouriteMovie").toString();
            student.setFavouriteMovie(favouriteMovie);

            String favouriteSport = jo.get("favouriteSport").toString();
            student.setFavouriteSport(favouriteSport);

            String favouriteUnit = jo.get("favouriteUnit").toString();
            student.setFavouriteUnit(favouriteUnit);

            String gender = jo.get("gender").toString();
            student.setGender(gender);

            String userName = jo.get("monashEmail").toString();
            student.setMonashEmail(userName);

            String nationality = jo.get("nationality").toString();
            student.setNationality(nationality);

            String nativeLanguage = jo.get("nativeLanguage").toString();
            student.setNativeLanguage(nativeLanguage);

            String password = jo.get("password").toString();
            student.setPassword(password);

            int sid = (int) jo.get("sid");
            student.setSid(sid);

            String studyMode = jo.get("studyMode").toString();
            student.setStudyMode(studyMode);

            String subrub = jo.get("subrub").toString();
            student.setSubrub(subrub);

            String subscriptionDate = jo.get("subscriptionDate").toString();
            student.setSubscriptionDate(subscriptionDate);

            String subscriptionTime = jo.get("subscriptionTime").toString();
            student.setSubscriptionTime(subscriptionTime);

            return student;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static ArrayList<Student> parseFriendInfo(String input) {
        ArrayList<Student> result = new ArrayList<>();

        try {
            JSONArray jaar = new JSONArray(input);
            int count = jaar.length();
            for (int i = 0; i < count; i++) {
                JSONObject friendship = jaar.getJSONObject(i);
                String friendInfoString = friendship.get("hasFriend").toString();

                //parse student info
                Student student = parseSingleStudentinfo(friendInfoString);
                result.add(student);
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<Student> parseFriendInfo2(String input) {
        ArrayList<Student> result = new ArrayList<>();

        try {
            JSONArray jaar = new JSONArray(input);
            int count = jaar.length();
            for (int i = 0; i < count; i++) {
                String friendInfoString = jaar.getJSONObject(i).toString();

                //parse student info
                Student student = parseSingleStudentinfo(friendInfoString);
                result.add(student);
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static HashMap<String, Integer> parsePopularUnitInfo(String input){
        HashMap<String, Integer> result = new HashMap<>();

        try {
            JSONArray jaar = new JSONArray(input);
            int count = jaar.length();
            for (int i = 0; i < count; i++) {
                JSONObject unitAndFrequency = jaar.getJSONObject(i);
                String unitName = (String) unitAndFrequency.get("unitName");
                int frequency = (Integer) unitAndFrequency.get("frequency");

                result.put(unitName,frequency);
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Student parseStudentString(String input){

        String[] array = input.split(",");
        Student student = new Student(Integer.parseInt(array[0]),array[1],array[2],array[3],array[4],array[5],
                array[6],array[7],array[8],array[9],array[10],array[11],array[12],array[13],
                array[14],array[15],array[16],array[17],array[18]);
        return student;
    }

    public static String parseStudentDialogInfo(Student student){
        String result = "";
        result += "Sid: " + student.getSid().toString() + "\r\n"
                + "FirstName: " + student.getSid().toString() + "\r\n"
                + "LastName: " + student.getLName() + "\r\n"
                + "Dob: " + student.getDob() + "\r\n"
                + "Gender: " + student.getGender() + "\r\n"
                + "StudyMode: " + student.getStudyMode() + "\r\n"
                + "Course: " + student.getCourse() + "\r\n"
                + "Address: " + student.getAddress() + "\r\n"
                + "Subrub: " + student.getSubrub() + "\r\n"
                + "Nationality: " + student.getNationality() + "\r\n"
                + "NativeLanguage: " + student.getNativeLanguage() + "\r\n"
                + "Favourite Sport: " + student.getFavouriteSport() + "\r\n"
                + "Favourite Movie: " + student.getFavouriteMovie() + "\r\n"
                + "Favourite Unit: " + student.getFavouriteUnit() + "\r\n"
                + "Current Job: " + student.getCurrentJob() + "\r\n"
                + "Monash Email: " + student.getMonashEmail();

        return result;
    }

    public static List<HashMap<String, String>> parseNearByFriends(String textResult, String rangeRadius){
        List<HashMap<String, String>> result = new ArrayList<>();
        try{
            JSONArray jaar = new JSONArray(textResult);
            int count = jaar.length();
            for (int i = 0; i < count; i++){
                JSONObject jo = (JSONObject) jaar.get(i);

                String distanceToYou = jo.getString("distanceToYou");
                if(Double.parseDouble(distanceToYou) > Double.parseDouble(rangeRadius)){
                    continue;
                }

                String currentLatitude = jo.getString("currentLatitude");
                String currentLongtitude = jo.getString("currentLongtitude");
                String fName = jo.getString("fName");
                String lName = jo.getString("lName");

                HashMap<String, String> hm = new HashMap<>();
                hm.put("currentLatitude",currentLatitude);
                hm.put("currentLongtitude",currentLongtitude);
                hm.put("distanceToYou",distanceToYou);
                hm.put("fName",fName);
                hm.put("lName",lName);

                result.add(hm);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return result;
    }

//    public static void main(String[] args){
//        String textresult = "[{\"FName\":\"RICHARD\",\"LName\":\"GUO\",\"address\":\"106 MURRUMBEENA ROAD\",\"course\":\"MIT\",\"currentJob\":\"UNEMPLOYED\",\"dob\":\"1993-02-25\",\"favouriteMovie\":\"MATRIX\",\"favouriteSport\":\"BADMINTON\",\"favouriteUnit\":\"FIT5046\",\"gender\":\"MALE\",\"monashEmail\":\"zyguo5@student.monash.edu\",\"nationality\":\"CHINA\",\"nativeLanguage\":\"CHINESE\",\"password\":\"e10adc3949ba59abbe56e057f20f883e\",\"sid\":1,\"studyMode\":\"FULLTIME\",\"subrub\":\"MURRUMBEENA\",\"subscriptionDate\":\"2015-7-1\",\"subscriptionTime\":\"19:00:00\"}]";
//        System.out.println(parseStudentinfo(textresult));
//    }
}

package com.example.richardssurface.mff;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Richard's Surface on 4/25/2017.
 */

public class testaaa {

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
                buffer.append(line + "rn");

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





    public static void main(String[] args) {

        WeatherHttpClient whc = new WeatherHttpClient();
        String response = whc.getWeatherData("-37.8830", "145.0930");
        try {
//            JsonObject jo = new JsonParser().parse(result).getAsJsonObject();
//            String temperature = jo.get("temperature").toString();
//            String location = jo.get("name").toString();
//            System.out.println(i);
//            String stringSource = "{\"name\":\"richard\"}";
//            JSONObject t = new JSONObject(response);
//            String testResponse = "{\"name\":\"richard\",\"age\":20, \"list\":[{\"ele1\":1.23}]}";
////
//            JSONObject jso = new JSONObject(testResponse);
//            JSONArray jaar = new JSONArray();
//            JSONObject jso1 = jaar.getJSONObject(0);
//
//
//            TestResponse a = new TestResponse();
//            a.setName(getString("name",jso));
//            a.setAge(getInt("age",jso));
//            a.setList(jaar);
//
//            ListElement le = new ListElement();
//            le.setEle1(getFloat("ele1",jso1));


//            Gson gs = new Gson();
//            Weather w = gs.fromJson(response,Weather.class);

            // We start extracting the info
//            Location loc = new Location();
// 
//            JSONObject coordObj = getObject("coord", jObj);
//            loc.setLatitude(getFloat("lat", coordObj));
//            loc.setLongitude(getFloat("lon", coordObj));
// 
//            JSONObject sysObj = getObject("sys", jObj);
//            loc.setCountry(getString("country", sysObj));
//            loc.setSunrise(getInt("sunrise", sysObj));
//            loc.setSunset(getInt("sunset", sysObj));
//            loc.setCity(getString("name", jObj));
//            weather.location = loc;

//            JSONObject jso = new JSONObject(response);
//            Weather w = new Weather();
//            w.setCod(getString("cod", jso));
//            w.setMessage(getFloat("message", jso));
//            w.setCnt(getInt("cnt", jso));
//
//            JSONArray jArr = jso.getJSONArray("list");
//            JSONObject weatherinList = jArr.getJSONObject(0);




//            System.out.println(response);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static JSONObject getObject(String tagName, JSONObject jObj) throws JSONException {
        JSONObject subObj = jObj.getJSONObject(tagName);
        return subObj;
    }

    private static String getString(String tagName, JSONObject jObj) throws JSONException {
        return jObj.getString(tagName);
    }


    private static float getFloat(String tagName, JSONObject jObj) throws JSONException {
        return (float) jObj.getDouble(tagName);
    }

    private static int getInt(String tagName, JSONObject jObj) throws JSONException {
        return jObj.getInt(tagName);
    }
}

package com.example.richardssurface.mff.Utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Richard's Surface on 5/3/2017.
 */

public class SharedPreferencesForLogin {
    static SharedPreferences userSp;

    public static void saveSPForLogin(Context context,Student currentUser) {
        userSp = context.getSharedPreferences("userSP", Context.MODE_PRIVATE);
        SharedPreferences.Editor eUserSp = userSp.edit();
        eUserSp.putString("userName", currentUser.getMonashEmail());
        eUserSp.putString("userInfo", currentUser.allToString());
        eUserSp.apply();
    }

    public static Student loadSPForSkipLogin(Context context) {
        userSp = context.getSharedPreferences("userSP", Context.MODE_PRIVATE);
        if (userSp != null) {
            final String userName = userSp.getString("userName", "");
            final String userInfoString = userSp.getString("userInfo", "");
            //parse string into student object
            Student currentUser = JSONParsingTool.parseStudentString(userInfoString);
            return currentUser;
        }else{
            return null;
        }
    }
}

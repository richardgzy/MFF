package com.example.richardssurface.mff.Utilities;

import android.content.Context;

import com.example.richardssurface.mff.LoginPage;

import java.util.List;

/**
 * Created by Richard's Surface on 5/3/2017.
 */

public class MyMultiSpinnerListener implements MultiSpinner.MultiSpinnerListener {
    @Override
    public void onItemsSelected(boolean[] selected, Context context) {
        String criteria = "/";
        //add criteria
        if (selected[0]) {
            criteria += ",subrub";
        }
        if (selected[1]) {
            criteria += ",nationality";
        }
        if (selected[2]) {
            criteria += ",nativeLanguage";
        }
        if (selected[3]) {
            criteria += ",favouriteSport";
        }
        if (selected[4]) {
            criteria += ",favouriteMovie";
        }
        if (selected[5]) {
            criteria += ",favouriteUnit";
        }
        if (selected[6]) {
            criteria += ",currentJob";
        }

        //do some string modification
        if (criteria.length() > 2) {
            int i = 2;
            criteria = criteria.substring(0, i) + criteria.substring(i + 1);
        }

        Student currentUser = LoginPage.getCurrentUser();
        List<Student> friends = RestDataReceiver.searchFriendsUsingCriteria(currentUser, criteria);


    }
}

package com.example.richardssurface.mff;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

/**
 * Created by Richard's Surface on 4/26/2017.
 */

public class SubscriptionFragment extends Fragment {

    View vMain;
    //text view part

    //user input part
    EditText et_f_name;
    EditText et_l_name;
    EditText et_dob;
    EditText et_gender;
    Spinner study_mode_spinner;
    Spinner course_spinner;
    EditText et_course;
    EditText et_address;
    Spinner subrub_spinner;
    Spinner nationality_spinner;
    Spinner native_language_spinner;
    Spinner favourite_sport_spinner;
    EditText et_favourite_movie;
    Spinner favourite_unit_spinner;
    EditText et_currentJob;
    EditText et_userName;
    EditText et_password;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        vMain = inflater.inflate(R.layout.subscription_page, container, false);
        study_mode_spinner = (Spinner) vMain.findViewById(R.id.study_mode_spinner);
        study_mode_spinner = (Spinner) vMain.findViewById(R.id.study_mode_spinner);
        course_spinner = (Spinner) vMain.findViewById(R.id.course_spinner);
        subrub_spinner = (Spinner) vMain.findViewById(R.id.subrub_spinner);
        nationality_spinner = (Spinner) vMain.findViewById(R.id.nationality_spinner);
        native_language_spinner = (Spinner) vMain.findViewById(R.id.native_language_spinner);
        favourite_sport_spinner = (Spinner) vMain.findViewById(R.id.favourite_sport_spinner);
        favourite_unit_spinner = (Spinner) vMain.findViewById(R.id.favourite_unit_spinner);


        //TODO use CursorAdapter to set data from SQLLite database
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.study_mode_spinner_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
//        study_mode_spinner.setAdapter(adapter);

        return vMain;
    }
}

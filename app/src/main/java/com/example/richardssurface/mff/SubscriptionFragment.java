package com.example.richardssurface.mff;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.ArrayRes;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.example.richardssurface.mff.Utilities.HttpPostClient;
import com.example.richardssurface.mff.Utilities.Student;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.example.richardssurface.mff.R.array.course_spinner_array;
import static com.example.richardssurface.mff.R.array.favourite_sport_array;
import static com.example.richardssurface.mff.R.array.favourite_unit_array;
import static com.example.richardssurface.mff.R.array.nationality_array;
import static com.example.richardssurface.mff.R.array.native_language_array;
import static com.example.richardssurface.mff.R.array.study_mode_spinner_array;
import static com.example.richardssurface.mff.R.array.subrub_array;

//string arrays

/**
 * Created by Richard's Surface on 4/26/2017.
 */

public class SubscriptionFragment extends Fragment {

    View vMain;
    //text view part

    //user input part
    EditText et_f_name, et_l_name, datepicker_dob, et_address, et_favourite_movie, et_current_job, et_username, et_password;
    Spinner study_mode_spinner, course_spinner, subrub_spinner, nationality_spinner, native_language_spinner, favourite_sport_spinner, favourite_unit_spinner;
    RadioGroup gender_radioGroup;
    Button b_subscribe;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        vMain = inflater.inflate(R.layout.subscription_page, container, false);
        //edit texts
        et_f_name = (EditText) vMain.findViewById(R.id.et_f_name);
        et_l_name = (EditText) vMain.findViewById(R.id.et_l_name);
        datepicker_dob = (EditText) vMain.findViewById(R.id.datepicker_dob);
        gender_radioGroup = (RadioGroup) vMain.findViewById(R.id.gender_radioGroup);
        et_address = (EditText) vMain.findViewById(R.id.et_address);
        et_favourite_movie = (EditText) vMain.findViewById(R.id.et_favourite_movie);
        et_current_job = (EditText) vMain.findViewById(R.id.et_current_job);
        et_username = (EditText) vMain.findViewById(R.id.et_username);
        et_password = (EditText) vMain.findViewById(R.id.et_password);

        // spinners
        study_mode_spinner = (Spinner) vMain.findViewById(R.id.study_mode_spinner);
        course_spinner = (Spinner) vMain.findViewById(R.id.course_spinner);
        subrub_spinner = (Spinner) vMain.findViewById(R.id.subrub_spinner);
        nationality_spinner = (Spinner) vMain.findViewById(R.id.nationality_spinner);
        native_language_spinner = (Spinner) vMain.findViewById(R.id.native_language_spinner);
        favourite_sport_spinner = (Spinner) vMain.findViewById(R.id.favourite_sport_spinner);
        favourite_unit_spinner = (Spinner) vMain.findViewById(R.id.favourite_unit_spinner);


        //TODO use CursorAdapter to set data from SQLLite database


        populateDatainSpinner(study_mode_spinner,study_mode_spinner_array);
        populateDatainSpinner(course_spinner,course_spinner_array);
        populateDatainSpinner(subrub_spinner,subrub_array);
        populateDatainSpinner(nationality_spinner,nationality_array);
        populateDatainSpinner(native_language_spinner,native_language_array);
        populateDatainSpinner(favourite_sport_spinner,favourite_sport_array);
        populateDatainSpinner(favourite_unit_spinner,favourite_unit_array);

        //TODO after user entering the data, send the student information to server

        // do the subscription
        b_subscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                //TODO user input validation

                Student newStudent = new Student();
                newStudent.setFName(et_f_name.getText().toString());
                newStudent.setLName(et_l_name.getText().toString());
                newStudent.setAddress(et_address.getText().toString());
                newStudent.setCourse(course_spinner.getSelectedItem().toString());
                newStudent.setCurrentJob(et_current_job.getText().toString());
                newStudent.setDob(datepicker_dob.getText().toString());
                newStudent.setFavouriteMovie(et_favourite_movie.getText().toString());
                newStudent.setFavouriteUnit(favourite_unit_spinner.getSelectedItem().toString());

                //get gender
                int radioButtonID = gender_radioGroup.getCheckedRadioButtonId();
                View radioButton = gender_radioGroup.findViewById(radioButtonID);
                int idx = gender_radioGroup.indexOfChild(radioButton);
                if(idx == 0){
                    newStudent.setGender("MALE");
                }else{
                    newStudent.setGender("FEMALE");
                }

                newStudent.setMonashEmail(et_username.getText().toString());
                newStudent.setNationality(nationality_spinner.getSelectedItem().toString());
                newStudent.setNativeLanguage(native_language_spinner.getSelectedItem().toString());
                newStudent.setPassword(et_password.getText().toString());
                newStudent.setStudyMode(study_mode_spinner.getSelectedItem().toString());

                //get current date and time
                String currentDateString = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
                String currentTimeString = new SimpleDateFormat("HH:MM:ss").format(new Date());

                newStudent.setSubscriptionDate(currentDateString);
                newStudent.setSubscriptionTime(currentTimeString);

                HttpPostClient.subcribe2(getActivity(), newStudent);
            }
        });

        return vMain;
    }

    public void populateDatainSpinner(Spinner spinner, @ArrayRes int textArrayResId){
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getActivity(), textArrayResId, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
    }
}

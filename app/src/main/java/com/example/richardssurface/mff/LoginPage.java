package com.example.richardssurface.mff;

/**
 * Created by Richard's Surface on 4/24/2017.
 */

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.richardssurface.mff.Utilities.HashTool;
import com.example.richardssurface.mff.Utilities.RestDataReceiver;
import com.example.richardssurface.mff.Utilities.SharedPreferencesForLogin;
import com.example.richardssurface.mff.Utilities.Student;

import java.util.List;

public class LoginPage extends Fragment {
    private View vLogin;
    private EditText etUserName, etPassword;
    private Button bLogin, bSubscribe, bSkipLogin;
    private static Student currentUser = null;
    private static boolean currentLoginState;

//    //TODO PLEASE CHANGE THE IP ADDRESS HERE WHEN CHANGING NETWORK ENVIRONEMNT
//    static final String myIpAddress = "192.168.1.5";
//    private static final String BASE_URI = "http://" + myIpAddress + ":8080/MFF/webresources";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Set Variables and listener
        vLogin = inflater.inflate(R.layout.login_page, container, false);
        etUserName = (EditText) vLogin.findViewById(R.id.et_username);
        etPassword = (EditText) vLogin.findViewById(R.id.et_password);
        bLogin = (Button) vLogin.findViewById(R.id.b_login);
        bSubscribe = (Button) vLogin.findViewById(R.id.b_subscribe);
        bSkipLogin = (Button) vLogin.findViewById(R.id.b_skiplogin);

        bLogin.setOnClickListener(new View.OnClickListener() {
            /**
             * When the log in button is clicked
             */
            @Override
            public void onClick(View v) {

                Toast.makeText(getActivity(), "Processing...Please wait",
                        Toast.LENGTH_LONG).show();

                // Gather user input
                final String userName = etUserName.getText().toString();
                if (userName.isEmpty()) {
                    etUserName.setError("Please Enter your Username");
                    return;
                }

                final String password = etPassword.getText().toString();
                if (password.isEmpty()) {
                    etPassword.setError("Please Enter your Password");
                    return;
                }

                //hasing password
                final String hashedPassword = HashTool.md5Hashing(password);

                // Validate user input using asyncTask
                new AsyncTask<Void, Void, List<Student>>() {
                    @Override
                    protected List<Student> doInBackground(Void... params) {
                        if(android.os.Debug.isDebuggerConnected())
                            android.os.Debug.waitForDebugger();
                        return RestDataReceiver.RestLogin(userName, hashedPassword);
                    }

                    @Override
                    protected void onPostExecute(List<Student> result) {
                        if(android.os.Debug.isDebuggerConnected())
                            android.os.Debug.waitForDebugger();
                        if (result.size() == 1) {
                            // login successful
                            currentUser = result.get(0);
                            currentLoginState = true;
                        } else {
                            //login failed
                            currentUser = null;
                            currentLoginState = false;
                        }

                        if (currentLoginState && currentUser != null) {
                            //show successful info
                            Toast.makeText(getActivity(), "Log in successfully",
                                    Toast.LENGTH_LONG).show();

                            //Shared Preferences for skip log in after
                            SharedPreferencesForLogin.saveSPForLogin(getActivity(),currentUser);

                            //jump to fragment main
                            Fragment fragment = new MainFragment();
                            FragmentManager fragmentManager = getActivity().getFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.content_frame, fragment);
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();

                        } else {
                            etPassword.setError("Sorry, the username/password is incorrect, please try again");
                            Toast.makeText(getActivity(), "Sorry, the username/password is incorrect, please try again",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                }.execute();
            }
        });

        bSubscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new SubscriptionFragment();
                FragmentManager fragmentManager = getActivity().getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.content_frame, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });



        bSkipLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Student temp = SharedPreferencesForLogin.loadSPForSkipLogin(getActivity());
                if(temp != null){
                    currentUser = temp;
                    currentLoginState = true;
                    Toast.makeText(getActivity(), "Skip Log in successfully",
                            Toast.LENGTH_LONG).show();
                    Fragment fragment = new MainFragment();
                    FragmentManager fragmentManager = getActivity().getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.content_frame, fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }else{
                    Toast.makeText(getActivity(), "Sorry, we don't have your information, please log in",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        return vLogin;
    }

    public void logOut() {
        currentUser = null;
        currentLoginState = false;
    }

    public static Student getCurrentUser() {
        return currentUser;
    }

    public static boolean isCurrentLoginState() {
        return currentLoginState;
    }
}

package com.example.richardssurface.mff;

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
import android.widget.ListView;
import android.widget.Toast;

import com.example.richardssurface.mff.Utilities.MultiSpinner;
import com.example.richardssurface.mff.Utilities.RestDataReceiver;
import com.example.richardssurface.mff.Utilities.Student;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Richard's Surface on 5/3/2017.
 */

public class searchFragment extends Fragment {
    View vMain;
    MultiSpinner ms;
    Button b_search_friend, b_map_view;
    ListView b_friend_list_view;
    Student currentUser;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        vMain = inflater.inflate(R.layout.search_friend_fragment, container, false);
        ms = (MultiSpinner) vMain.findViewById(R.id.multi_spinner);
        b_search_friend = (Button) vMain.findViewById(R.id.b_search_friend);
        b_map_view = (Button) vMain.findViewById(R.id.b_map_view);
        b_friend_list_view = (ListView) vMain.findViewById(R.id.b_friend_list_view);

        List<String> items = new ArrayList<>();
        items.add("same subrub!");
        items.add("same nationality!");
        items.add("same native language!");
        items.add("same favourite sport!");
        items.add("same favourite movie!");
        items.add("same favourite unit!!");
        items.add("same current job!");
        ms.setItems(items, getString(R.string.for_all), ms.getListener());

        new MultiSpinner.MultiSpinnerListener() {
            @Override
            public void onItemsSelected(boolean[] selected) {

                //add criteria
                String criteria = "/";
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
                    final String criteriaToPass = criteria;

                    new AsyncTask<Void, Void, List<Student>>() {
                        @Override
                        protected List<Student> doInBackground(Void... params) {
                            currentUser = LoginPage.getCurrentUser();
                            return RestDataReceiver.searchFriendsUsingCriteria(currentUser, criteriaToPass);
                        }

                        @Override
                        protected void onPostExecute(List<Student> result) {
                            //TODO display result in fragment xml
//                            b_friend_list_view

                        }
                    };
                } else {
                    //no criteria entered
                    Toast.makeText(getActivity(), "No criteria selected, search aborted",
                            Toast.LENGTH_LONG).show();
                }
            }

            ;
        };

        b_map_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new MapFragment();
                FragmentManager fragmentManager = getActivity().getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.content_frame, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        return vMain;
    }
}

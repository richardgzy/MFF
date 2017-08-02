package com.example.richardssurface.mff;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.richardssurface.mff.Utilities.CustomDialogTool;
import com.example.richardssurface.mff.Utilities.JSONParsingTool;
import com.example.richardssurface.mff.Utilities.MultiSpinner;
import com.example.richardssurface.mff.Utilities.RestDataReceiver;
import com.example.richardssurface.mff.Utilities.Student;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Richard's Surface on 5/3/2017.
 */

public class SearchFriendFragment extends Fragment implements MultiSpinner.MultiSpinnerListener {
    private View vMain;
    private MultiSpinner ms;
    private Button  b_map_view;
    private ListView b_friend_list_view;
    private Student currentUser = LoginPage.getCurrentUser();
    private List<String> itemsinMultiSpinner;
    static private List<Student> friends;
    final String[] colHEAD = new String[]{"sid", "FName", "LName"};
    final int[] dataCell = new int[]{R.id.sid, R.id.FName, R.id.LName};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        vMain = inflater.inflate(R.layout.search_friend_fragment, container, false);
        ms = (MultiSpinner) vMain.findViewById(R.id.multi_spinner);
        b_map_view = (Button) vMain.findViewById(R.id.b_map_view);
        b_friend_list_view = (ListView) vMain.findViewById(R.id.b_friend_list_view);

        itemsinMultiSpinner = new ArrayList<>();
        itemsinMultiSpinner.add("subrub");
        itemsinMultiSpinner.add("nationality");
        itemsinMultiSpinner.add("nativeLanguage");
        itemsinMultiSpinner.add("favouriteSport");
        itemsinMultiSpinner.add("favouriteMovie");
        itemsinMultiSpinner.add("favouriteUnit");
        itemsinMultiSpinner.add("currentJob");

        ms.setItems(itemsinMultiSpinner, getString(R.string.for_all));
        ms.setListener(new MultiSpinner.MultiSpinnerListener() {
            @Override
            public void onItemsSelected(boolean[] selected, Context context) {
                List<String> temp = new ArrayList<>();
                for (int i = 0;i < selected.length;i++){
                    if (selected[i]){
                        temp.add(itemsinMultiSpinner.get(i));
                    }
                }

                final String criteria = parseStudentListtoString(temp);
                if(criteria.length() > 1){
                    new AsyncTask<Void, Void, List<Student>>() {
                        @Override
                        protected List<Student> doInBackground(Void...params) {
                            if(android.os.Debug.isDebuggerConnected())
                                android.os.Debug.waitForDebugger();
                            friends = RestDataReceiver.searchFriendsUsingCriteria(currentUser, criteria);
                            return friends;
                        }

                        @Override protected void onPostExecute (List<Student> result) {
                            List<HashMap<String, String>> FriendListArray = new ArrayList<>();
                            for(Student s: friends){
                                HashMap<String, String> hm = new HashMap<>();
                                hm.put("sid",s.getSid().toString());
                                hm.put("FName",s.getFName());
                                hm.put("LName",s.getLName());
                                FriendListArray.add(hm);
                            }

                            SimpleAdapter MyListAdapter = new SimpleAdapter(getActivity(), FriendListArray, R.layout.list_view, colHEAD, dataCell);
                            b_friend_list_view.setAdapter(MyListAdapter);
                        }
                    }.execute();
                }else{
                    Toast.makeText(getActivity(), "Please choose a criteria to search",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        b_map_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MapFragment fragment = new MapFragment();
                fragment.setTag(true);
                FragmentManager fragmentManager = getActivity().getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.content_frame, fragment, "search");
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });

        b_friend_list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Student selectedStudent = friends.get(position);
                String displayText = JSONParsingTool.parseStudentDialogInfo(selectedStudent);
                CustomDialogTool.viewFriendDetailDialog(getActivity(),selectedStudent,"Friend information",displayText);
            }
        });

        return vMain;
    }

    private String parseStudentListtoString(List<String> input){
        String output = "/";
        for (String s : input){
            output += "," + s;
        }
        output = output.substring(0,1) + output.substring(2);
        return output;
    }


    @Override
    public void onItemsSelected(boolean[] selected, Context context){

    }

    public static void setFriends(List<Student> friends) {
        SearchFriendFragment.friends = friends;
    }

    static public List<Student> getFriends() {
        return friends;
    }

    public Student getCurrentUser() {
        return currentUser;
    }

}

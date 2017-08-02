package com.example.richardssurface.mff;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.richardssurface.mff.Utilities.CustomDialogTool;
import com.example.richardssurface.mff.Utilities.JSONParsingTool;
import com.example.richardssurface.mff.Utilities.RestDataReceiver;
import com.example.richardssurface.mff.Utilities.Student;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Richard's Surface on 5/4/2017.
 */

public class AllFriendsFragment extends Fragment {
    private View vMain;
    private ListView listView;
    private Button all_friend_map;
    private Student currentUser = LoginPage.getCurrentUser();

    protected List<HashMap<String, String>> FriendListArray;
    protected static List<Student> FriendObjectListArray;
    protected SimpleAdapter MyListAdapter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        vMain = inflater.inflate(R.layout.all_friend_fragment, container, false);
        listView = (ListView) vMain.findViewById(R.id.listView);
        all_friend_map = (Button) vMain.findViewById(R.id.all_friend_map);
        FriendObjectListArray = new ArrayList<>();

        final String[] colHEAD = new String[]{"sid", "FName", "LName"};
        final int[] dataCell = new int[]{R.id.sid, R.id.FName, R.id.LName};

        new AsyncTask<Void, Void, List<HashMap<String, String>>>() {
            @Override
            protected List<HashMap<String, String>> doInBackground(Void... params) {
                if(android.os.Debug.isDebuggerConnected())
                    android.os.Debug.waitForDebugger();
                List<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
                List<Student> temp = RestDataReceiver.getAllUserFriends(currentUser);
                for(Student s: temp){
                    HashMap<String, String> hm = new HashMap<String, String>();
                    hm.put("sid",s.getSid().toString());
                    hm.put("FName",s.getFName());
                    hm.put("LName",s.getLName());
                    data.add(hm);
                    FriendObjectListArray.add(s);
                }
                return data;
            }

            @Override
            protected void onPostExecute(List<HashMap<String, String>> FriendListArray) {
                MyListAdapter = new SimpleAdapter(getActivity(), FriendListArray, R.layout.list_view, colHEAD, dataCell);
                listView.setAdapter(MyListAdapter);
            }
        }.execute();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Student selectedStudent = FriendObjectListArray.get(position);
                String displayText = JSONParsingTool.parseStudentDialogInfo(selectedStudent);
                CustomDialogTool.viewFriendDetailDialog(getActivity(),selectedStudent,"Friend information",displayText);
            }
        });

        all_friend_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MapFragment fragment = new MapFragment();
                fragment.setTag(false);
                FragmentManager fragmentManager = getActivity().getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.content_frame, fragment, "all");
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        return vMain;
    }

    public static List<Student> getFriendObjectListArray() {
        return FriendObjectListArray;
    }
}

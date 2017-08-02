package com.example.richardssurface.mff.Utilities;

import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.richardssurface.mff.R;

/**
 * Created by Richard's Surface on 5/4/2017.
 */

public class CustomDialogTool {

    public static void viewFriendDetailDialog(Context context, Student student, String dialogTitle, String dialogText) {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.custom_dialog);
        dialog.setTitle(dialogTitle);

        // set the custom dialog components - text and button
        TextView info = (TextView) dialog.findViewById(R.id.info);
        info.setText(dialogText);

        final String keyword = student.getFavouriteMovie();
        final String snippet = "";
        final String imageUrl = "";
        new AsyncTask<Void, Void, String>() {

            @Override protected String doInBackground (Void...params){
                return GoogleSearchAPI.getMovieDescription(keyword);
            }

            @Override protected void onPostExecute (String snippet){
                TextView tv_snippet = (TextView) dialog.findViewById(R.id.tv_snippet);
                tv_snippet.setText(snippet);
            }
        }.execute();

        new AsyncTask<Void, Void, String>() {

            @Override protected String doInBackground (Void...params){
                return GoogleSearchAPI.getMovieImage(keyword);
            }

            @Override protected void onPostExecute (String imageUrl){
                ImageView imgv = (ImageView) dialog.findViewById(R.id.image);
                new DownloadImageTask(imgv)
                        .execute(imageUrl);
            }
        }.execute();

        Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}

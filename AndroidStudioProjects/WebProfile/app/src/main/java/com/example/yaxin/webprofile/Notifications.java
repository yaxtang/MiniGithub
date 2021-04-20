package com.example.yaxin.webprofile;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Yaxin on 11/2/2017.
 */

public class Notifications extends Fragment{
    View myView;
    public String TAG = com.example.yaxin.webprofile.Home.class.getSimpleName();
    public ProgressDialog pDialog;
    public TextView Oauth;
    TextView user;
    TextView useremail;
    public String username;
    public String user_email;
    public ListView lv;
    public Custom_Adapter_Notification adapter;

    ArrayList<item_follow> itemlist;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.notifications,container, false);
     //   ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Notification");
        itemlist = new ArrayList<>();
        lv = (ListView) myView.findViewById(R.id.list_notifications);
        new com.example.yaxin.webprofile.Notifications.GetNot().execute();
        return myView;
    }
    private class GetNot extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(getContext());
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();
            String jsonStr = sh.makeServiceCall("https://api.github.com/notifications?access_token=48723cf295203fd5f194be3eafaf8f65e73386ae&all=true");//"https://api.github.com/user?access_token=7f00f23585937070a2a1bd91b6ad6f1a12132b10");
            Log.e(TAG, "Response from url: " + jsonStr);
            if (jsonStr != null) {
                try {
                    JSONArray projects = new JSONArray(jsonStr);
                    // looping through All projects
                    for (int i = 0; i < projects.length(); i++) {
                        JSONObject c = projects.getJSONObject(i);
                        String is_read = c.getString("unread");
                        String reason = c.getString("reason");
                        item_follow newItem = new item_follow(is_read,reason);
                        itemlist.add(newItem);

                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity().getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity().getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (pDialog.isShowing())
                pDialog.dismiss();
            adapter = new Custom_Adapter_Notification(
                    getContext(), itemlist);
            lv.setAdapter(adapter);

        }

    }
}


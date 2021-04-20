package com.example.yaxin.webprofile;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Yaxin on 10/22/2017.
 */

/**
 * This class is Follow layout, which process the json file url and get variables
 * from there
 */
public class Follow extends Fragment{
    public Follow(){

    }
        public String TAG = com.example.yaxin.webprofile.Public_Repository.class.getSimpleName();
        public ProgressDialog pDialog;
        public ListView lv;
        public Custom_Adapter_Follow adapter;
        public View myView;
        Set followers;
        public static final String MyPREFERENCES = "MyPrefs";
        SharedPreferences sharedpreferences;

    // URL to get projects JSON

        private String url = "https://api.github.com/user/followers?access_token=7f00f23585937070a2a1bd91b6ad6f1a12132b10";
        ArrayList<item_follow> itemlist;
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

            myView = inflater.inflate(R.layout.follow,container, false);
         //   ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Followers");
            Bundle args = getArguments();
            if (args != null) {
                url = args.getString("url",url);
            }
            itemlist = new ArrayList<>();
            lv = (ListView) myView.findViewById(R.id.list_follow);
            new com.example.yaxin.webprofile.Follow.GetFollowers().execute();
            followers = new HashSet<>();

            return myView;
        }

        /**
         * Async task class to get json by making HTTP call
         */
        private class GetFollowers extends AsyncTask<Void, Void, Void> {
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
                // Making a request to url and getting response
                String jsonStr = sh.makeServiceCall(url);
                Log.e(TAG, "Response from url: " + jsonStr);

                if (jsonStr != null) {
                    try {
                        JSONArray projects = new JSONArray(jsonStr);
                        // looping through All projects
                        for (int i = 0; i < projects.length(); i++) {
                            JSONObject c = projects.getJSONObject(i);
                            String url = c.getString("avatar_url");
                            String name = c.getString("login");
                            followers.add(name);
                            item_follow newItem = new item_follow(name,url);

                            itemlist.add(newItem);

                        }
                        sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putStringSet("followers",followers);
                        editor.commit();


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
                // Dismiss the progress dialog
                if (pDialog.isShowing())
                    pDialog.dismiss();
                /**
                 * Updating parsed JSON data into ListView
                 * */
                adapter = new Custom_Adapter_Follow(
                        getContext(), itemlist);
                lv.setAdapter(adapter);
            }

        }
    }


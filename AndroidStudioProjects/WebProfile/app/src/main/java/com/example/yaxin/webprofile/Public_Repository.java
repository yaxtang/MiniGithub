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
 * Created by Yaxin on 10/23/2017.
 */

/**
 * get all my public repositories from myself
 */
public class Public_Repository extends Fragment {

    public String TAG = Public_Repository.class.getSimpleName();
    public ProgressDialog pDialog;
    public ListView lv;
    public Custom_Adapter_Repos adapter;
    public View myView;
    private String url = "https://api.github.com/user/repos?access_token=7f00f23585937070a2a1bd91b6ad6f1a12132b10";
    ArrayList<item> itemlist;
    ArrayList<String> starred;
    public static final String MyPREFERENCES = "MyPrefs";
    SharedPreferences sharedpreferences;
    Set repos;



    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.public_repos,container, false);
       // ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Public_Repository");

        Bundle args = getArguments();
        //url = "https://api.github.com/users/yaxtang";
        if (args != null) {
            url = args.getString("url",url);
        }
        itemlist = new ArrayList<>();
       // starred = new ArrayList<>();
        lv = (ListView) myView.findViewById(R.id.list);
        starred = new ArrayList<>() ;
        new com.example.yaxin.webprofile.Public_Repository.StarList().execute();
        new GetProjects().execute();
        repos =new HashSet<>();
        return myView;
    }
    /**
     * Async task class to get json by making HTTP call
     */
    /*public ArrayList<String> getStarredArray(){
        return starred;
    }*/
    private class GetProjects extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(getContext());
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(true);
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
                    // JSONArray jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONArray projects = new JSONArray(jsonStr);
                    // looping through All projects
                    for (int i = 0; i < projects.length(); i++) {

                        JSONObject c = projects.getJSONObject(i);

                        String proj = c.getString("name");// thats project name
                        String url = c.getString("html_url");
                        String descrip = c.getString("description");
                        if (descrip == null){
                            descrip = "No description provided";
                        }

                        JSONObject owners = c.getJSONObject("owner");
                        String name = owners.getString("login");
                        repos.add(name);
                        String starurl = "https://github.com/" + name + "/" + proj;
                        // int st = 0;
                        int icon = 0; //= getContext().getResources().getDrawable(R.drawable.star_disable);
                        for (int j = 0; j < starred.size(); j++) {
                            Log.e(TAG,"starred url :::::" + starred.get(j));

                            if (starurl.equals(starred.get(j))) {
                                Log.e(TAG,"urlstar" + "starurl :::::" + starred.get(j));
                                icon = 1;//getContext().getResources().getDrawable(R.drawable.star_enable);
                            }
                        }

                            item newItem = new item(name,url,descrip, proj, icon);
                        itemlist.add(newItem);

                    }
                    sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putStringSet("repos",repos);
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
/*
            // handle list
            HttpHandler shh = new HttpHandler();
            // Making a request to url and getting response
            String starstring = shh.makeServiceCall("https://api.github.com/users/yaxtang/starred");
            Log.e(TAG, "Star Response from url: " + starstring);
            if (starstring != null) {
                try {
                    // JSONArray jsonObj = new JSONObject(jsonStr);
                    // Getting JSON Array node
                    JSONArray projects = new JSONArray(starstring);
                    // looping through All projects
                    for (int i = 0; i < projects.length(); i++) {
                        JSONObject c = projects.getJSONObject(i);
                        String url = c.getString("html_url");
                        starred.add(url);
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

            }*/
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
            adapter = new Custom_Adapter_Repos(
                    getContext(), itemlist);
            lv.setAdapter(adapter);
        }

    }

    private class StarList extends AsyncTask<Void, Void, Void> {
        // handle list
        @Override
        protected Void doInBackground(Void... arg0) {
            //   starred = new ArrayList<>();
            HttpHandler shh = new HttpHandler();
            // Making a request to url and getting response
            String starstring = shh.makeServiceCall("https://api.github.com/users/yaxtang/starred");
            Log.e(TAG, "Star Response from url: " + starstring);
            if (starstring != null) {
                try {
                    // JSONArray jsonObj = new JSONObject(jsonStr);
                    // Getting JSON Array node
                    JSONArray projects = new JSONArray(starstring);
                    // looping through All projects
                    for (int i = 0; i < projects.length(); i++) {
                        JSONObject c = projects.getJSONObject(i);
                        String url = c.getString("html_url");
                        Log.e(TAG,"html_url" + url);
                        starred.add(url);
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");

            }
            return null;
        }
    }
}
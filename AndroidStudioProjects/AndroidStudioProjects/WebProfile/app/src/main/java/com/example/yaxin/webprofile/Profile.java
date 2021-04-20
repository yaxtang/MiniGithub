package com.example.yaxin.webprofile;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Yaxin on 10/22/2017.
 */
/*
import android.app.Fragment;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class Profile extends Fragment {
    View myView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.profile_item, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Profile");


        Button follow = (Button) myView.findViewById(R.id.Followers);
        follow.setPaintFlags(follow.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        Button following = (Button) myView.findViewById(R.id.Following);
        following.setPaintFlags(following.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        Button public_ = (Button) myView.findViewById(R.id.public_repos);
        public_.setPaintFlags(public_.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        return myView;
    }
}
    /** Called when the user taps the Send button *
    public void changeToFollowPage(View view) {
        Fragment fragment = Profile.newInstance();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.container_layout, fragment).commit();
        Intent intent = new Intent(this, Follow.class);
        startActivity(intent);
    }
}
*/
//package com.example.yaxin.webprofile;

public class Profile extends Fragment {

    public Profile(){

    }
    public String TAG = Profile.class.getSimpleName();
    public ProgressDialog pDialog;
    public Custom_Adapter_Profile adapter;
    public View myView;
    public ListView lv;

    public static final String MyPREFERENCES = "MyPrefs";
    SharedPreferences sharedpreferences;

    // URL to get projects JSON
    public String url;// = "https://api.github.com/users/yaxtang";
    // ArrayList<HashMap<String, String>> projectList;
    ArrayList<item_profile> itemList;
    private String follo;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.profile, container, false);
        //((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Profile");
        ActionBar toggle = ((AppCompatActivity) getActivity()).getSupportActionBar();
        Drawable drawable= getResources().getDrawable(R.drawable.profile);
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
        Drawable newdrawable = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 80, 80, true));
        newdrawable.setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_ATOP);
        toggle.setHomeAsUpIndicator(newdrawable);
       toggle.setDisplayHomeAsUpEnabled(true);
        //toggle.setHomeButtonEnabled(true);

        //getActivity().getActionBar().setHomeAsUpIndicator(newdrawable);
        //getActivity().getActionBar().syncState();
        Bundle args = getArguments();
        url = "https://api.github.com/users/yaxtang?access_token=7f00f23585937070a2a1bd91b6ad6f1a12132b10";
        if (args != null) {
            url = args.getString("url_from_follow",url);
        }
        //url = follo;
        Log.e(TAG,"url" + url);
//        url = getArguments().getString("url");
        //return inflater.inflate(R.layout.fragment, container, false);
        //lv =  myView.findViewById(R.id.profile_lay);
        itemList = new ArrayList<>();
        lv = (ListView) myView.findViewById(R.id.list_profile);
        new Getinfo().execute();
        return myView;
    }

    private class Getinfo extends AsyncTask<Void, Void, Void> {
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

                    JSONObject info = new JSONObject(jsonStr);
                    String name = info.getString("login");

                    String github = info.getString("html_url");
                    String bio = info.getString("bio");
                    Log.e(TAG, "bio info " + bio);
                    if (bio == "null") {
                        bio = "no bio provided";
                    }
                    String avatar = info.getString("avatar_url");
                    String email = info.getString("email");
                    if (email == "null") {
                        email = "no email provided";
                    }
                    Log.e(TAG, "bio infoooe " + email);
                    int follower_count = info.getInt("followers");
                    int follwering_count = info.getInt("following");
                    int publicRepos_count = info.getInt("public_repos");

                    //  itemList = new ArrayList<>();
                    Log.e(TAG, "bio infooo " + bio);
                    item_profile a = new item_profile(
                            name, github, bio, avatar, email, follower_count, follwering_count, publicRepos_count);
                    Log.e(TAG, "all " + name + github + bio + avatar + email + follower_count + follwering_count + publicRepos_count);
                    itemList.add(a);
                    sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString("username",name);
                    editor.putString("email",email);
                    editor.putString("github_url",github);
                    editor.putString("bio",bio);
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
           /* adapter = new Custom_Adapter_Profile(
                    getContext(), itemList);
            */adapter = new Custom_Adapter_Profile(
                    getContext(), itemList);

           /* Log.e(TAG,"itemList:"+itemList.get(0).email);
            Log.e(TAG,"itemList:"+itemList.get(0).name);
            Log.e(TAG,"itemList:"+itemList.get(0).bio);
            Log.e(TAG,"itemList:"+itemList.get(0).github);
            Log.e(TAG,"itemList:"+itemList.get(0).avatar);

            Log.e(TAG,"itemList:"+itemList.get(0).follower_count);

            Log.e(TAG,"itemList:"+itemList.get(0).follwering_count);

            Log.e(TAG,"itemList:"+itemList.get(0).publicRepos_count);
            */lv.setAdapter(adapter);
        }
    }
}
/*
            if(myView == null){
                myView = LayoutInflater.from(context).inflate(R.layout.profile_item, null, false);
            }
            myView = inflter.inflate(R.layout.profile_item, null);
            TextView name_ = (TextView) myView.findViewById(R.id.profile_name);
            TextView bio_ = (TextView) myView.findViewById(R.id.profile_bio);
            TextView github_ = (TextView) myView.findViewById(R.id.profile_github);
            TextView email_ = (TextView) myView.findViewById(R.id.profile_email);
            ImageView avatar_ = (ImageView) myView.findViewById(R.id.profile_avatar);
            String avatar_url = itemList.avatar;
            name_.setText(itemList.name);
            bio_.setText(itemList.bio);
            github_.setText(itemList.github);
            email_.setText(itemList.email);
            int follow_count = itemList.follower_count;
            int following_count = itemList.follwering_count;
            int public_count = itemList.publicRepos_count;

            Button follow = (Button) myView.findViewById(R.id.Followers);
            follow.setText("Followers: " + follow_count);
            Button following = (Button) myView.findViewById(R.id.Following);
            following.setText("Following: " + following_count);
            Button public_ = (Button) myView.findViewById(R.id.public_repos);
            public_.setText("Public Repositories: " + public_count);
            new ImageDownloaderTask(avatar_).execute(avatar_url);
            //return myView;
        }
    }
}*/
/*
        public View getView(View view,ArrayList<item_profile> itemList) {
            view = inflter.inflate(R.layout.profile_item, null);
            TextView name_ = (TextView) view.findViewById(R.id.profile_name);
            TextView bio_ = (TextView) view.findViewById(R.id.profile_bio);
            TextView github_ = (TextView) view.findViewById(R.id.profile_github);
            TextView email_ = (TextView) view.findViewById(R.id.profile_email);
            ImageView avatar_ = (ImageView) view.findViewById(R.id.profile_avatar);
            String avatar_url = itemList.get(i).avatar;
            name_.setText(itemList.get(i).name);
            bio_.setText(itemList.get(i).bio);
            github_.setText(itemList.get(i).github);
            email_.setText(itemList.get(i).email);
            int follow_count = itemList.get(i).follower_count;
            int following_count = itemList.get(i).follwering_count;
            int public_count = itemList.get(i).publicRepos_count;


            Button follow = (Button) view.findViewById(R.id.Followers);
            follow.setText("Followers: " + follow_count);
            Button following = (Button) view.findViewById(R.id.Following);
            following.setText("Following: " + following_count);
            Button public_ = (Button) view.findViewById(R.id.public_repos);
            public_.setText("Public Repositories: " + public_count);
            new ImageDownloaderTask(avatar_).execute(avatar_url);
            return view;
        }
    }
}


import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/*import java.util.ArrayList;
public class Profile extends Fragment {

    public String TAG = Profile.class.getSimpleName();
    public ProgressDialog pDialog;
    public ListView lv;
    public Custom_Adapter_Repos adapter;
    public View myView;
    private static String url = "https://api.github.com/users/yaxtang/repos";
    ArrayList<item> itemlist;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.profile,container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Profile");
        itemlist = new ArrayList<>();
        lv = (ListView) myView.findViewById(R.id.list_profile);
        new GetProjects().execute();
        return myView;
    }private class GetProjects extends AsyncTask<Void, Void, Void> {
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
                        System.out.print("hello output: "+proj+" "+ url+" "+descrip+" "+name);
                        Log.e(TAG, "hello output: "+proj+" "+ url+" "+descrip+" "+name);

                        // tmp hash map for single contact
                        // HashMap<String, String> project = new HashMap<>();
                        item newItem = new item(name,url,descrip, proj);
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
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            adapter = new Custom_Adapter_Repos(
                    getContext(), itemlist);
            lv.setAdapter(adapter);
        }

    }
}*/
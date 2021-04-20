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
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Yaxin on 10/22/2017.
 */

/**
 * Home layout, under construction....
 */
public class Home extends Fragment{
    View myView;
    public String TAG = com.example.yaxin.webprofile.Home.class.getSimpleName();
    public ProgressDialog pDialog;
    //public ListView lv;
    //public Custom_Adapter_Follow adapter;
    public TextView Oauth;
    TextView user;
    TextView useremail;
    public String username;
    public String user_email;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.home,container, false);
      //  ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Login");
        Oauth = (TextView)myView.findViewById(R.id.Oauth);
        Button login = (Button)myView.findViewById(R.id.Login);
        //user = (TextView).findViewById(R.id.user);
        //useremail = (TextView) myView.findViewById(R.id.useremail);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new com.example.yaxin.webprofile.Home.GetOauth().execute();
            }
    });
        return myView;
    }
        private class GetOauth extends AsyncTask<Void, Void, Void> {
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
            String Str = sh.makeServiceCall("https://api.github.com/user?access_token=7f00f23585937070a2a1bd91b6ad6f1a12132b10");
            Log.e(TAG, "Response from url: " + Str);
           /* if (jsonStr != null) {
                try {
                    JSONArray projects = new JSONArray(jsonStr);
                    // looping through All projects
                    for (int i = 0; i < projects.length(); i++) {
                        JSONObject c = projects.getJSONObject(i);
                        String url = c.getString("avatar_url");
                        String name = c.getString("login");
                        item_follow newItem = new item_follow(name,url);
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
*/
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
           Oauth.setText("Waiting for Authorization...\nAuthroization Success!");
         //   user.setText("Yaxin Tang");
         //   useremail.setText("yaxin.t@gmail.com");
        }

    }
}

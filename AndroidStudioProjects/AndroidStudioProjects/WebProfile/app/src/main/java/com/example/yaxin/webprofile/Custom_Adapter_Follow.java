package com.example.yaxin.webprofile;

/**
 * Created by Yaxin on 10/23/2017.
 */

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.content.ContentValues.TAG;
import static com.example.yaxin.webprofile.R.id.follow_button;
import static com.example.yaxin.webprofile.R.id.name;
import static com.example.yaxin.webprofile.R.id.unfollow_button;

/**
 * this adapter is for follow class, build a veiw return a view that has corresponding
 * variables
 */
public class Custom_Adapter_Follow extends BaseAdapter {
    private Context context;
    ArrayList<item_follow> itemList = new ArrayList<item_follow>();
    LayoutInflater inflter;

    public Custom_Adapter_Follow(Context applicationContext, ArrayList<item_follow> projList) {
        this.itemList = projList;
        this.context = applicationContext;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.follow, viewGroup, false);
        }
        view = inflter.inflate(R.layout.follow_item, null);
        TextView name_ = (TextView) view.findViewById(name);
        ImageView links_ = (ImageView) view.findViewById(R.id.avatar);
        final String url = itemList.get(i).link;
        final String username = itemList.get(i).name;
        name_.setText(username);
        final String url_from_follow = "https://api.github.com/users/" + username;
        new ImageDownloaderTask(links_).execute(url);
        links_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // do stuff
                if (view != null) {
                    Log.e(TAG, "correct in view" + url_from_follow);
                    // Create fragment and give it an argument for the selected article
                    Profile newFragment = new Profile();
                    Bundle args = new Bundle();
                    args.putString("url_from_follow", url_from_follow);
                    newFragment.setArguments(args);
                    // Activity activity = (Activity) context;
                    // FragmentManager fragmentManager = activity.getFragmentManager();
                    ((AppCompatActivity) context)
                            .getFragmentManager().beginTransaction()
                            .replace(R.id.content_frame, newFragment).show(newFragment)
                            .addToBackStack(null)
                            .commit();
                }
            }
        });
        final Button follow_ = (Button) view.findViewById(follow_button);
        follow_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new com.example.yaxin.webprofile.Custom_Adapter_Follow.handleFollow(username).execute();
             //   follow_.setTextColor(Color.parseColor("white"));
            }
        });
        final Button unfollow_ = (Button) view.findViewById(unfollow_button);
        unfollow_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new com.example.yaxin.webprofile.Custom_Adapter_Follow.handleUnfollow(username).execute();
            //    unfollow_.setTextColor(Color.parseColor("white"));
            }
        });

        return view;
    }

    private class handleFollow extends AsyncTask<Void, Void, Void> {
        String username;

        private handleFollow(String username) {
            this.username = username;
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            OkHttpClient client = new OkHttpClient();
            MediaType mediaType = MediaType.parse("multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW");
            RequestBody body = RequestBody.create(mediaType, "------WebKitFormBoundary7MA4YWxkTrZu0gW\r\nContent-Disposition: form-data; name=\"cs\"\r\n\r\n870583beb40212e96ae46ab7d122b9133d12f417\r\n------WebKitFormBoundary7MA4YWxkTrZu0gW--");
            Request request = new Request.Builder()
                    .url("https://api.github.com/user/following/" + username)
                    .put(body) //870583beb40212e96ae46ab7d122b9133d12f417
                    .addHeader("authorization", "Bearer 7f00f23585937070a2a1bd91b6ad6f1a12132b10")
                    .addHeader("content-type", "multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW")
                    .addHeader("cs", "7f00f23585937070a2a1bd91b6ad6f1a12132b10")
                    .addHeader("cache-control", "no-cache")
                    .addHeader("postman-token", "92ae9711-f474-4f08-5257-23107fee4a47")
                    .build();
            try {
                Response response = client.newCall(request).execute();
                Log.e(TAG, "Response: " + response.body().string());
            } catch (IOException e) {
                e.printStackTrace();
            }
            //follow_.setTextColor(Color.parseColor("black"));
            return null;
        }

       /* @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog

            follow_.setTextColor(Color.parseColor("black"));
        }*/
    }

    private class handleUnfollow extends AsyncTask<Void, Void, Void> {
        String username;

        private handleUnfollow(String username) {
            this.username = username;
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            OkHttpClient client = new OkHttpClient();

            MediaType mediaType = MediaType.parse("multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW");
            RequestBody body = RequestBody.create(mediaType, "------WebKitFormBoundary7MA4YWxkTrZu0gW\r\nContent-Disposition: form-data; name=\"cs\"\r\n\r\n870583beb40212e96ae46ab7d122b9133d12f417\r\n------WebKitFormBoundary7MA4YWxkTrZu0gW--");
            Request request = new Request.Builder()
                    .url("https://api.github.com/user/following/" + username)
                    .delete(body) //870583beb40212e96ae46ab7d122b9133d12f417
                    .addHeader("authorization", "Bearer 7f00f23585937070a2a1bd91b6ad6f1a12132b10")
                    .addHeader("content-type", "multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW")
                    .addHeader("cs", "7f00f23585937070a2a1bd91b6ad6f1a12132b10")
                    .addHeader("cache-control", "no-cache")
                    .addHeader("postman-token", "92ae9711-f474-4f08-5257-23107fee4a47")
                    .build();
            try {
                Response response = client.newCall(request).execute();
                Log.e(TAG, "Response: " + response.body().string());
            } catch (IOException e) {
                e.printStackTrace();
            }
            //follow_.setTextColor(Color.parseColor("black"));
            return null;
        }
    }

}

package com.example.yaxin.webprofile;

/**
 * Created by Yaxin on 10/23/2017.
 */

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.content.ContentValues.TAG;

/**
 * This is a custom build adapter for public repository class, use for listview layout to
 * to show multiple things.
 */
public class Custom_Adapter_Repos extends BaseAdapter {
    private Context context;
    ArrayList<item> itemList = new ArrayList<item>();
    ;
    LayoutInflater inflter;
    //Public_Repository a = new Public_Repository();
    ArrayList<String> starred;


    public Custom_Adapter_Repos(Context applicationContext, ArrayList<item> projList) {
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
            view = LayoutInflater.from(context).inflate(R.layout.public_repos, viewGroup, false);
        }
        //final item currentitem = itemList.get(i);
        //   star = new com.example.yaxin.webprofile.Custom_Adapter_Follow.handleUnfollow(username).execute();
        view = inflter.inflate(R.layout.repos_item, null);
        TextView project = (TextView) view.findViewById(R.id.proj);
        TextView name_ = (TextView) view.findViewById(R.id.name);
        TextView descrip_ = (TextView) view.findViewById(R.id.descrip);
        final ImageButton icon_ = (ImageButton) view.findViewById(R.id.icon);
        Button links_ = (Button) view.findViewById(R.id.Open);
        links_.setText("OPEN");
        final String url = itemList.get(i).link;
        links_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse(url));
                context.startActivity(intent);
            }
        });
        //links_.setMovementMethod(LinkMovementMethod.getInstance());
        final String project_name = itemList.get(i).proj;
        final String username = itemList.get(i).name;
        name_.setText(username);
        project.setText(project_name);
        int icon_image = itemList.get(i).icon;
        //icon_.setBackgroundDrawable(icon_image);
        if (icon_image == 1){
            icon_.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.star_enable));
        }
        else{
            icon_.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.star_disable));
        }
        name_.setText(itemList.get(i).name);
        descrip_.setText(itemList.get(i).descrip);
        descrip_.setMovementMethod(new ScrollingMovementMethod());
        icon_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bitmap bitmap = ((BitmapDrawable)icon_.getBackground()).getBitmap();
                Bitmap bitmap2 = ((BitmapDrawable)(context.getResources().getDrawable(R.drawable.star_disable)))
                        .getBitmap();
                Log.e(TAG, "background "+ bitmap+ "disable"+ bitmap2);
               if (bitmap.equals
                       (bitmap2)){
                    new com.example.yaxin.webprofile.Custom_Adapter_Repos.handleStar(project_name, username).execute();
                    icon_.setBackgroundResource(R.drawable.star_enable);
                }
                else {
                    new com.example.yaxin.webprofile.Custom_Adapter_Repos.handleUnstar(project_name, username).execute();
                    icon_.setBackgroundResource(R.drawable.star_disable);
                }


            }
        });
        return view;
    }

    private class handleStar extends AsyncTask<Void, Void, Void> {
        String projname;
        String username;

        private handleStar(String projname, String username) {
            this.projname = projname;
            this.username = username;
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            OkHttpClient client = new OkHttpClient();
            MediaType mediaType = MediaType.parse("multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW");
            RequestBody body = RequestBody.create(mediaType, "------WebKitFormBoundary7MA4YWxkTrZu0gW\r\nContent-Disposition: form-data; name=\"cs\"\r\n\r\n870583beb40212e96ae46ab7d122b9133d12f417\r\n------WebKitFormBoundary7MA4YWxkTrZu0gW--");
            Request request = new Request.Builder()
                    .url("https://api.github.com/user/starred/" + username + "/" + projname)
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

    }

    private class handleUnstar extends AsyncTask<Void, Void, Void> {
        String projname;
        String username;

        private handleUnstar(String projname, String username) {
            this.projname = projname;
            this.username = username;
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            OkHttpClient client = new OkHttpClient();
            MediaType mediaType = MediaType.parse("multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW");
            RequestBody body = RequestBody.create(mediaType, "------WebKitFormBoundary7MA4YWxkTrZu0gW\r\nContent-Disposition: form-data; name=\"cs\"\r\n\r\n870583beb40212e96ae46ab7d122b9133d12f417\r\n------WebKitFormBoundary7MA4YWxkTrZu0gW--");
            Request request = new Request.Builder()
                    .url("https://api.github.com/user/starred/" + username + "/" + projname)
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
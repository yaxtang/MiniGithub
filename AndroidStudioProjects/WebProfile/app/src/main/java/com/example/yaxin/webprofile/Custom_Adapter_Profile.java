package com.example.yaxin.webprofile;

import android.content.Context;
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

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

/**
 * Created by Yaxin on 10/23/2017.
 */


public class Custom_Adapter_Profile extends BaseAdapter {
    private Context context;
    ArrayList<item_profile> itemList = new ArrayList<item_profile>();;
    LayoutInflater inflter;
    public Custom_Adapter_Profile(Context applicationContext, ArrayList<item_profile> projList) {
        this.itemList = projList;
        this.context = applicationContext;
        inflter = (LayoutInflater.from(applicationContext));
        Log.e(TAG,"you are in adater");
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
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.profile, viewGroup,false);
        }
        view = inflter.inflate(R.layout.profile_item, null);

        TextView name_ = (TextView) view.findViewById(R.id.profile_name);
        TextView bio_ = (TextView)view.findViewById(R.id.profile_bio);

        TextView github_ = (TextView)view.findViewById(R.id.profile_github);
        TextView email_ = (TextView)view.findViewById(R.id.profile_email);
        ImageView avatar_ = (ImageView) view.findViewById(R.id.profile_avatar);
        ImageView avatar_back = (ImageView) view.findViewById(R.id.avatar_background);
        final String avatar_url = itemList.get(i).avatar;
        new ImageDownloaderTask(avatar_).execute(avatar_url);
        new ImageDownloaderTask(avatar_back).execute(avatar_url);
        final String name = itemList.get(i).name;
        name_.setText(name);
        bio_.setText(itemList.get(i).bio);
        github_.setText(itemList.get(i).github);
        email_.setText(itemList.get(i).email);
        int follow_count = itemList.get(i).follower_count;
        int following_count = itemList.get(i).follwering_count;
        int public_count = itemList.get(i).publicRepos_count;

       Button follow = (Button) view.findViewById(R.id.Followers);
        follow.setText("Followers: "+ follow_count);
        follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // do stuff
                if (view != null) {
                    // Create fragment and give it an argument for the selected article
                    Follow newFragment = new Follow();
                    Bundle args = new Bundle();
                    args.putString("url", "https://api.github.com/users/"+name +"/followers");
                    newFragment.setArguments(args);
                    ((AppCompatActivity)context)
                            .getFragmentManager().beginTransaction()
                            .replace(R.id.content_frame, newFragment).show(newFragment)
                            .addToBackStack(null)
                            .commit();
                }
            }});
        Button following = (Button) view.findViewById(R.id.Following);
        following.setText("Following: "+ following_count);
        following.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // do stuff
                if (view != null) {
                    // Create fragment and give it an argument for the selected article
                    Following newFragment = new Following();
                    Bundle args = new Bundle();
                    args.putString("url", "https://api.github.com/users/"+name +"/following");
                    newFragment.setArguments(args);
                    ((AppCompatActivity)context)
                            .getFragmentManager().beginTransaction()
                            .replace(R.id.content_frame, newFragment).show(newFragment)
                            .addToBackStack(null)
                            .commit();
                }
            }});


        Button public_ = (Button) view.findViewById(R.id.public_repos);
        public_.setText("Public Repositories: "+ public_count);
        public_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // do stuff
                if (view != null) {
                    // Create fragment and give it an argument for the selected article
                    Public_Repository newFragment = new Public_Repository();
                    Bundle args = new Bundle();
                    args.putString("url", "https://api.github.com/users/"+name +"/repos");
                    newFragment.setArguments(args);
                    ((AppCompatActivity)context)
                            .getFragmentManager().beginTransaction()
                            .replace(R.id.content_frame, newFragment).show(newFragment)
                            .addToBackStack(null)
                            .commit();
                }
            }});

        return view;
    }
}

package com.example.yaxin.webprofile;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import java.util.ArrayList;

/**
 * Created by yaxin on 05/11/2017.
 */


    public class CustomListAdapter_search extends BaseAdapter {
        private Context context; //context
        private ArrayList<item_search> items; //data source of the list adapter
        //private boolean is_user;
        private Activity act;
        //private Activity main_act = MainActivity.get_main_act();


    //public constructor
        public CustomListAdapter_search(Context context, Activity act,  ArrayList<item_search> items) {
            this.context = context;
            this.items = items;
            //this.is_user = is_user;
           // Log.e("adapter 35",  is_user?"true":"false");
            this.act = act;
        }

        @Override
        public int getCount() {
            return items.size(); //returns total of items in the list
        }

        @Override
        public Object getItem(int position) {
            return items.get(position); //returns list item at the specified position
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // inflate the layout for each list row
            if (convertView == null) {
                convertView = LayoutInflater.from(context).
                        inflate(R.layout.list_item_search, parent, false);
                // list_item _search
            }

            // get current item to be displayed
            final item_search currentItem = (item_search) getItem(position);
            // get the TextView for item name and item description
            Button textViewItemName = (Button)
                    convertView.findViewById(R.id.list_item_search);
            textViewItemName.setText(currentItem.name);
            //Log.e("A","in adapter 66");

            textViewItemName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(currentItem.is_user){
                        Intent intent = new Intent(act, MainActivity.class);
                        Bundle bundle = new Bundle();
                        String url_from_search = "https://api.github.com/users/" + currentItem.user.name;
                        bundle.putString("url_from_follow",url_from_search);
                        intent.putExtra("open_search_profile", bundle);
                        context.startActivity(intent);
                    }
                    else{
                        //repo
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_VIEW);
                        intent.addCategory(Intent.CATEGORY_BROWSABLE);
                        intent.setData(Uri.parse(currentItem.repo_url));
                        //Log.e("adpater 88", "repo_url"+currentItem.repo_url);
                        context.startActivity(intent);
                    }
                }
            });
            //Log.e("A","in adapter 92");
            return convertView;
        }


    }


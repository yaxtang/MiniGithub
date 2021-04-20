package com.example.yaxin.webprofile;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
/***************
 * Bundle bundle = new Bundle();
 bundle.putString("edttext", "From Activity");
 // set Fragmentclass Arguments
 Fragmentclass fragobj = new Fragmentclass();
 fragobj.setArguments(bundle);
 */

/**
 * Navigation Drawer in here and open fragments for each layout and event
 */
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    NavigationView navigationView;
    public String TAG = com.example.yaxin.webprofile.Public_Repository.class.getSimpleName();
    public ProgressDialog pDialog;
    ActionBarDrawerToggle toggle;
  //  public ArrayList<String> starred = new ArrayList<String>();
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
/**
 * This piece for code is modified from https://stackoverflow.com/questions/8701634/send-email-intent
 */
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Email = new Intent(Intent.ACTION_SEND);
                Email.setType("text/email");
                Email.putExtra(Intent.EXTRA_EMAIL,
                        new String[]{"yaxin.t@gmail.com"});  //developer 's email
                Email.putExtra(Intent.EXTRA_SUBJECT,
                        "Add your Subject"); // Email 's Subject
                Email.putExtra(Intent.EXTRA_TEXT, "Dear," + "");  //Email 's Greeting text
                startActivity(Intent.createChooser(Email, "Send Feedback:"));
            }
        });

        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        //ActionBarDrawerToggle
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        changeIcon(getResources().getDrawable(R.drawable.home), getSupportActionBar());
        drawer.setDrawerListener(toggle);

        //toggle.syncState();
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Intent intent = getIntent();
        if(intent.hasExtra("open_search_profile") ){
            Bundle bundle = intent.getBundleExtra("open_search_profile");
            Profile profile = new Profile();
            profile.setArguments(bundle);

            getFragmentManager().beginTransaction()
                    .replace(R.id.content_frame, profile)
                    .commit();
        }
        }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        Log.e("A","Handle Intent 95");

        //   MenuItem searchItem = menu.findItem(R.id.action_search);
      //  SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        // Associate searchable configuration with the SearchView
      //  SearchManager searchManager =
       //         (SearchManager) getSystemService(Context.SEARCH_SERVICE);
       // searchView.setSearchableInfo(
        //        searchManager.getSearchableInfo(getComponentName()));
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.action_search).getActionView();
        Log.e("Tag", "SEARCHview"+searchView);
        searchView.setQueryHint("Enter username and respositoy");
        Log.e("Tag", "component"+getComponentName());
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        Log.e("A","Handle Intent 111");



        // Define the listener
      /*  MenuItemCompat.OnActionExpandListener expandListener = new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                // Do something when action item collapses
                return true;  // Return true to collapse action view
            }
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                // Do something when expanded
                return true;  // Return true to expand action view
            }
        };
        // Get the MenuItem for the action item
        MenuItem actionMenuItem = menu.findItem(R.id.action_search);

        // Assign the listener to that action item
        MenuItemCompat.setOnActionExpandListener(actionMenuItem, expandListener);
        // Any other things you have to do when creating the options menu…
        // Configure the search info and add any event listeners...
           // return super.onCreateOptionsMenu(menu);
       // }*/
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.filter) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        android.app.FragmentManager fragmentManager = getFragmentManager();
        if (id == R.id.nav_home) {
            // Handle the home
            getFragmentManager().beginTransaction()
                    .replace(R.id.content_frame, new Home())
                    .commit();
            changeIcon(getResources().getDrawable(R.drawable.home), getSupportActionBar());

        } else if (id == R.id.nav_profile) {

            //handle profile_item
            //changeIcon(getResources().getDrawable(R.drawable.profile));

            getFragmentManager().beginTransaction()
                    .replace(R.id.content_frame, new Profile())
                    .commit();
            //changeIcon(getResources().getDrawable(R.drawable.profile));
            changeIcon(getResources().getDrawable(R.drawable.profile), getSupportActionBar());
        }
         else if (id == R.id.nav_notifications) {
                //handle profile_item
                getFragmentManager().beginTransaction()
                        .replace(R.id.content_frame, new Notifications())
                        .commit();
            //changeIcon(getResources().getDrawable(R.drawable.home));
            changeIcon(getResources().getDrawable(R.drawable.notif), getSupportActionBar());


        } else if (id == R.id.nav_follow) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.content_frame, new Follow())
                    .commit();
            //changeIcon(getResources().getDrawable(R.drawable.follow));
            changeIcon(getResources().getDrawable(R.drawable.follow), getSupportActionBar());


        } else if (id == R.id.nav_following) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.content_frame, new Following())
                    .commit();
            changeIcon(getResources().getDrawable(R.drawable.following), getSupportActionBar());

            //changeIcon(getResources().getDrawable(R.drawable.following));

        } else if (id == R.id.nav_public_repos) {
           /* Bundle bundle = new Bundle();
            bundle.putString("edttext", "From Activity");
            // set Fragmentclass Arguments
            Public_Repository p = new Public_Repository();
            p.setArguments(bundle);
*/
            getFragmentManager().beginTransaction()
                    .replace(R.id.content_frame, new Public_Repository())
                    .commit();
            changeIcon(getResources().getDrawable(R.drawable.about), getSupportActionBar());

            //changeIcon(getResources().getDrawable(R.drawable.about));

        }
        else if (id == R.id.nav_contributes) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.content_frame, new TopContributes())
                    .commit();
            changeIcon(getResources().getDrawable(R.drawable.contributes), getSupportActionBar());
        }else if (id == R.id.nav_comments) {

            getFragmentManager().beginTransaction()
                    .replace(R.id.content_frame, new TopComments())
                    .commit();
            changeIcon(getResources().getDrawable(R.drawable.comments), getSupportActionBar());

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    /** Called when the user taps the Send button */

    public void changePage(View v){
        //Fragment newFragment;
        if (v == findViewById(R.id.avatar) || v == findViewById(R.id.profile_avatar) ) {
            navigationView.setCheckedItem(R.id.nav_profile);
            changeIcon(getResources().getDrawable(R.drawable.profile), getSupportActionBar());
        }
        else if(v == findViewById(R.id.Followers)){
           // Button b = (Button)v.findViewById(R.id.Followers);

            getFragmentManager().beginTransaction()
                    .replace(R.id.content_frame, new Follow())
                    .commit();
            navigationView.setCheckedItem(R.id.nav_follow);
            changeIcon(getResources().getDrawable(R.drawable.follow), getSupportActionBar());

            // changeIcon(getResources().getDrawable(R.drawable.follow));
            //change the bar color corresponding to
        }
        else if(v == findViewById(R.id.Following)){
            getFragmentManager().beginTransaction()
                    .replace(R.id.content_frame, new Following())
                    .commit();
            navigationView.setCheckedItem(R.id.nav_following);
            changeIcon(getResources().getDrawable(R.drawable.following), getSupportActionBar());

        }
        else if(v == findViewById(R.id.public_repos)){
            getFragmentManager().beginTransaction()
                    .replace(R.id.content_frame, new Public_Repository())
                    .commit();
            navigationView.setCheckedItem(R.id.nav_public_repos);
            changeIcon(getResources().getDrawable(R.drawable.about), getSupportActionBar());

            //   changeIcon(getResources().getDrawable(R.drawable.about));
        }
        /*FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();*/
    }
    public void changeIcon(Drawable icon, ActionBar toggle){
        Drawable drawable= icon;//getResources().getDrawable(R.drawable.home);
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
        Drawable newdrawable = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 80, 80, true));
        newdrawable.setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_ATOP);
        toggle.setHomeAsUpIndicator(newdrawable);
        toggle.setDisplayHomeAsUpEnabled(true);
     }
    /**
     * Called when the user taps the Send button
     */
   /* public_repo void changeToFollowPage(View view) {
        Fragme
        android.app.FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .show(fragmentManager.findFragmentByTag("one"))
                        .commit();

        //.show(R.id.content_frame, new Follow())
         //       .commit();
    }*/


}

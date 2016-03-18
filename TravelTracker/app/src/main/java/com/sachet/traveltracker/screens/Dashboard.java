package com.sachet.traveltracker.screens;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.sachet.traveltracker.R;
import com.sachet.traveltracker.fragments.FavouriteFragment;
import com.sachet.traveltracker.fragments.NotificationFragment;
import com.sachet.traveltracker.fragments.UserProfileFragment;

public class Dashboard extends AppCompatActivity {

    private static final int NOTIFICATION_FRAGMENT = 0;
    private static final int FAVOURITE_FRAGMENT = 1;
    private static final int USER_PROFILE_FRAGMENT = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        setActionBar();
        displayView(NOTIFICATION_FRAGMENT);
        setViewObjects();
    }

    private void setViewObjects(){
        ((ImageButton)findViewById(R.id.notification)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayView(NOTIFICATION_FRAGMENT);
            }
        });

        ((ImageButton)findViewById(R.id.favourite)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayView(FAVOURITE_FRAGMENT);
            }
        });

        ((ImageButton)findViewById(R.id.userProfile)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayView(USER_PROFILE_FRAGMENT);
            }
        });
        ((ImageButton)findViewById(R.id.switchProfile)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Switch User Profile");
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //Write your logic here
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void setActionBar() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    private void displayView(int position) {
        Fragment fragment = null;
        String title = getString(R.string.app_name);
        switch (position) {
            case 0:
                fragment = new NotificationFragment();
                title = getString(R.string.title_notification);
                break;
            case 1:
                fragment = new FavouriteFragment();
                title = getString(R.string.title_favourite);
                break;
            case 2:
                fragment = new UserProfileFragment();
                title = getString(R.string.title_user_profile);
                break;
            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.commit();

            // set the toolbar title
            getSupportActionBar().setTitle(title);
        }
    }
}

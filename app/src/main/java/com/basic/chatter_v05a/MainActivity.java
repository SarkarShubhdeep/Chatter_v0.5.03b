package com.basic.chatter_v05a;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.TableLayout;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.iid.FirebaseInstanceId;

import java.io.IOException;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private Toolbar mToolbar;

    private ViewPager mViewPager;
    private DatabaseReference mUserRef;
    private SectionPageAdapter mSectionPageAdapter;

    private TabLayout mTabLayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser == null) {

            sendToStart();

        }else {

            mUserRef = FirebaseDatabase.getInstance().getReference().child("Users").child(mAuth.getCurrentUser().getUid());

            mToolbar = (Toolbar) findViewById(R.id.main_page_toolbar);
            setSupportActionBar(mToolbar);
            getSupportActionBar().setTitle("Chatter");

            //Tabs
            mViewPager = (ViewPager) findViewById(R.id.mainPager);
            mSectionPageAdapter = new SectionPageAdapter(getSupportFragmentManager());

            mViewPager.setAdapter(mSectionPageAdapter);

            mTabLayout = (TabLayout) findViewById(R.id.main_tabs);
            mTabLayout.setupWithViewPager(mViewPager);

            //Tabs Icon
            mTabLayout.getTabAt(0).setIcon(R.drawable.icon_requests_light);
            mTabLayout.getTabAt(0).setText("");

            mTabLayout.getTabAt(1).setIcon(R.drawable.icon_chats_light);
            mTabLayout.getTabAt(1).setText("");
            mTabLayout.getTabAt(1).select();

            mTabLayout.getTabAt(2).setIcon(R.drawable.icon_friends_light);
            mTabLayout.getTabAt(2).setText("");


        }

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.

        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser == null) {

            sendToStart();

        }else{

            mUserRef.child("online").setValue("true");


        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser!=null)
            mUserRef.child("online").setValue(ServerValue.TIMESTAMP);
    }

    private void sendToStart() {
        Intent startIntent = new Intent(MainActivity.this, StartActivity.class);
        startActivity(startIntent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.main_menu, menu);


        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
            super.onOptionsItemSelected(item);

            if (item.getItemId() == R.id.main_logout_btn) {

                FirebaseAuth.getInstance().signOut();


                sendToStart();

                mUserRef.child("online").setValue(ServerValue.TIMESTAMP);


            }
            if (item.getItemId() == R.id.main_settings_btn) {
                Intent settingsIntent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(settingsIntent);

            }
            if (item.getItemId() == R.id.main_all_btn) {
                Intent settingsIntent = new Intent(MainActivity.this, UserActivity.class);
                startActivity(settingsIntent);
            }
            return true;
    }

}

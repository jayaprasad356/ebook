package com.example.ebook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.ebook.helper.Constant;
import com.example.ebook.helper.Session;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class HomeActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {

    FrameLayout fragment_container;
    BottomNavigationView bottomNavigationView;
    homeFragment homeFragment = new homeFragment();
    MybookFragment mybookFragment = new MybookFragment();
    MywatchlistFragment mywatchlistFragment = new MywatchlistFragment();
    Activity activity ;
    Session session ;
    public static FragmentManager fm = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        Activity activity = HomeActivity.this;
        session = new Session(activity);

        fm = getSupportFragmentManager();










        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(this);

        fm.beginTransaction().replace(R.id.fragment_container, new homeFragment()).commit();
        bottomNavigationView.setSelectedItemId(R.id.nav_home);




    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {


        switch (item.getItemId()){
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,homeFragment).commit();
                return true;
            case R.id.nav_book:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,mybookFragment).commit();
                return true;
            case R.id.nav_watchlist:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,mywatchlistFragment).commit();
                return true;
        }
        return false;
    }
}
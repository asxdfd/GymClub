package com.example.lenovo.gymclub;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public static BmobUser user = new BmobUser();
    private int selectID = 0;
    private static boolean isExit = false;
    private static int fragment = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.content_main,
                    new DashboardFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }
        Bmob.initialize(this, "163858a36e4095d164e352750e1244c7");
    }

    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isExit = false;
        }
    };

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (fragment > 0) {
                while(fragment > 0) {
                    super.onBackPressed();
                    fragment--;
                }
                NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
                navigationView.getMenu().getItem(selectID).setChecked(true);
            } else if (exit()) {
                super.onBackPressed();
                System.exit(0);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("SetTextI18n")
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
            selectID = 0;

            getSupportFragmentManager().beginTransaction().replace(R.id.content_main,
                    new DashboardFragment()).commit();
        } else if (id == R.id.nav_schedule) {
            selectID = 1;
            getSupportFragmentManager().beginTransaction().replace(R.id.content_main,
                    new ScheduleFragment()).commit();
        } else if (id == R.id.nav_sports) {
            selectID = 2;
            getSupportFragmentManager().beginTransaction().replace(R.id.content_main,
                    new SportsFragment()).commit();
        } else if (id == R.id.nav_coach) {
            selectID = 3;
            getSupportFragmentManager().beginTransaction().replace(R.id.content_main,
                    new CoachesFragment()).commit();
        } else if (id == R.id.nav_apply) {
            fragment++;
            NavigationView navigationView = findViewById(R.id.nav_view);
            navigationView.getMenu().getItem(selectID).setChecked(false);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.content_main, new ApplyFragment());
            transaction.addToBackStack(null);
            transaction.commit();
        } else if (id == R.id.nav_signup) {
            fragment++;
            NavigationView navigationView = findViewById(R.id.nav_view);
            navigationView.getMenu().getItem(selectID).setChecked(false);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.content_main, new SignUpFragment());
            transaction.addToBackStack(null);
            transaction.commit();
        } else if (id == R.id.nav_login) {
            fragment++;
            NavigationView navigationView = findViewById(R.id.nav_view);
            navigationView.getMenu().getItem(selectID).setChecked(false);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.content_main, new LogInFragment());
            transaction.addToBackStack(null);
            transaction.commit();
        } else if (id == R.id.nav_logout) {
            NavigationView navigationView = findViewById(R.id.nav_view);
            Menu communicateMenu = navigationView.getMenu().getItem(5).getSubMenu();
            communicateMenu.getItem(1).setVisible(true);
            communicateMenu.getItem(2).setVisible(true);
            communicateMenu.getItem(3).setVisible(false);
            Toast.makeText(getApplicationContext(), "Log out successfully",
                    Toast.LENGTH_SHORT).show();
            TextView textView = (TextView) findViewById(R.id.info_username);
            textView.setText("Please login first");
            user.setUsername(null);
            user.setPassword(null);
            BmobUser.logOut();
        } else if (id == R.id.nav_video) {
            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            Intent intent = new Intent(this, VideoActivity.class);
            startActivity(intent);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private boolean exit() {
        if (!isExit) {
            isExit = true;
            Toast.makeText(getApplicationContext(), "Click again to exit",
                    Toast.LENGTH_SHORT).show();
            // 利用handler延迟发送更改状态信息
            mHandler.sendEmptyMessageDelayed(0, 2000);
            return false;
        } else {
            return true;
        }
    }

    public static BmobUser getUser() {
        return user;
    }

    public static int getFragment() {
        return fragment;
    }

}

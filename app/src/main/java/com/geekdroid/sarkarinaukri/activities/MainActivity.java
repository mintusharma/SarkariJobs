package com.geekdroid.sarkarinaukri.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.geekdroid.bottom_navigation.BubbleBottomNavigationLinearView;
import com.geekdroid.bottom_navigation.listener.BubbleBottomNavigationChangeListener;
import com.geekdroid.sarkarinaukri.R;
import com.geekdroid.sarkarinaukri.Utilis.Utilities;
import com.geekdroid.sarkarinaukri.Utilis.ViewDialog;
import com.geekdroid.sarkarinaukri.fragment.AdmitCardFragment;
import com.geekdroid.sarkarinaukri.fragment.HomeFragment;
import com.geekdroid.sarkarinaukri.fragment.LatestJobFragment;
import com.geekdroid.sarkarinaukri.fragment.AboutSarkariJob;

public class MainActivity extends AppCompatActivity {

    private Fragment fragment;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {
        toolbar=findViewById(R.id.toolbarHome);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Sarkari Naukri");

        BubbleBottomNavigationLinearView bLv = findViewById(R.id.bottom_navigation_view_linear);
        fragment = new HomeFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();

        bLv.setNavigationChangeListener(new BubbleBottomNavigationChangeListener() {
            @Override
            public void onNavigationChanged(View view, int position) {
                switch(position){
                    case 0:
                        fragment=new HomeFragment();
                        break;
                    case 1:
                        fragment=new LatestJobFragment();
                        break;
                    case 2:
                        fragment=new AdmitCardFragment();
                        break;
                    case 3:
                        fragment=new AboutSarkariJob();
                        break;

                }
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_container,
                        fragment).commit();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_home) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }
        if (id == R.id.action_more) {
            new Utilities().moreAppsUrl(this);
        }
      /*  if (id == R.id.action_about) {
            startActivity(new Intent(getApplicationContext(), AboutActivity.class));
        }*/
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        ViewDialog dialog=new ViewDialog();
        dialog.showDialog(this);
    }
}

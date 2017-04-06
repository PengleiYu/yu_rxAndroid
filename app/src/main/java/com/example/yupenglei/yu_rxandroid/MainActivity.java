package com.example.yupenglei.yu_rxandroid;

import android.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;

import com.example.yupenglei.yu_rxandroid.fragment.ui.item4.DistinctFragment;
import com.example.yupenglei.yu_rxandroid.fragment.ui.item4.FilterFragment;
import com.example.yupenglei.yu_rxandroid.fragment.ui.FirstFragment;
import com.example.yupenglei.yu_rxandroid.fragment.NavigationDrawerFragment;
import com.example.yupenglei.yu_rxandroid.fragment.ui.SecondFragment;
import com.example.yupenglei.yu_rxandroid.fragment.ui.ThreeFragment;
import com.example.yupenglei.yu_rxandroid.fragment.ui.item4.TakeFragment;

import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements NavigationDrawerCallbacks {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R
                .string.app_name, R.string.app_name);
        toggle.syncState();
        drawerLayout.setDrawerListener(toggle);

        NavigationDrawerFragment fragment_drawer = (NavigationDrawerFragment) getFragmentManager
                ().findFragmentById(R.id.fragment_drawer);
        fragment_drawer.setNavigationDrawerCallbacks(this);
        onNavigationDrawerItemSelected(0);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        Log.e(">>>", String.format("click item %d", position));
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new FirstFragment();
                break;
            case 1:
                fragment = new SecondFragment();
                break;
            case 2:
                fragment = new ThreeFragment();
                break;
            case 3:
                fragment = new FilterFragment();
                break;
            case 4:
                fragment = new TakeFragment();
                break;
            case 5:
                fragment = new DistinctFragment();
                break;
        }
        if (fragment != null) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.frameLayout_container, fragment)
                    .commit();
        }
    }
}

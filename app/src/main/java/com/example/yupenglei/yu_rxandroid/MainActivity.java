package com.example.yupenglei.yu_rxandroid;

import android.app.Fragment;
import android.content.Intent;
import android.os.StrictMode;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.yupenglei.yu_rxandroid.fragment.ui.item4.DistinctFragment;
import com.example.yupenglei.yu_rxandroid.fragment.ui.item4.FilterFragment;
import com.example.yupenglei.yu_rxandroid.fragment.ui.item1.FirstFragment;
import com.example.yupenglei.yu_rxandroid.fragment.NavigationDrawerFragment;
import com.example.yupenglei.yu_rxandroid.fragment.ui.item2.SecondFragment;
import com.example.yupenglei.yu_rxandroid.fragment.ui.item3.ThreeFragment;
import com.example.yupenglei.yu_rxandroid.fragment.ui.item4.TakeFragment;
import com.example.yupenglei.yu_rxandroid.fragment.ui.item5.GroupByFragment;
import com.example.yupenglei.yu_rxandroid.fragment.ui.item5.MapFragment;
import com.example.yupenglei.yu_rxandroid.fragment.ui.item5.ScanFragment;
import com.example.yupenglei.yu_rxandroid.fragment.ui.item6.AndThenWhenFragment;
import com.example.yupenglei.yu_rxandroid.fragment.ui.item6.CombineLatest;
import com.example.yupenglei.yu_rxandroid.fragment.ui.item6.JoinFragment;
import com.example.yupenglei.yu_rxandroid.fragment.ui.item6.MergeFragment;
import com.example.yupenglei.yu_rxandroid.fragment.ui.item6.ZipFragment;
import com.example.yupenglei.yu_rxandroid.fragment.ui.item7.LongTaskFragment;
import com.example.yupenglei.yu_rxandroid.fragment.ui.item7.NetWorkFragment;
import com.example.yupenglei.yu_rxandroid.fragment.ui.item7.SharedPreferenceFragment;

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

        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(
                    new StrictMode.ThreadPolicy.Builder()
                            .detectAll()
                            .penaltyLog()
                            .build());
            StrictMode.setVmPolicy(
                    new StrictMode.VmPolicy.Builder()
                            .detectAll()
                            .penaltyLog()
                            .build());
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private final String TAG = getClass().getSimpleName();

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        startActivity(new Intent(this, TestActivity.class));
        return super.onOptionsItemSelected(item);
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
            case 6:
                fragment = new MapFragment();
                break;
            case 7:
                fragment = new ScanFragment();
                break;
            case 8:
                fragment = new GroupByFragment();
                break;
            case 9:
                fragment = new MergeFragment();
                break;
            case 10:
                fragment = new ZipFragment();
                break;
            case 11:
                fragment = new JoinFragment();
                break;
            case 12:
                fragment = new CombineLatest();
                break;
            case 13:
                fragment = new AndThenWhenFragment();
                break;
            case 14:
                fragment = new SharedPreferenceFragment();
                break;
            case 15:
                fragment = new LongTaskFragment();
                break;
            case 16:
                fragment = new NetWorkFragment();
                break;
        }
        if (fragment != null) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.frameLayout_container, fragment)
                    .commit();
        }
    }
}

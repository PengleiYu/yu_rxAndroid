package com.example.yupenglei.yu_rxandroid;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by yupenglei on 17/3/31.
 */

public class NavigationDrawerFragment extends Fragment implements AdapterView.OnItemClickListener {
    private NavigationDrawerCallbacks mNavigationDrawerCallbacks;
    private DrawerLayout mDrawerLayout;

    public void setNavigationDrawerCallbacks(NavigationDrawerCallbacks callbacks) {
        mNavigationDrawerCallbacks = callbacks;
    }

    public void setUp(DrawerLayout drawerLayout, Toolbar toolbar) {
        mDrawerLayout = drawerLayout;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle
            savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
        ListView listView = (ListView) view.findViewById(R.id.list_view_fragment_drawer);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout
                .simple_list_item_1, new ArrayList<String>());
        adapter.add("item 1");
        adapter.add("item 2");
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(this);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        mDrawerLayout.closeDrawer(Gravity.START);
        DrawerLayout drawerLayout= (DrawerLayout) getView().getParent();
        drawerLayout.closeDrawer(Gravity.START);
        if (mNavigationDrawerCallbacks != null) {
            mNavigationDrawerCallbacks.onNavigationDrawerItemSelected(position);
        }
    }
}

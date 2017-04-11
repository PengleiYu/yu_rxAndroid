package com.example.yupenglei.yu_rxandroid.fragment.ui.item8;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.yupenglei.yu_rxandroid.R;
import com.example.yupenglei.yu_rxandroid.fragment.ui.item8.api.ApiManager;
import com.example.yupenglei.yu_rxandroid.fragment.ui.item8.models.User;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;

/**
 * Created by yupenglei on 17/4/10.
 */

public class StackOverflowFragment extends Fragment implements SwipeRefreshLayout
        .OnRefreshListener {
    @BindView(R.id.recycler_fragment_example)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipe_container_fragment_example)
    SwipeRefreshLayout mSwipeRefreshLayout;
    private ApiManager mManager;
    private StackOverflowAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle
            savedInstanceState) {
        return inflater.inflate(R.layout.fragment_example, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new StackOverflowAdapter(new ArrayList<User>(), getActivity());
        mRecyclerView.setAdapter(mAdapter);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mManager = new ApiManager();

        mSwipeRefreshLayout.setRefreshing(true);
        onRefresh();
    }

    @Override
    public void onRefresh() {

        mManager.getMostPopularUsers(10)
                .subscribe(new Subscriber<List<User>>() {
                    @Override
                    public void onCompleted() {
                        Log.e("stackOverflow", "onCompleted");
                        mSwipeRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("stackOverflow", "onError");
                        mSwipeRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onNext(List<User> users) {
                        mAdapter.setUsers(users);
                    }
                });

    }
}

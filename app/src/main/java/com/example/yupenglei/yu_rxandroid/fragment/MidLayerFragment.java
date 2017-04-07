package com.example.yupenglei.yu_rxandroid.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.yupenglei.yu_rxandroid.R;
import com.example.yupenglei.yu_rxandroid.app.AppInfo;
import com.example.yupenglei.yu_rxandroid.app.ApplicationAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by yupenglei on 17/4/5.
 */

public abstract class MidLayerFragment extends BaseFragment implements SwipeRefreshLayout
        .OnRefreshListener {
    @BindView(R.id.recycler_fragment_example)
    protected RecyclerView mRecyclerView;

    @BindView(R.id.swipe_container_fragment_example)
    protected SwipeRefreshLayout mSwipeRefreshLayout;
    private Unbinder mUnbinder;
    protected ApplicationAdapter mAdapter;
    private Subscription mSubscribe;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        mUnbinder = ButterKnife.bind(this, view);


        mAdapter = new ApplicationAdapter(new ArrayList<AppInfo>());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setRefreshing(true);
        onRefresh();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
        if (mSubscribe != null && !mSubscribe.isUnsubscribed()) {
            mSubscribe.unsubscribe();
        }
    }

    protected void doCompelet(String fragmentNumber) {
        Toast.makeText(getActivity(), "Here is the " + fragmentNumber + " list", Toast
                .LENGTH_SHORT).show();
        mSwipeRefreshLayout.setRefreshing(false);
    }

    protected void doError() {
        Toast.makeText(getActivity(), "Something is wrong", Toast.LENGTH_SHORT).show();
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onRefresh() {
        mAdapter.clear();
        mSubscribe = getObservable().observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<AppInfo>() {
                    @Override
                    public void onCompleted() {
                        doCompelet(getName());
                    }

                    @Override
                    public void onError(Throwable e) {
                        doError();
                    }

                    @Override
                    public void onNext(AppInfo info) {
                        mAdapter.addAppInfo(info);
                        mRecyclerView.smoothScrollToPosition(mAdapter.getItemCount() - 1);
                    }
                });
    }

    private String getName() {
        String name = getClass().getSimpleName();
        return name.substring(0, name.lastIndexOf("Fragment"));
    }

    protected abstract Observable<AppInfo> getObservable();

}

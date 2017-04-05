package com.example.yupenglei.yu_rxandroid.fragment.ui;

import android.widget.Toast;

import com.example.yupenglei.yu_rxandroid.app.AppInfo;
import com.example.yupenglei.yu_rxandroid.app.ApplicationList;
import com.example.yupenglei.yu_rxandroid.fragment.MidLayerFragment;

import java.util.List;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by yupenglei on 17/4/5.
 */

public class SecondFragment extends MidLayerFragment {
    @Override
    public void onRefresh() {
        loadList(ApplicationList.getInstance().getList());

    }

    private void loadList(List<AppInfo> appInfos) {
        Observable.from(appInfos)
                .subscribe(new Subscriber<AppInfo>() {
                    @Override
                    public void onCompleted() {
                        mSwipeRefreshLayout.setRefreshing(false);
                        Toast.makeText(getActivity(), "Here is the second list", Toast.LENGTH_SHORT)
                                .show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(getActivity(), "Something is wrong", Toast.LENGTH_SHORT)
                                .show();
                        mSwipeRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onNext(AppInfo appInfo) {
//                        mAdapter.addAppInfo(appInfo, mAdapter.getItemCount());
                        mAdapter.addAppInfo(appInfo);
                    }
                });
    }
}

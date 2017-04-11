package com.example.yupenglei.yu_rxandroid.fragment.ui.item8.api;


import com.example.yupenglei.yu_rxandroid.fragment.ui.item8.models.User;
import com.example.yupenglei.yu_rxandroid.fragment.ui.item8.models.UsersResponse;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by yupenglei on 17/4/10.
 */

public class ApiManager {
    private StackOverflowApi mStackOverflowApi;

    public ApiManager() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.stackexchange.com")
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mStackOverflowApi = retrofit.create(StackOverflowApi.class);
    }

    public Observable<List<User>> getMostPopularUsers(int howMany) {
        return mStackOverflowApi.getMostPopularUser(howMany)
                .map(new Func1<UsersResponse, List<User>>() {
                    @Override
                    public List<User> call(UsersResponse usersResponse) {
                        return usersResponse.getUsers();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}

package com.example.yupenglei.yu_rxandroid.fragment.ui.item8.api;

import com.example.yupenglei.yu_rxandroid.fragment.ui.item8.models.UsersResponse;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by yupenglei on 17/4/10.
 */

public interface StackOverflowApi {

    @GET("/2.2/users?order=desc&sort=reputation&site=stackoverflow")
    Observable<UsersResponse> getMostPopularUser(@Query("pagesize") int howMany);

    @GET("/2.2/users?order=desc&pagesize=10&sort=reputation&site=stackoverflow")
    Observable<UsersResponse> getMostPopularUser();
}

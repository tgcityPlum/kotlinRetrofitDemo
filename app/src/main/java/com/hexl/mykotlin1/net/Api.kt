package com.hexl.mykotlin1.net

import com.hexl.mykotlin1.data.FollowersEntity
import com.hexl.mykotlin1.data.SubscriptionsEntity
import com.hexl.mykotlin1.data.UserInfoEntity
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface Api {

    @GET("users/{path}")
    fun getUserInfo(@Path("path") string: String): Call<UserInfoEntity>

    @GET("users/kjalnes/subscriptions")
    fun getSubscrptions(): Call<List<SubscriptionsEntity>>

    @GET("users/HexlDL/followers")
    fun getFollowers():Observable<List<FollowersEntity>>
}
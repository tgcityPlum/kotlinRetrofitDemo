package com.hexl.mykotlin1

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.hexl.mykotlin1.data.UserInfoEntity
import com.hexl.mykotlin1.net.Api
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    val baseUrl: String = "https://api.github.com/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //设置布局
        setContentView(R.layout.activity_main)

        btn_net.setOnClickListener {
            getInterNet()
        }
    }

    fun getInterNet() {
        val retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        val api = retrofit.create(Api::class.java)

        //使用RxJava操作符实现网络请求
        api.getFollowers()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribe({ result ->
                    println(result.toString())
                }, { error ->
                    println(error.message)
                }, {
                    println("complete")
                })
        //请求网络，通过返回的call对象实现
//        api.getSubscrptions()
//                .enqueue(object : Callback<List<SubscriptionsEntity>> {
//                    override fun onFailure(call: Call<List<SubscriptionsEntity>>?, t: Throwable?) {
//                        println(t!!.localizedMessage)
//                    }
//
//                    override fun onResponse(call: Call<List<SubscriptionsEntity>>?, response: Response<List<SubscriptionsEntity>>?) {
//                        if (response!!.isSuccessful) {
////                            println(response.body().toString())
//                            val list = response.body()
//                            for (entity: SubscriptionsEntity in list!!) {
//                                val owner = entity.owner
//                            }
//                        }
//                    }
//                })
        api.getUserInfo("test")
                .enqueue(object : Callback<UserInfoEntity> {
                    override fun onFailure(call: Call<UserInfoEntity>?, t: Throwable?) {
                        println(t?.localizedMessage)
                    }

                    override fun onResponse(call: Call<UserInfoEntity>?, response: Response<UserInfoEntity>?) {
                        println(response?.message())
                        val userInfoEntity = response?.body()
                        println(userInfoEntity.toString())
                        println("用户名：${userInfoEntity?.login}  id: ${userInfoEntity?.id}  nodeId：${userInfoEntity?.node_id}")
                    }
                })
    }
}

package com.mao.rxjava3application

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mao.rxjava3application.api.Api
import com.mao.rxjava3application.model.ResponseData
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.core.SingleObserver
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.functions.Function
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private var disposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //创建 retrofit 对象

        val retrofit = Retrofit.Builder()
                .baseUrl("https://api.github.com")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create()).build()

        val api = retrofit.create(Api::class.java)

        api.getRepo("maoqitian")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : SingleObserver<List<ResponseData>> {
                    override fun onSuccess(t: List<ResponseData>) {
                        textView.text = "请求成功$t[0].name"
                    }

                    override fun onSubscribe(d: Disposable) {
                        textView.text = "正在请求"
                        disposable = d
                        //this@MainActivity.disposable = disposable
                    }

                    override fun onError(e: Throwable) {
                        textView.text = e.message ?: e.javaClass.name
                    }

                })


        //创建被观察者对象
        val single = Single.just(1)
        //使用操作符 map 数据转换
        val singleStr = single.map(object : Function<Int, String> {
            override fun apply(t: Int): String {
                return t.toString()
            }

        })
        //订阅被观察者
        singleStr.subscribe(object :SingleObserver<String>{
            override fun onSubscribe(d: Disposable?) {
            }

            override fun onSuccess(t: String?) {
            }

            override fun onError(e: Throwable?) {
            }

        })

        //按照时间间隔 1秒 发送一次
        Observable.interval(0, 1, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<Long?> {
                override fun onComplete() {
                }

                override fun onSubscribe(d: Disposable?) {
                }

                override fun onNext(t: Long?) {
                    textView.text = t.toString()
                }

                override fun onError(e: Throwable?) {
                }
            })
    }

    override fun onDestroy() {
        disposable?.dispose()
        super.onDestroy()
    }
}
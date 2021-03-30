package com.mao.rxjava3application

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    //private var disposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //创建 retrofit 对象

        /*val retrofit = Retrofit.Builder()
                .baseUrl("https://api.github.com")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create()).build()

        val api = retrofit.create(Api::class.java)

        api.getRepo("maoqitian")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object :SingleObserver<List<ResponseData>>{
                    override fun onSuccess(t: List<ResponseData>) {
                        println(t[0].name)
                    }

                    override fun onSubscribe(d: Disposable) {
                        textView.text = "正在请求"
                        disposable = d
                        //this@MainActivity.disposable = disposable
                    }

                    override fun onError(e: Throwable) {
                        textView.text = e.message ?: e.javaClass.name
                    }

                })*/



        Single.just(1)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

        Observable.interval(0, 1, TimeUnit.SECONDS)
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

   /* override fun onDestroy() {
        disposable?.dispose()
        super.onDestroy()
    }*/
}
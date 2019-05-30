package com.revolution.myexamination

import android.app.Application
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.revolution.myexamination.retrofit.RetrofitRequestInterface
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by Rajan.Bhavsar on 2019-05-30.
 * This is the Application level class where we are initialized the Variables which we have ti used threw out the application for single time.
 */

public class MyApplication : Application() {


    private var requestInterface: RetrofitRequestInterface? = null


    override fun onCreate() {
        super.onCreate()
        instanceApp = this
    }

    companion object {
        private var instanceApp: MyApplication? = null


        /**
         * This is the function to fetch singleton class instance of application.
         */
        fun getInstanceMyApplication(): MyApplication {
            if (instanceApp == null) {
                instanceApp = MyApplication()
                return instanceApp as MyApplication
            } else {
                return instanceApp as MyApplication
            }
        }
    }


    /**
     * This is the function to fetch singleton class instance of RetrofitRequestInterface.
     */
    public fun getrequestInterface(): RetrofitRequestInterface {
        if (requestInterface == null) {
            requestInterface = getRestAdapter().create(RetrofitRequestInterface::class.java)
            return requestInterface as RetrofitRequestInterface
        } else {
            return requestInterface as RetrofitRequestInterface
        }
    }

    /**
     * This is the Function where we are going to initialized the Retrofit For Making An Api call and displaying the Api call Url and Response in Logcat
     * we can also disable same from here.
     */
    private fun getRestAdapter(): Retrofit {
        val client = OkHttpClient.Builder()
        client.readTimeout(3, TimeUnit.MINUTES)
        client.connectTimeout(3, TimeUnit.MINUTES)
        client.retryOnConnectionFailure(true)

        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        client.networkInterceptors().add(loggingInterceptor)
        client.addInterceptor(loggingInterceptor)

        val clients = client.build()

        val gson = GsonBuilder()
            .setLenient()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create()

        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(clients)
            .build()
    }
}
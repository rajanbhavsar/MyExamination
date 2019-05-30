package com.revolution.myexamination.retrofit

import com.revolution.myexamination.response.FeedResponseParser
import io.reactivex.Observable
import retrofit2.http.GET

/**
 * Created by Rajan.Bhavsar on 2019-05-30.
 * This is an Interface to declare multiple api calls which we will implement in our required ui classes.
 */

interface RetrofitRequestInterface {


    /**
     * This abstract method is used to fetch the Feed response.
     */
    @GET("s/2iodh4vg0eortkl/facts.json")
    fun getAllFeeds(): Observable<FeedResponseParser>
}
package com.revolution.myexamination

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.View
import com.revolution.myexamination.adapter.FeedListAdapter
import com.revolution.myexamination.response.FeedResponseParser
import com.revolution.myexamination.response.model.Feed
import com.revolution.myexamination.retrofit.RetrofitRequestInterface
import com.revolution.myexamination.utils.Utils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Created by Rajan.Bhavsar on 2019-05-30.
 * This is the class where we going to display the RecycleView with Feed List.
 */

class MainActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener {


    var mCompositeDisposable: CompositeDisposable? = null
    private var anInterface: RetrofitRequestInterface? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
    }

    /**
     * This method is used for initialized the views and attached the listeners
     */
    private fun initViews() {
        mRecycleViewFeed.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(this)
        mRecycleViewFeed.layoutManager = layoutManager
        swipeToRefresh.setOnRefreshListener(this)

        fetchFeedList(this)
    }


    /**
     * This is the swipe to refresh callback function which we implemented inside this class
     */
    override fun onRefresh() {
        fetchFeedList(this)
    }


    /**
     * This is the method where we are checking the internet connectivity and start the Api Call.
     */
    fun fetchFeedList(context: Context) {
        if (Utils.isNetworkConnected(context)) {
            mCompositeDisposable = CompositeDisposable()
            mTextViewNoRecords.visibility = View.GONE
            mRecycleViewFeed.visibility = View.VISIBLE
            anInterface = MyApplication.getInstanceMyApplication().getrequestInterface()
            callFeedListApi()
        } else {
            if (swipeToRefresh.isRefreshing) {
                swipeToRefresh.isRefreshing = false
            }
            mTextViewNoRecords.visibility = View.VISIBLE
            mRecycleViewFeed.visibility = View.GONE
            mTextViewNoRecords.text = resources.getString(R.string.label_pull_to_refresh)
            Utils.showSnackBar(mRecycleViewFeed, resources.getString(R.string.lab_no_internet_connection))
        }
    }


    /**
     * This method is used to attached the list with Recycle View.
     */
    private fun displayData(list_rows: ArrayList<Feed>) {
        if (swipeToRefresh.isRefreshing) {
            swipeToRefresh.isRefreshing = false
        }

        if (list_rows != null && list_rows.size > 0) {
            mTextViewNoRecords.visibility = View.GONE
            mRecycleViewFeed.visibility = View.VISIBLE
            val adapter = FeedListAdapter(list_rows, this)
            mRecycleViewFeed.adapter = adapter
        } else {
            mTextViewNoRecords.visibility = View.VISIBLE
            mRecycleViewFeed.visibility = View.GONE
            mTextViewNoRecords.text = resources.getString(R.string.label_no_records_found)
        }
    }


    /**
     * This is an actual method of Api Call with Retrofit.
     */
    private fun callFeedListApi() {
        mprogressbar.visibility = View.VISIBLE
        mCompositeDisposable?.add(
            anInterface?.getAllFeeds()
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe(({ this.handlesuccessresponse(it) }), ({ this.handleError(it) }))!!
        )
    }

    /**
     * This is the Function where we are handle the error response if occurs for api call
     */
    private fun handleError(throwable: Throwable) {
        if (swipeToRefresh.isRefreshing) {
            swipeToRefresh.isRefreshing = false
        }
        mprogressbar.visibility = View.GONE
        Utils.handleErrorMessage(this, throwable)
    }


    /**
     * This method is used to handle the response came from the api call
     */
    private fun handlesuccessresponse(response: FeedResponseParser) {
        if (swipeToRefresh.isRefreshing) {
            swipeToRefresh.isRefreshing = false
        }
        mprogressbar.visibility = View.GONE

        if (response != null) {
            if (!TextUtils.isEmpty(response.name))
                title = response.name
            else
                title = resources.getString(R.string.app_name)

            if (response.list_rows != null && response.list_rows!!.size > 0) {
                displayData(response.list_rows)
                mTextViewNoRecords.visibility = View.GONE
                mRecycleViewFeed.visibility = View.VISIBLE
            } else {
                mTextViewNoRecords.visibility = View.VISIBLE
                mRecycleViewFeed.visibility = View.GONE
                mTextViewNoRecords.text = resources.getString(R.string.label_no_records_found)
            }
        }
    }


    /**
     * This is overrided method where we are going to free up the memory of initialized variables.
     */
    public override fun onDestroy() {
        super.onDestroy()
        if (mCompositeDisposable != null) {
            mCompositeDisposable?.dispose()
        }
    }
}

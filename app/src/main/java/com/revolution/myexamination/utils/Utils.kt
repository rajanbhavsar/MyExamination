package com.revolution.myexamination.utils

import android.content.Context
import android.net.ConnectivityManager
import android.support.design.widget.Snackbar
import android.view.View
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException
import com.revolution.myexamination.R

/**
 * Created by Rajan.Bhavsar on 2019-05-30.
 * This is the Utility class to define some common used methods in single static class
 * Here companion keyword is used to define static keyword in kotlin Language.
 */

class Utils {

    companion object {


        /**
         * This method is used to check the Internet Connectivity.
         */
        fun isNetworkConnected(context: Context): Boolean {
            val connMgr = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = connMgr.activeNetworkInfo
            return networkInfo != null && networkInfo.isConnected

        }

        /**
         * This method is used to display the Message inside the Screen.
         */
        fun showSnackBar(view: View, msg: String) {
            val snackbar = Snackbar.make(view, msg, Snackbar.LENGTH_SHORT)
            snackbar.show()
        }


        /**
         * This method is used to display message based on the throwable object we received when ever have an error.
         */
        fun handleErrorMessage(context: Context, throwable: Throwable): String {
            if (throwable is HttpException) {
                if (isNetworkConnected(context)) {
                    val code = throwable.code()
                    when (code) {
                        400 -> return context.getString(R.string.lab_error_api)
                        else -> return context.getString(R.string.lab_error_api)
                    }
                } else {
                    return context.getString(R.string.lab_no_internet_connection)
                }
            } else {
                return context.getString(R.string.lab_error_api)
            }
        }
    }
}
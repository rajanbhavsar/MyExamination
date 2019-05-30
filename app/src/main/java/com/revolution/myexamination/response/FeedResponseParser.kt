package com.revolution.myexamination.response

import com.google.gson.annotations.SerializedName
import com.revolution.myexamination.response.model.Feed

/**
 * Created by Rajan.Bhavsar on 2019-05-30.
 * This is the Response parser Data class to Parse Our JSon Response To Custom Model Class.
 */

data class FeedResponseParser(
    @SerializedName("title") var name: String? = null,
    @SerializedName("rows") var list_rows: ArrayList<Feed>
)
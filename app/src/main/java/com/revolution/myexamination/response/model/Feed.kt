package com.revolution.myexamination.response.model

import com.google.gson.annotations.SerializedName

/**
 * Created by Rajan.Bhavsar on 2019-05-30.
 * This is the Data class same as Pojo Class to Parse Row array data in our model class
 */

data class Feed(
    @SerializedName("title") var title: String? = null,
    @SerializedName("description") var description: String? = null,
    @SerializedName("imageHref") var imageHref: String? = null
)


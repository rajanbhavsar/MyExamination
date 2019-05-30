package com.revolution.myexamination.adapter

import android.content.Context
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.revolution.myexamination.R
import com.revolution.myexamination.response.model.Feed

/**
 * Created by Rajan.Bhavsar on 2019-05-30.
 * This is An Adapter Class to bind Response List Data With RecycleView.
 */

class FeedListAdapter(val dataset: List<Feed>, private val context: Context) :
    RecyclerView.Adapter<FeedListAdapter.ViewHolder>() {


    override fun onCreateViewHolder(viewgroup: ViewGroup, p1: Int): ViewHolder {
        val v = LayoutInflater.from(viewgroup.context).inflate(R.layout.layout_feed_item, viewgroup, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        if (!TextUtils.isEmpty(dataset[position].title)) {
            viewHolder.mTextViewTitleFeed.visibility = View.VISIBLE
            viewHolder.mTextViewTitleFeed.text = dataset[position].title
        } else {
            viewHolder.mTextViewTitleFeed.visibility = View.GONE
        }

        if (!TextUtils.isEmpty(dataset[position].description)) {
            viewHolder.mTextViewFeedDesc.visibility = View.VISIBLE
            viewHolder.mTextViewFeedDesc.text = dataset[position].description
        } else {
            viewHolder.mTextViewFeedDesc.visibility = View.GONE
        }

        if (!TextUtils.isEmpty(dataset[position].imageHref)) {
            viewHolder.mImageViewUser.visibility = View.VISIBLE
            context.let {
                Glide.with(it)
                    .load(dataset[position].imageHref)
                    .into(viewHolder.mImageViewUser)
            }
        } else {
            viewHolder.mImageViewUser.visibility = View.GONE
        }

        if (TextUtils.isEmpty(dataset[position].title) && TextUtils.isEmpty(dataset[position].description) && TextUtils.isEmpty(
                dataset[position].imageHref
            )
        ) {
            viewHolder.mCardView.visibility = View.GONE
        } else {
            viewHolder.mCardView.visibility = View.VISIBLE
        }
    }


    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        internal val mTextViewTitleFeed: TextView
        internal val mTextViewFeedDesc: TextView
        internal val mImageViewUser: ImageView
        internal val mCardView: CardView

        init {
            mTextViewTitleFeed = v.findViewById(R.id.mTextViewTitleFeed)
            mTextViewFeedDesc = v.findViewById(R.id.mTextViewFeedDesc)
            mImageViewUser = v.findViewById(R.id.mImageViewUser)
            mCardView = v.findViewById(R.id.mCardView)
        }
    }


}
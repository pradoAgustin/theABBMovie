package com.agustin.abbmovie

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_fullscreen.*

abstract class AbstractRecyclerActivity : BaseActivity() {


    fun setRecyclerViewScrollListener() {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val linearLayoutManager = recyclerView!!.layoutManager as LinearLayoutManager
                val totalItemCount = linearLayoutManager.itemCount
                val lastItemPosition = linearLayoutManager.findLastCompletelyVisibleItemPosition()
                val lastVisibleItemPosition = lastItemPosition + 1
                if (totalItemCount == lastVisibleItemPosition) {
                    onLastItemShown(lastVisibleItemPosition)
                }
            }
        })
    }

    open fun onLastItemShown(lastVisibleItemPosition: Int) {
    }

}
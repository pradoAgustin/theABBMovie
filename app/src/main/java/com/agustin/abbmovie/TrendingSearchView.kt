package com.agustin.abbmovie

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater

class TrendingSearchView : CustomSearchView {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, attributeSetId: Int) : super(context, attrs, attributeSetId)

    init {
        LayoutInflater.from(context)
                .inflate(R.layout.trending_search_view, this, true)
    }

    override fun cleanTextFromEditText() {
        // do nothing
    }

    override fun getTextFromEditText(): String {
        //do nothing
        return ""
    }

}
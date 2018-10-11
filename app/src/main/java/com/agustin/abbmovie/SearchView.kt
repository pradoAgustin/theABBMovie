package com.agustin.abbmovie

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import kotlinx.android.synthetic.main.search_view.view.*

class SearchView : CustomSearchView {
    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, attributeSetId: Int) : super(context, attrs, attributeSetId)

    init {
        LayoutInflater.from(context)
                .inflate(R.layout.search_view, this, true)
    }

    override fun initializeSearchListener(listener :SearchListener) {
       super.initializeSearchListener(listener)
       setCancelActionListener(cancelIcon)
        setCancelActionListener(canceLabel)
        setSearchActionListener(goSearch)
        setSearchActionListener(searchIcon)
    }

    override fun cleanTextFromEditText() {
        editTextSearch.text.clear()
    }

    override fun getTextFromEditText(): String {
        return editTextSearch.text.toString()
    }
}
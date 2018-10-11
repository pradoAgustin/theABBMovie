package com.agustin.abbmovie

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.view.View

abstract class CustomSearchView : ConstraintLayout {
    private var searchListener: SearchListener? = null

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, attributeSetId: Int) : super(context, attrs, attributeSetId)


    open fun initializeSearchListener(listener: SearchListener) {
        searchListener = listener
    }

    fun setSearchActionListener(view: View) {
        view.setOnClickListener {
            val text = getTextFromEditText()
            searchListener?.also { it.onSearchRequested(text) }
        }
    }

    fun setCancelActionListener(view: View) {
        view.setOnClickListener {
            cleanTextFromEditText()
            searchListener?.onCancelRequested()
        }
    }

    abstract fun cleanTextFromEditText()

    abstract fun getTextFromEditText(): String
}
package com.agustin.abbmovie

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.view.LayoutInflater
import kotlinx.android.synthetic.main.search_view.view.*

class SearchView : ConstraintLayout {
private var searchListener : SearchListener? = null

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, attributeSetId: Int) : super(context, attrs, attributeSetId)

    init {
        LayoutInflater.from(context)
                .inflate(R.layout.search_view, this, true)
    }

    fun initializeSearchListener(listener :SearchListener) {
        searchListener = listener
        initializeListeners()

    }

    private fun initializeListeners() {
        goSearch.setOnClickListener {
            val text = getTextFromEditText()
            searchListener?.also{it.onSearchRequested(text)}
        }

        canceLabel.setOnClickListener {
            cleanTextFromEditText()
            searchListener?.onCancelRequested()
        }
    }

    private fun cleanTextFromEditText() {
        editTextSearch.text.clear()
    }

    private fun getTextFromEditText(): String {
        return editTextSearch.text.toString()
    }
}
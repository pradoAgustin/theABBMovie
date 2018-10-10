package com.agustin.abbmovie

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutResourceId())
        if (shouldHideAppBar()) {
            supportActionBar?.hide()
        }
        initializeCustomSearchViewIfRequired()
    }

    open fun initializeCustomSearchViewIfRequired() {
    }

    fun shouldHideAppBar(): Boolean {
        return false
    }

    abstract fun getLayoutResourceId(): Int
}
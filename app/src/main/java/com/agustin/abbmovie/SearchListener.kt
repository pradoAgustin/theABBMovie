package com.agustin.abbmovie

interface SearchListener {
   fun onSearchRequested(text : String)
   fun onCancelRequested()
}
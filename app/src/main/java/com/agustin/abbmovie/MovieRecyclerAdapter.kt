package com.agustin.abbmovie

import android.support.v7.widget.RecyclerView
import java.util.*

abstract class MovieRecyclerAdapter<T, K : MovieRecyclerViewHolder<T>>(list: List<T>?) : RecyclerView.Adapter<K>() {
    var adapterList: List<T> = list ?: Collections.emptyList()

    override fun getItemCount(): Int {
        return adapterList.size
    }

    override fun onBindViewHolder(holder: K, position: Int) {
        holder.bindItem(adapterList[position])
    }

    private fun onItemsInserted(updatedResults: List<T>?) {
        updatedResults?.also {
            it.forEach {
                adapterList += (it)
            }
            this.notifyItemInserted(adapterList.size)
        }

    }

    fun getItem(adapterPosition: Int): T {
        return adapterList[adapterPosition]
    }

    fun updateItems(list: List<T>?) {
        onItemsInserted(list)
    }

}
package com.delizarov.core.android.adapter

import android.support.v7.widget.RecyclerView
import com.delizarov.core.android.viewholder.ViewHolderBase

abstract class ListAdapter<T>(
    private val items: MutableList<T>
) : RecyclerView.Adapter<ViewHolderBase<T>>() {

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolderBase<T>, pos: Int) = holder.bind(items[pos])

    fun addItem(item: T) {

        items.add(item)
        notifyItemInserted(items.size - 1)
    }
}
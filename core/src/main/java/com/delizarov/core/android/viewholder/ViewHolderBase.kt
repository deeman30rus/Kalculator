package com.delizarov.core.android.viewholder

import android.content.Context
import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

abstract class ViewHolderBase<T>(
    ctx: Context,
    parent: ViewGroup,
    @LayoutRes resId: Int
) : RecyclerView.ViewHolder(LayoutInflater.from(ctx).inflate(resId, parent, false)) {

    abstract fun bind(item: T)
}
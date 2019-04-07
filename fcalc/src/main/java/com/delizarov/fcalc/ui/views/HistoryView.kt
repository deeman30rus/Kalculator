package com.delizarov.fcalc.ui.views

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.TextView
import com.delizarov.core.android.adapter.ListAdapter
import com.delizarov.core.android.viewholder.ViewHolderBase
import com.delizarov.domain.math.expression.Expression
import com.delizarov.fcalc.R


class HistoryView(ctx: Context, attrs: AttributeSet?, defStyleAttr: Int) :
        RecyclerView(ctx, attrs, defStyleAttr) {

    constructor(ctx: Context) : this(ctx, null)

    constructor(ctx: Context, attrs: AttributeSet?) : this(ctx, attrs, 0)

    init {

        layoutManager = LinearLayoutManager(ctx)

        adapter = HistoryAdapter(ctx, this)
    }

    fun addItem(expr: Expression) = (adapter as HistoryAdapter).addItem(expr)
}

class HistoryAdapter(
    private val ctx: Context,
    private val rv: RecyclerView
) : ListAdapter<Expression>(mutableListOf()) {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int) = ExpressionViewHolder(ctx, rv)
}

class ExpressionViewHolder(ctx: Context, parent: ViewGroup) :
    ViewHolderBase<Expression>(ctx, parent, R.layout.viewholder_history_item) {

    private val equationView = itemView.findViewById<TextView>(R.id.equation_view)

    override fun bind(item: Expression) {

        equationView.text = "${item.expr} = ${item.value}"
    }
}
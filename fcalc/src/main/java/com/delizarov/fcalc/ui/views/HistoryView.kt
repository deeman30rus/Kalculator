package com.delizarov.fcalc.ui.views

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.ViewGroup
import com.delizarov.core.android.adapter.ListAdapter
import com.delizarov.core.android.viewholder.ViewHolderBase
import com.delizarov.domain.math.expression.Expression
import com.delizarov.fcalc.R
import com.delizarov.views.com.delizarov.views.expression.ExpressionView


class HistoryView(ctx: Context, attrs: AttributeSet?, defStyleAttr: Int) :
    RecyclerView(ctx, attrs, defStyleAttr) {

    constructor(ctx: Context) : this(ctx, null)

    constructor(ctx: Context, attrs: AttributeSet?) : this(ctx, attrs, 0)

    init {

        layoutManager = object : LinearLayoutManager(ctx) {

            override fun canScrollVertically(): Boolean {
                return false
            }
        }

        with(layoutManager as LinearLayoutManager) {
            stackFromEnd = true
        }

        adapter = HistoryAdapter(ctx, this)
        adapter?.registerAdapterDataObserver(object : AdapterDataObserver() {

            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                super.onItemRangeInserted(positionStart, itemCount)

                scrollToPosition(adapter!!.itemCount - 1)
            }
        })


        val divider = DividerItemDecoration(context, (layoutManager as LinearLayoutManager).orientation)
        divider.setDrawable(ContextCompat.getDrawable(context, R.drawable.divider_history)!!)
        addItemDecoration(divider)
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

    private val equationView = itemView.findViewById<ExpressionView>(R.id.equation_view)

    override fun bind(item: Expression) {

        equationView.expression = item
    }
}


package com.delizarov.views.com.delizarov.views

import android.content.Context
import android.util.AttributeSet
import android.view.animation.Animation
import android.widget.LinearLayout
import android.widget.TextView
import com.delizarov.core.pool.Factory
import com.delizarov.core.pool.SimplePool
import java.util.*

private const val DEFAULT_PART_LIMIT = 50

open class TypeWriter(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
    LinearLayout(context, attrs, defStyleAttr) {

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    private val viewPool = SimplePool(DEFAULT_PART_LIMIT, object : Factory<TextView> {
        override fun create() = TextView(context)
    })

    private val line = Stack<TypePart>()

    init {

    }

    fun addPart(str: String,
                decoration: Decoration = DEFAULT_DECORATION,
                animation: Animation? = null) {

        val part = TypePart(
            str,
            viewPool.obtain()
        )

        line.add(part)
        decoration.decorate(part.tv)
        addView(part.tv)

        if (animation != null) part.tv.startAnimation(animation)
    }

    fun removeLastPart() {
        val part = line.pop()

        removeView(part.tv)
        viewPool.recycle(part.tv)
    }

    fun clear() {

        while (line.isNotEmpty()) removeLastPart()
    }

    class TypePart(
        val str: String,
        val tv: TextView
    ) {
        init {
            tv.text = str
        }
    }

    interface Decoration {
        fun decorate(tv: TextView) {}
    }

    companion object {
        object DEFAULT_DECORATION : Decoration
    }

}
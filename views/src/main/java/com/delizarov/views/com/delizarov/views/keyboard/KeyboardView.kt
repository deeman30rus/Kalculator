package com.delizarov.views.com.delizarov.views.keyboard

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import com.delizarov.core.utils.loop2d
import com.delizarov.views.R
import com.delizarov.views.com.delizarov.views.GridKeyPattern
import kotlin.math.min

internal const val TYPE_NUMERIC = 0
internal const val TYPE_FUNCTIONAL = 1
internal const val TYPE_EQUALS = 2

class KeyboardView(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
    FrameLayout(context, attrs, defStyleAttr) {

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    private val views = mutableListOf<MutableList<View?>>()

    private var layouted = false
    private var decorators = mutableListOf<Decorator>()

    var adapter = Adapter(context, this, GridKeyPattern.EmptyPattern)
        set(value) {
            field = value
            field.callback = onKeyPressed

            prepareViews()
            requestLayout()
        }

    var onKeyPressed: (Key) -> Unit = {}

    init {

        addDecorator(BackgroundDecorator())

        prepareViews()
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)

        if (adapter.itemCount == 0)
            return

        val cellWidth = width / adapter.maxColumns
        val cellHeight = height / adapter.maxRows

        val cellSize = (min(cellWidth, cellHeight) * 0.93).toInt()

        val hMargin = (cellWidth - cellSize) / 2
        val vMargin = (cellHeight - cellSize) / 2

        loop2d(adapter.maxRows, adapter.maxColumns) { r, c ->

            with(views[r][c] ?: return@loop2d) {
                layoutParams = FrameLayout.LayoutParams(cellSize, cellSize)
                foregroundGravity = Gravity.CENTER
                (layoutParams as FrameLayout.LayoutParams).setMargins(c * cellWidth + hMargin, r * cellHeight + vMargin, 0, 0)
            }
        }
    }

    fun addDecorator(decorator: Decorator) {
        decorators.add(decorator)
    }

    private fun prepareViews() {

        layouted = false

        views.clear()
        removeAllViews()

        for (r in 0 until adapter.maxRows) {
            views.add(
                (0 until adapter.maxColumns)
                    .map { c ->

                        val view = if (adapter.getKeyAt(r, c) != null) adapter.createKeyView(r, c)
                        else null

                        for (decorator in decorators) {
                            if (view == null)
                                continue
                            decorator.decorate(view, adapter.getKeyType(r, c))
                        }

                        view
                    }
                    .toMutableList()
            )
        }

        loop2d(adapter.maxRows, adapter.maxColumns) { r, c ->
            addView(views[r][c] ?: return@loop2d)
        }
    }

    class Adapter(
        private val context: Context,
        private val parent: ViewGroup,
        private val pattern: GridKeyPattern
    ) {

        var callback: (Key) -> Unit = {}

        val maxRows = pattern.maxRows
        val maxColumns = pattern.maxCols

        val itemCount = pattern.nonNullKeysCount

        fun getKeyAt(row: Int, col: Int) = pattern[row, col]

        fun createKeyView(row: Int, col: Int): View {

            val key = pattern[row, col] ?: throw IllegalStateException("Can't create view for null key")

            return createKeyView(key)

        }

        fun getKeyType(row: Int, col: Int): Int {

            val key = pattern[row, col] ?: throw IllegalStateException("Can't get key type of null object")

            return getKeyType(key)
        }

        private fun createKeyView(key: Key): View {
            val inflater = LayoutInflater.from(context)

            val view = inflater.inflate(R.layout.view_key, parent, false)

            bindKeyToView(view, key, callback)

            return view
        }

        private fun getKeyType(key: Key) = when {
            key.isNumeric() -> TYPE_NUMERIC
            key.isFunctional() -> TYPE_FUNCTIONAL
            key.isEquals() -> TYPE_EQUALS
            else -> throw IllegalArgumentException("Seems like you've forgot condition for $key")

        }

        companion object {

            fun bindKeyToView(view: View, key: Key, callback: (Key) -> Unit) {

                view.findViewById<TextView>(R.id.key).text = key.toResString(view.context)

                view.setOnClickListener {
                    callback.invoke(key)
                }
            }
        }
    }

    abstract class Decorator {

        abstract fun decorate(view: View, viewType: Int)
    }
}

private fun Key.toResString(context: Context) = context.getString(
    when (this) {
        Key.Key0 -> R.string.key_0
        Key.Key1 -> R.string.key_1
        Key.Key2 -> R.string.key_2
        Key.Key3 -> R.string.key_3
        Key.Key4 -> R.string.key_4
        Key.Key5 -> R.string.key_5
        Key.Key6 -> R.string.key_6
        Key.Key7 -> R.string.key_7
        Key.Key8 -> R.string.key_8
        Key.Key9 -> R.string.key_9
        Key.KeyPlus -> R.string.key_plus
        Key.KeyMinus -> R.string.key_minus
        Key.KeyMultiply -> R.string.key_multiply
        Key.KeyDivide -> R.string.key_divide
        Key.KeyClear -> R.string.key_clear
        Key.KeyAC -> R.string.key_ac
        Key.KeyEquals -> R.string.key_equals
        Key.KeyBackspace -> R.string.key_backspace
        Key.KeyPercent -> R.string.key_percent
        Key.KeyDot -> R.string.key_dot
    }
)
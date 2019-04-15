package com.delizarov.views.com.delizarov.views.expression

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.support.annotation.ColorRes
import android.support.v4.content.ContextCompat
import android.text.SpannableString
import android.text.Spanned
import android.text.TextUtils
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.util.AttributeSet
import android.widget.TextView
import com.delizarov.domain.math.expression.Expression
import com.delizarov.domain.math.misc.isEquals
import com.delizarov.domain.math.misc.isFloatingPoint
import com.delizarov.domain.math.misc.isOperator
import com.delizarov.domain.math.misc.slice
import com.delizarov.views.R
import java.lang.IllegalStateException


class ExpressionView(ctx: Context, attrs: AttributeSet?, defStyleAttr: Int) : TextView(ctx, attrs, defStyleAttr) {

    constructor(ctx: Context) : this(ctx, null)

    constructor(ctx: Context, attrs: AttributeSet?) : this(ctx, attrs, 0)

    var expression = Expression.EMPTY
        set(value) {

            if (field == value) return

            field = value
            render()
        }

    private var _mode = Mode.Expression
    var mode: Mode
        set(value) {

            if (_mode == value) return

            _mode = value
            render()
        }
        get() = _mode

    private var _operatorColor: Int = Color.WHITE
    var operatorColor: Int
        set(value) {

            if (_operatorColor == value) return

            _operatorColor = value
            render()
        }
        get() = _operatorColor

    private var _operandColor: Int = Color.WHITE
    var operandColor: Int
        set(value) {

            if (_operandColor == value) return

            _operandColor = value
            render()
        }
        get() = _operandColor


    init {

        ctx.theme.obtainStyledAttributes(
            attrs,
            R.styleable.ExpressionView,
            0, 0
        ).apply {

            try {
                _mode = getInteger(R.styleable.ExpressionView_mode, Mode.Expression.ordinal).toMode()
                _operatorColor = getColor(R.styleable.ExpressionView_operatorColor, ContextCompat.getColor(context, R.color.dusty_grey))
                _operandColor = getColor(R.styleable.ExpressionView_operandColor, ContextCompat.getColor(context, R.color.dusty_grey))

            } finally {
                recycle()
            }
        }

        render()
    }

    private fun render() {

        if (expression == Expression.EMPTY) return

        val expr = expression.toString()

        val span = if (mode == Mode.Expression)
            SpannableString(expr)
        else SpannableString("$expression=${expression.value}")

        for (i in 0 until span.length) {
            if (span[i].isOperator() || span[i].isEquals()) {
                span.setSpan(StyleSpan(Typeface.BOLD), i, i + 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
                span.setSpan(
                    ForegroundColorSpan(operatorColor),
                    i,
                    i + 1,
                    Spanned.SPAN_INCLUSIVE_EXCLUSIVE
                )
            }
        }

        text = span
    }

    enum class Mode {
        Expression,
        Equation
    }
}

private fun Int.toMode(): ExpressionView.Mode = ExpressionView.Mode.values()[this]
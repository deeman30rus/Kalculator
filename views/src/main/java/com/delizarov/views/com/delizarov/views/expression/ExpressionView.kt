package com.delizarov.views.com.delizarov.views.expression

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.support.v4.content.ContextCompat
import android.text.*
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.util.AttributeSet
import android.widget.TextView
import com.delizarov.domain.math.expression.Expression
import com.delizarov.domain.math.expression.Operand
import com.delizarov.domain.math.expression.Operator
import com.delizarov.views.R
import java.text.DecimalFormat


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

    private var _valueColor: Int = Color.WHITE
    var valueColor: Int
        set(value) {

            if (_valueColor == value) return

            _valueColor = value
            render()
        }
        get() = _valueColor


    init {

        ctx.theme.obtainStyledAttributes(
            attrs,
            R.styleable.ExpressionView,
            0, 0
        ).apply {

            try {
                _mode = getInteger(R.styleable.ExpressionView_mode, Mode.Expression.ordinal).toMode()
                _operatorColor = getColor(
                    R.styleable.ExpressionView_operatorColor,
                    ContextCompat.getColor(context, R.color.dusty_grey)
                )
                _operandColor = getColor(
                    R.styleable.ExpressionView_operandColor,
                    ContextCompat.getColor(context, R.color.dusty_grey)
                )
                _valueColor =
                    getColor(R.styleable.ExpressionView_valueColor, ContextCompat.getColor(context, R.color.dusty_grey))

            } finally {
                recycle()
            }
        }

        render()
    }

    private fun render() {

        if (expression == Expression.EMPTY) return

        val span = SpannableStringBuilder()
        var p = 0

        for (term in expression.terms) {

            val str = term.toString()

            when (term) {
                is Operand -> {
                    span.append(str)

                    p += str.length
                }
                is Operator -> {

                    val s = SpannableString(" $str ")
                    span.append(s)

                    span.setSpan(StyleSpan(Typeface.BOLD), p, p + s.length, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
                    span.setSpan(
                        ForegroundColorSpan(operatorColor),
                        p,
                        p + s.length,
                        Spanned.SPAN_INCLUSIVE_EXCLUSIVE
                    )

                    p += s.length
                }
            }
        }

        if (mode == Mode.Equation) {
            // adding equals
            span.append(" = ")
            span.setSpan(StyleSpan(Typeface.BOLD), p, p + 3, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
            span.setSpan(
                ForegroundColorSpan(operatorColor),
                p,
                p + 3,
                Spanned.SPAN_INCLUSIVE_EXCLUSIVE
            )
            p += 3

            // adding value
            val value = DecimalFormat("0.##############").format(expression.value)
            span.append(value)
            span.setSpan(
                ForegroundColorSpan(valueColor),
                p,
                p + value.length,
                Spanned.SPAN_INCLUSIVE_EXCLUSIVE
            )
        }

        text = span
    }

    enum class Mode {
        Expression,
        Equation
    }
}

private fun Int.toMode(): ExpressionView.Mode = ExpressionView.Mode.values()[this]
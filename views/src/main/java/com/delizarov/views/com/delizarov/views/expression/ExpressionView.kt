package com.delizarov.views.com.delizarov.views.expression

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.util.TypedValue
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.delizarov.domain.math.expression.Expression
import com.delizarov.domain.math.expression.Operand
import com.delizarov.domain.math.expression.Operator
import com.delizarov.domain.math.expression.Term
import com.delizarov.views.R
import java.lang.IllegalArgumentException


private const val DURATION = 200L // animation duration ms
private const val TEXT_VIEW_LIMIT = 50

class ExpressionView(ctx: Context, attrs: AttributeSet?, defStyleAttr: Int) : LinearLayout(ctx, attrs, defStyleAttr) {

    constructor(ctx: Context) : this(ctx, null)

    constructor(ctx: Context, attrs: AttributeSet?) : this(ctx, attrs, 0)

    var expression = Expression.EMPTY
        set(value) {

            if (field == value) return

            field = value
            render()
        }

    private val textViews = mutableListOf<TextView>()

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


    private var animateChange = false
    private var textSize: Int = 14

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

                animateChange = getBoolean(R.styleable.ExpressionView_animateChange, false)
                textSize = getDimensionPixelSize(R.styleable.ExpressionView_textSize, 14)

            } finally {
                recycle()
            }
        }

        layoutParams = LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT)
        orientation = HORIZONTAL

        textViews.addAll(
            (0 until TEXT_VIEW_LIMIT).map {
                TextView(context).apply {
                    layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
                }
            }
        )

        render()
    }

    private fun render() {

        if (expression == Expression.EMPTY) return

        if (expression.terms.size > TEXT_VIEW_LIMIT) throw IllegalArgumentException("Expression is too large, current limit is $TEXT_VIEW_LIMIT terms")

        removeAllViews()

        repeat(expression.terms.size) { i ->
            val term = expression.terms[i]
            val textView = textViews[i]

            setTermPropertiesToTextView(term, textView)

            addView(textView)
        }

        if (mode == Mode.Equation) {

            // applying equals
            val eqTv = textViews[expression.terms.size]
            textViewApplyProperties(
                eqTv,
                "=",
                operatorColor,
                textSize,
                Typeface.BOLD,
                listOf(10, 0, 10, 0)
            )

            addView(eqTv)

            val valueTv = textViews[expression.terms.size + 1]
            textViewApplyProperties(
                valueTv,
                expression.value.toString(),
                valueColor,
                textSize,
                Typeface.BOLD,
                listOf(0, 0, 0, 0)
            )

            addView(valueTv)
        }
    }

    private fun setTermPropertiesToTextView(term: Term, textView: TextView) = when (term) {
        is Operand -> setOperandPropertiesToTextView(term, textView)
        is Operator -> setOperatorPropertiesToTextView(term, textView)
        else -> throw IllegalArgumentException("Term $term does not supported")
    }


    private fun setOperandPropertiesToTextView(operand: Operand, tv: TextView) = textViewApplyProperties(
        tv,
        operand.toString(),
        operandColor,
        textSize,
        Typeface.NORMAL,
        listOf(0, 0, 0, 0)
    )

    private fun setOperatorPropertiesToTextView(operator: Operator, tv: TextView) = textViewApplyProperties(
        tv,
        operator.toString(),
        operatorColor,
        textSize,
        Typeface.BOLD,
        listOf(10, 0, 10, 0)
    )

    companion object {

        /**
         * applying provided properties to selected text view
         * @param tv [TextView]
         * @param str text property
         * @param textColor direct text color not resource/color
         * @param textSize text size in pixels
         * @param margins margins array [start, top, end, bottom]
         * */
        fun textViewApplyProperties(
            tv: TextView,
            str: String,
            textColor: Int,
            textSize: Int,
            typeface: Int,
            margins: List<Int>
        ) = tv.apply {
            text = str
            setTextColor(textColor)
            setTypeface(this.typeface, typeface)
            setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize.toFloat())
            (layoutParams as LayoutParams).setMargins(margins[0], margins[1], margins[2], margins[3])
        }
    }

    enum class Mode {
        Expression,
        Equation
    }

}

private fun Int.toMode(): ExpressionView.Mode = ExpressionView.Mode.values()[this]
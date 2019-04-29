package com.delizarov.fcalc.ui.views

import android.content.Context
import android.graphics.Typeface
import android.support.v4.content.ContextCompat
import android.util.TypedValue
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.widget.FrameLayout
import android.widget.TextView
import com.delizarov.fcalc.R
import com.delizarov.views.com.delizarov.views.TypeWriter

const val APPEARANCE_DURATION = 300L // ms

class ExpressionTypeWriter(private val typeWriter: TypeWriter) {

    private val context: Context
        get() = typeWriter.context

    private val numberColor = ContextCompat.getColor(context, R.color.dusty_grey)
    private val operatorColor = ContextCompat.getColor(context, R.color.white)
    private val textSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16f, context.resources.displayMetrics)

    private val operatorDecoration = object : TypeWriter.Decoration {

        override fun decorate(tv: TextView) {
            super.decorate(tv)

            tv.apply {
                setTextColor(operatorColor)
                layoutParams = ViewGroup.MarginLayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT
                ).apply {
                    setMargins(10, 0, 10, 0)
                }
                this.textSize = this@ExpressionTypeWriter.textSize
            }
        }
    }

    private val numberDecoration = object : TypeWriter.Decoration {

        override fun decorate(tv: TextView) {
            super.decorate(tv)

            tv.apply {
                setTextColor(numberColor)
                layoutParams = ViewGroup.MarginLayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT
                ).apply {
                    setMargins(0, 0, 0, 0)
                }
                this.textSize = this@ExpressionTypeWriter.textSize
            }
        }
    }


    fun addPart(part: String) {

        val decoration = if (part[0].isDigit()) numberDecoration else operatorDecoration
        val animation = AlphaAnimation(0f, 1f).apply {
            duration = APPEARANCE_DURATION
        }

        typeWriter.addPart(part, decoration, animation)
    }

    fun removeLastPart() {
        typeWriter.removeLastPart()
    }

    fun clear() {
        typeWriter.clear()
    }
}
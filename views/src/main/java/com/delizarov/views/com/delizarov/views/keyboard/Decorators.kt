package com.delizarov.views.com.delizarov.views.keyboard

import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.TextView
import com.delizarov.views.R
import java.lang.IllegalArgumentException

class BackgroundDecorator : KeyboardView.Decorator() {

    override fun decorate(view: View, viewType: Int) {

        decorateBackground(view, viewType)
        decorateText(view, viewType)
    }

    private fun decorateBackground(view: View, viewType: Int) {
        view.setBackgroundResource(
            when (viewType) {
                TYPE_EQUALS -> R.drawable.bg_equals_key
                TYPE_FUNCTIONAL -> R.drawable.bg_functional_key
                TYPE_NUMERIC -> R.drawable.bg_numeric_key
                else -> throw IllegalArgumentException("Don't know type $viewType")
            }
        )
    }

    private fun decorateText(view: View, viewType: Int) {

        (view as TextView).setTextColor(
            when (viewType) {
                TYPE_EQUALS -> ContextCompat.getColor(view.context, R.color.alto)
                TYPE_NUMERIC, TYPE_FUNCTIONAL -> ContextCompat.getColor(view.context, R.color.dusty_grey)
            else -> throw IllegalArgumentException("Don't know type $viewType")
            }
        )
    }
}
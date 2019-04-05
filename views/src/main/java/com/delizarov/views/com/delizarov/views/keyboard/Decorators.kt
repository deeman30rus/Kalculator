package com.delizarov.views.com.delizarov.views.keyboard

import android.view.View
import com.delizarov.views.R
import java.lang.IllegalArgumentException

class BackgroundDecorator : KeyboardView.Decorator() {

    override fun decorate(view: View, viewType: Int) {
        view.setBackgroundResource(
            when (viewType) {
                TYPE_EQUALS -> R.drawable.bg_equals_key
                TYPE_FUNCTIONAL -> R.drawable.bg_functional_key
                TYPE_NUMERIC -> R.drawable.bg_numeric_key
                else -> throw IllegalArgumentException("Don't know type $viewType")
            }
        )
    }
}
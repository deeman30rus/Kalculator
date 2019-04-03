package com.delizarov.core.mvc

import android.view.View

abstract class MvcView(
    protected val root: View,
    protected val listener: EventListener
) {
    fun onStart() = listener.onStart()

    fun onResume() = listener.onResume()

    fun onPause() = listener.onPause()

    fun onStop() = listener.onStop()

    interface EventListener {

        fun onStart() {}

        fun onPause() {}

        fun onResume() {}

        fun onStop() {}
    }

    abstract class Factory<out T: MvcView> {

        abstract fun create(): T
    }
}

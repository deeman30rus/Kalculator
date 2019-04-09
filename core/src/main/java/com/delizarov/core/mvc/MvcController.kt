package com.delizarov.core.mvc

abstract class MvcController<in TView : MvcView> : MvcView.EventListener {

    abstract fun attachView(view: TView)

    abstract fun detachView()

    abstract class Factory<out T : MvcController<*>> {

        abstract fun create(): T
    }
}
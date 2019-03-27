package com.delizarov.core.mvc

class BaseController<T : View> {

    var view: T? = null

    fun attachView(view: T) {
        this.view = view
    }
}
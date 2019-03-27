package com.delizarov.core.observable

interface Observer<T> {

    fun onNext(obj: T)
}


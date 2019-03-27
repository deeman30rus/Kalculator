package com.delizarov.core.observable

interface Subscription {

    fun close()
}

interface Observable<T> {

    fun registerObserver(observer: Observer<T>)
    fun removeObserver(observer: Observer<T>)
    fun notifyObservers()

    fun subscripe(): Subscription
}
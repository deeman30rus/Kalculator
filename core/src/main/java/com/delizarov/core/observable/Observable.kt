package com.delizarov.core.observable

typealias ValueHandler<T> = (T) -> Unit

interface CloseableSubscription {

    fun close()
}

interface Observer<T> {

    fun onNext(item: T)
}

open class Observable<T> {

    protected val observers = mutableListOf<Observer<T>>()

    open fun emit(item: T) {
        notifyObservers(item)
    }

    fun subscribe(handler: ValueHandler<T>): CloseableSubscription {

        val subscription = Subscription(handler)
        registerObserver(subscription)

        return subscription
    }

    private fun registerObserver(observer: Observer<T>) {
        observers.add(observer)
    }

    private fun removeObserver(observer: Observer<*>) {
        observers.remove(observer)
    }

    protected fun notifyObservers(item: T) {

        observers.forEach { it.onNext(item) }
    }

    private inner class Subscription<T>(
        private val handler: ValueHandler<T>
    ) : Observer<T>, CloseableSubscription {

        override fun onNext(item: T) {
            handler.invoke(item)
        }

        override fun close() {
            removeObserver(this@Subscription)
        }
    }
}
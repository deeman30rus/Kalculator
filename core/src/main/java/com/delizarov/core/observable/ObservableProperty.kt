package com.delizarov.core.observable

open class ObservableProperty<T>(
    initial: T
) : Observable<T>() {

    var property: T = initial
        set(value) {
            field = value
            emit(field)
        }
}
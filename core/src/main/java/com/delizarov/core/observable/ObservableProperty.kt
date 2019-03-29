package com.delizarov.core.observable

class ObservableProperty<T>(
    initial: T
) : Observable<T>() {

    var item: T = initial
        set(value) {

            if (field == value) return

            field = value
            emit(field)
        }
}
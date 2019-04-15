package com.delizarov.domain.input

import com.delizarov.domain.DomainException

abstract class InputInteractor<in T> {

    var state: String = ""
        protected set

    @Throws(DomainException::class)
    abstract fun process(input: T)

}
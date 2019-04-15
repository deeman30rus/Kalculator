package com.delizarov.domain

sealed class DomainException(msg: String) : Throwable(msg) {

    class UnrecognizedInputException(input: String) : DomainException("Unrecognized input $input")
}
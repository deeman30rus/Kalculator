package com.delizarov.domain.user.input

private const val START_CHAR = '_' // should use this char to mark beginning

class InputHistory {

    private var _input = mutableListOf<Char>()

    fun addChar(c: Char) {
        _input.add(c)
    }

    fun undo() {
        _input.dropLast(1)
    }

    fun clear() {
        _input.clear()
    }
}
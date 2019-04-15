package com.delizarov.views.com.delizarov.views.keyboard

enum class Key(
    private val str: String
) {
    Key0("0"),
    Key1("1"),
    Key2("2"),
    Key3("3"),
    Key4("4"),
    Key5("5"),
    Key6("6"),
    Key7("7"),
    Key8("8"),
    Key9("9"),
    KeyPlus("+"),
    KeyMinus("-"),
    KeyMultiply("*"),
    KeyDivide("/"),
    KeyClear("clear"),
    KeyAC("all clear"),
    KeyEquals("="),
    KeyBackspace("<-"),
    KeyPercent("%"),
    KeyDot(".");

    override fun toString() = str
}

fun Key.isNumberPart() = this == Key.Key0 ||
        this == Key.Key1 ||
        this == Key.Key2 ||
        this == Key.Key3 ||
        this == Key.Key4 ||
        this == Key.Key5 ||
        this == Key.Key6 ||
        this == Key.Key7 ||
        this == Key.Key8 ||
        this == Key.Key9 ||
        this == Key.KeyDot

fun Key.isFunctional() = this == Key.KeyClear ||
        this == Key.KeyDivide ||
        this == Key.KeyMultiply ||
        this == Key.KeyBackspace ||
        this == Key.KeyMinus ||
        this == Key.KeyPlus ||
        this == Key.KeyPercent

fun Key.isEquals() = this == Key.KeyEquals


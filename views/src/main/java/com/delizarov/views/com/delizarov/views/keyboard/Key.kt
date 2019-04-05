package com.delizarov.views.com.delizarov.views.keyboard

enum class Key {
    Key0,
    Key1,
    Key2,
    Key3,
    Key4,
    Key5,
    Key6,
    Key7,
    Key8,
    Key9,
    KeyPlus,
    KeyMinus,
    KeyMultiply,
    KeyDivide,
    KeyClear,
    KeyAC,
    KeyEquals,
    KeyBackspace,
    KeyPercent,
    KeyDot
}

fun Key.isNumeric() = this == Key.Key0 ||
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


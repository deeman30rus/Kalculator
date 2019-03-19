package com.delizarov.views.com.delizarov.views

import com.delizarov.views.com.delizarov.views.keyboard.Key

sealed class GridKeyPattern(
    private val pattern: List<List<Key?>>
) {

    val maxRows: Int by lazy { pattern.size }
    val maxCols: Int by lazy { if (pattern.isEmpty()) 0 else pattern.map { r -> r.size }.max()!! }

    val nonNullKeysCount: Int by lazy { pattern.flatten().filter { item -> item != null }.count() }

    operator fun get(r: Int, c: Int): Key? = pattern[r][c]

    object DefaultPattern : GridKeyPattern(
        listOf(
            listOf<Key?>(Key.KeyClear, Key.KeyDivide, Key.KeyMultiply, Key.KeyBackspace),
            listOf<Key?>(Key.Key7, Key.Key8, Key.Key9, Key.KeyMinus),
            listOf<Key?>(Key.Key4, Key.Key5, Key.Key7, Key.KeyPlus),
            listOf<Key?>(Key.Key1, Key.Key2, Key.Key3, Key.KeyPercent),
            listOf<Key?>(null, Key.Key0, Key.KeyDot, Key.KeyEquals)
        )
    )

    object EmptyPattern : GridKeyPattern(emptyList())
}
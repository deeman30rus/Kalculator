package com.delizarov.core.utils

fun loop2d(maxRows: Int, maxColumns: Int, block: (Int, Int) -> Unit) {
    for (r in 0 until maxRows)
        for (c in 0 until maxColumns)
            block.invoke(r, c)
}
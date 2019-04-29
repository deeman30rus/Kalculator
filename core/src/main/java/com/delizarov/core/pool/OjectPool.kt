package com.delizarov.core.pool

import java.util.*

sealed class ObjectPoolException(msg: String) : Throwable(msg)

class ExceedPoolLimitException(limit: Int) : ObjectPoolException("Exceed pool limit($limit) exception")

class TwiceRecyclingException : ObjectPoolException("You can't recycle object twice")

class NonPoolObjectException : ObjectPoolException("Object you want to recycle does not belong to this pool")

interface Factory<T> {
    fun create(): T
}

interface Pool<T> {
    fun obtain(): T

    fun recycle(obj: T)
}

/**
 * Non concurrent fixed size simple object pool
 * */
class SimplePool<T>(
    private val size: Int,
    private val factory: Factory<T>
) : Pool<T>{

    private val free = LinkedList<T>()
    private val obtained = mutableListOf<Ref<T>>()

    init {
        free.addAll(
            (0 until size).map { factory.create() }
        )
    }

    override fun obtain(): T {

        if (free.isEmpty()) throw ExceedPoolLimitException(size)

        val obj = free.pop()

        obtained.add(obj.ref)

        return obj
    }

    override fun recycle(obj: T) {

        val subList = obtained.filter { it.obj === obj }

        when {
            subList.isEmpty() -> {
                if (free.contains(obj))
                    throw TwiceRecyclingException()
                else
                    throw NonPoolObjectException()
            }
            subList.size > 1 -> throw IllegalStateException("2 references obtained simultaneously")
            subList.size == 1 -> {

                val elem = subList[0]

                obtained.remove(elem)
                free.push(elem.obj)
            }
        }
    }

    private class Ref<T>(
        val obj: T
    )

    private val T.ref: Ref<T>
        get() = Ref(this)
}
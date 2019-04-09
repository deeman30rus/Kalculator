package com.delizarov.core.dataaccess

import com.delizarov.core.observable.Cancelable

interface Repository<T>

interface KeyValueRepository<TKey, TValue> : Repository<TKey> {

    fun add(key: TKey, value: TValue) {
        throw UnsupportedOperationException("Operation add() is not supported for $this by default")
    }

    fun update(key: TKey, value: TValue) {
        throw UnsupportedOperationException("Operation update() is not supported for $this by default")
    }

    fun delete(key: TKey) {
        throw UnsupportedOperationException("Operation delete() is not supported for $this by default")
    }

    fun get(key: TKey): TValue? {
        throw UnsupportedOperationException("Operation get() by key is not supported for $this by default")
    }

    fun getAll(): MutableCollection<TValue> {
        throw UnsupportedOperationException("Operation getAll() is not supported for $this by default")
    }
}

open class ObservableRepository<TKey, TValue>(
    private val repo: KeyValueRepository<TKey, TValue>
) : KeyValueRepository<TKey, TValue> by repo {

    private val observers = mutableListOf<CrudOperationsListener<TValue>>()

    fun subscribe(listener: CrudOperationsListener<TValue>) = Subscription(listener)

    override fun add(key: TKey, value: TValue) {
        repo.add(key, value)

        for (observer in observers)
            observer.onAdd(value)
    }

    override fun update(key: TKey, value: TValue) {

        val old = repo.get(key) ?: return

        super.update(key, value)

        for (observer in observers)
            observer.onUpdate(old, value)
    }

    override fun delete(key: TKey) {

        val deleted = repo.get(key) ?: return

        repo.delete(key)

        for (observer in observers)
            observer.onDelete(deleted)
    }

    interface CrudOperationsListener<T> {

        fun onAdd(obj: T) {}

        fun onUpdate(old: T, new: T) {}

        fun onDelete(obj: T) {}
    }

    inner class Subscription(
        private val listener: CrudOperationsListener<TValue>
    ) : Cancelable {

        init {
            observers.add(listener)
        }

        override fun cancel() {
            observers.remove(listener)
        }
    }
}

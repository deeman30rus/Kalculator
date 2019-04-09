package com.delizarov.fcalc.repo

import com.delizarov.core.dataaccess.KeyValueRepository
import com.delizarov.core.dataaccess.ObservableRepository
import com.delizarov.domain.math.expression.Expression

object HistoryRepository : ObservableRepository<String, Expression>(HistoryRepositoryImpl())

private class HistoryRepositoryImpl : KeyValueRepository<String, Expression> {

    private val cache = mutableMapOf<String, Expression>()

    override fun add(key: String, value: Expression) {
        cache[key] = value
    }

    override fun update(key: String, value: Expression) {
        cache[key] = value
    }

    override fun delete(key: String) {
        cache.remove(key)
    }

    override fun get(key: String) = cache[key]

    override fun getAll() = cache.values
}
package com.delizarov.core

import com.delizarov.core.pool.*
import org.junit.Test

class ObjectPoolTest {

    @Test(expected = ExceedPoolLimitException::class)
    fun `exceed pool limit on empty pool test`() {

        val pool = SimplePool(
            0,
            object : Factory<Any> {
                override fun create() = Any()
            }
        )

        pool.obtain()
    }

    @Test(expected = ExceedPoolLimitException::class)
    fun `exceed limit on non empty ref pool test`() {

        val size = 2

        val pool = SimplePool(
            size,
            object : Factory<Any> {
                override fun create() = Any()
            }
        )

        repeat(size + 1) {
            pool.obtain()
        }
    }

    @Test(expected = TwiceRecyclingException::class)
    fun `recycling twice same ref object test`() {

        val pool = SimplePool(2, RefStructure.ObjFactory)

        val obj = pool.obtain()

        pool.recycle(obj)
        pool.recycle(obj)
    }

    @Test(expected = TwiceRecyclingException::class)
    fun `recycling twice same data object test`() {

        val pool = SimplePool(2, DataStructure.ObjFactory)

        val obj = pool.obtain()

        pool.recycle(obj)
        pool.recycle(obj)
    }

    @Test(expected = NonPoolObjectException::class)
    fun `recycling non pool ref object test`() {

        val pool = SimplePool(2, RefStructure.ObjFactory)

        val obj = RefStructure.ObjFactory.create()

        pool.recycle(obj)
    }

    @Test(expected = NonPoolObjectException::class)
    fun `recycling non pool data object test`() {

        val pool = SimplePool(2, DataStructure.ObjFactory)

        val obj = DataStructure.ObjFactory.create()

        pool.recycle(obj)
    }

    private class RefStructure(
        val value1: Int,
        val value2: Any
    ) {

        object ObjFactory : Factory<RefStructure> {

            override fun create() = createRandomObject()
        }

        companion object {

            private fun createRandomObject() = RefStructure(
                (0..1000000).random(),
                Any()
            )
        }
    }

    private data class DataStructure(
        val value1: Int,
        val value2: Any
    ) {
        object ObjFactory : Factory<RefStructure> {

            override fun create() = createRandomObject()
        }

        companion object {

            private fun createRandomObject() = RefStructure(
                (0..1000000).random(),
                Any()
            )
        }
    }

}
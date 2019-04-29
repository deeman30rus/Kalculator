package com.delizarov.fcalc.domain.input

import com.delizarov.fcalc.domain.KeyInputInteractor
import com.delizarov.views.com.delizarov.views.keyboard.Key
import junit.framework.TestCase.assertEquals
import org.junit.Test

class KeyInputInteractorTest {

    @Test
    fun `simple correct input test`() {

        assertCorrect(
            listOf(
                Key.Key2, Key.KeyPlus, Key.Key2
            ), "2+2"
        )
    }

    @Test
    fun `replace operator test`() {

        assertCorrect(
            listOf(
                Key.Key2, Key.KeyPlus, Key.KeyMultiply, Key.Key3
            ), "2*3"
        )
    }

    @Test
    fun `key backspace on non empty state`() {

        assertCorrect(
            listOf(
                Key.Key2, Key.KeyPlus, Key.KeyBackspace
            ), "2"
        )
    }

    @Test
    fun `key backspace on empty state`() {

        assertCorrect(
            listOf(
                Key.KeyBackspace
            ), ""
        )
    }

    @Test
    fun `multiple key backspace`() {

        assertCorrect(
            listOf(
                Key.Key2, Key.KeyBackspace, Key.KeyBackspace, Key.KeyBackspace
            ), ""
        )
    }

    @Test
    fun `key clear after last test`() {

        assertCorrect(
            listOf(
                Key.Key2, Key.KeyPlus, Key.KeyMultiply, Key.Key3, Key.KeyClear
            ), ""
        )
    }

    @Test
    fun `key clear in middle of input test`() {

        assertCorrect(
            listOf(
                Key.Key2, Key.KeyPlus, Key.KeyClear, Key.Key3, Key.Key3
            ), "33"
        )
    }

    @Test
    fun `key dot on empty test`() {

        assertCorrect(
            listOf(
                Key.KeyDot, Key.Key3, Key.KeyPlus, Key.Key2
            ), "0.3+2"
        )
    }

    @Test
    fun `multiple key dot on empty test`() {

        assertCorrect(
            listOf(
                Key.KeyDot, Key.KeyDot, Key.KeyDot, Key.KeyDot, Key.KeyDot, Key.Key3
            ), "0.3"
        )
    }

    @Test
    fun `drop zero point test`() {

        assertCorrect(
            listOf(
                Key.KeyDot, Key.KeyBackspace
            ), ""
        )
    }

    @Test
    fun `drop non zero point test`() {

        assertCorrect(
            listOf(
                Key.Key3, Key.KeyDot, Key.KeyBackspace
            ), "3"
        )
    }

    @Test
    fun `double 0 test`() {

        assertCorrect(
            listOf(
                Key.Key3, Key.Key0, Key.Key0, Key.KeyPlus, Key.Key0, Key.Key0
            ), "300+0"
        )
    }

    companion object {

        private fun assertCorrect(keySeq: List<Key>, expectedResult: String) {

            val keyInput = KeyInputInteractor()

            for (key in keySeq) {
                keyInput.process(key)
            }

            assertEquals(expectedResult, keyInput.state)
        }
    }
}
package com.delizarov.domain.parser

import com.delizarov.domain.math.expression.Term
import com.delizarov.domain.math.misc.toTerm
import com.delizarov.domain.parser.impl.StringTermParser
import junit.framework.TestCase.assertEquals
import org.junit.Test
import java.lang.IllegalArgumentException

class StringTermParserTest {

    @Test
    fun `parse empty list`() {

        val dataSet = mapOf(
            "" to emptyList<Term>()
        )

        assertParseCorrect(dataSet)
    }

    @Test
    fun `parse simple expression`() {

        val dataSet = mapOf(
            "2+3" to listOf("2".toTerm(), "+".toTerm(), "3".toTerm()),
            "2-3" to listOf("2".toTerm(), "-".toTerm(), "3".toTerm()),
            "2*3" to listOf("2".toTerm(), "*".toTerm(), "3".toTerm()),
            "2/3" to listOf("2".toTerm(), "/".toTerm(), "3".toTerm()),
            "2%3" to listOf("2".toTerm(), "%".toTerm(), "3".toTerm())
        )

        assertParseCorrect(dataSet)
    }

    @Test
    fun `parse floating point expression`() {

        val dataSet = mapOf(
            "2.1+3.4" to listOf("2.1".toTerm(), "+".toTerm(), "3.4".toTerm())
        )

        assertParseCorrect(dataSet)
    }

    @Test
    fun `end operator expression`() {

        val dataSet = mapOf(
            "2.1+" to listOf("2.1".toTerm(), "+".toTerm())
        )

        assertParseCorrect(dataSet)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `incorrect symbol expression`() {

        val parser: TermParser<String> = StringTermParser()
        parser.parse("2 + 3")
    }

    companion object {

        private fun assertParseCorrect(dataSet: Map<String, Collection<Term>>) {

            val parser: TermParser<String> = StringTermParser()
            dataSet.forEach { (src, expected) ->
                assertEquals("assertion failed for $src", expected, parser.parse(src))
            }
        }
    }
}
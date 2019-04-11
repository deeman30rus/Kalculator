package com.delizarov.domain.math.calc.proc.impl

import com.delizarov.domain.math.calc.proc.Processor
import com.delizarov.domain.math.expression.BinaryOperator
import com.delizarov.domain.math.expression.Operand
import com.delizarov.domain.math.expression.Operator
import com.delizarov.domain.math.misc.isFloatingPoint
import com.delizarov.domain.math.misc.isOperator
import com.delizarov.domain.math.misc.slice
import com.delizarov.domain.math.misc.toTerm
import java.util.*

class RpmProcessor : Processor {

    private val op = Stack<Operator>()
    private val nums = Stack<Operand>()

    override fun process(expr: String): Float {

        var pos = 0

        do {

            val sTerm = readTerm(expr, pos)
            pos += sTerm.length

            val term = sTerm.toTerm()

            when (term) {
                is Operand -> {
                    nums.push(term)
                }
                is Operator -> {
                    while (op.isNotEmpty() && op.peek().priority >= term.priority) {
                        tryReduce(nums, op.peek())
                        op.pop()
                    }

                    op.push(term)
                }
            }

        } while (pos < expr.length)

        while (op.isNotEmpty()) {
            tryReduce(nums, op.peek())
            op.pop()
        }

        if (nums.size != 1)
            throw IllegalStateException("Nums size not equals 1 somehow")

        return nums.peek().value
    }

    companion object {

        private fun tryReduce(nums: Stack<Operand>, operator: Operator) = try { reduce(nums, operator) } catch(ex: Exception) { throw IllegalStateException() }

        private fun reduce(nums: Stack<Operand>, operator: Operator) {

            val r = nums.pop()
            val l = nums.pop()

            when (operator) {
                is BinaryOperator -> nums.push(operator.process(l, r))
            }

        }
    }
}

private fun readTerm(expr: String, pos: Int) = when {

    expr[pos].isDigit() -> readNumber(expr, pos)

    expr[pos].isOperator() -> readOperator(expr, pos)

    else -> throw IllegalArgumentException("can't parse symbol ${expr[pos]} at $pos")
}

private fun readNumber(s: String, p: Int) = s.slice(p) { c -> c.isDigit() || c.isFloatingPoint() }

private fun readOperator(s: String, p: Int) = s.slice(p) { c -> c.isOperator() }

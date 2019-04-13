package com.delizarov.domain.math.calc.proc.impl

import com.delizarov.domain.math.calc.proc.Processor
import com.delizarov.domain.math.expression.BinaryOperator
import com.delizarov.domain.math.expression.Operand
import com.delizarov.domain.math.expression.Operator
import com.delizarov.domain.math.expression.Term
import java.util.*

class RpmProcessor : Processor<Collection<Term>> {

    private val op = Stack<Operator>()
    private val nums = Stack<Operand>()

    override fun process(expr: Collection<Term>): Float {

        for (term in expr) {
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
        }

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
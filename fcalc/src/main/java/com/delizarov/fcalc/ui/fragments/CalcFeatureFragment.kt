package com.delizarov.fcalc.ui.fragments


import com.delizarov.core.mvc.MvcCombination
import com.delizarov.core.mvc.MvcController
import com.delizarov.core.mvc.MvcFragment
import com.delizarov.core.mvc.MvcView
import com.delizarov.fcalc.R
import com.delizarov.fcalc.mvc.CalculatorMvcView
import com.delizarov.fcalc.mvc.HistoryMvcView

class CalcFeatureFragment : MvcFragment() {

    private val _mvcCombinations: MutableCollection<MvcCombination<*, *>>  = mutableListOf(
        MvcCombination(
            object : MvcController.Factory<CalculatorMvcView.Controller>() {

                override fun create() = CalculatorMvcView.Controller(context!!)
            },
            object : MvcView.Factory<CalculatorMvcView>() {

                override fun create() = CalculatorMvcView(view!!)
            }
        ),
        MvcCombination(
            object : MvcController.Factory<HistoryMvcView.Controller>() {

                override fun create() = HistoryMvcView.Controller()
            },
            object : MvcView.Factory<HistoryMvcView>() {

                override fun create() = HistoryMvcView(view!!)
            }
        )
    )

    override val mvcCombinations: MutableCollection<MvcCombination<*, *>>
        get() = _mvcCombinations

    override val layoutResId: Int
        get() = R.layout.fragment_calculator
}
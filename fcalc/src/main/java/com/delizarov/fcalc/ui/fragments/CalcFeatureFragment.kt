package com.delizarov.fcalc.ui.fragments


import com.delizarov.core.mvc.MvcContainer
import com.delizarov.core.mvc.MvcController
import com.delizarov.core.mvc.MvcFragment
import com.delizarov.core.mvc.MvcView
import com.delizarov.fcalc.R
import com.delizarov.fcalc.domain.impl.KeyInputInteractor
import com.delizarov.fcalc.mvc.CalculatorMvcView
import com.delizarov.fcalc.mvc.HistoryMvcView

class CalcFeatureFragment : MvcFragment() {

    private val _mvcContainers: MutableCollection<MvcContainer<*, *>>  = mutableListOf(
        MvcContainer(
            object : MvcController.Factory<CalculatorMvcView.Controller>() {

                override fun create() = CalculatorMvcView.Controller(KeyInputInteractor())
            },
            object : MvcView.Factory<CalculatorMvcView>() {

                override fun create() = CalculatorMvcView(view!!)
            }
        ),
        MvcContainer(
            object : MvcController.Factory<HistoryMvcView.Controller>() {

                override fun create() = HistoryMvcView.Controller()
            },
            object : MvcView.Factory<HistoryMvcView>() {

                override fun create() = HistoryMvcView(view!!)
            }
        )
    )

    override val mvcContainers: MutableCollection<MvcContainer<*, *>>
        get() = _mvcContainers

    override val layoutResId: Int
        get() = R.layout.fragment_calculator
}
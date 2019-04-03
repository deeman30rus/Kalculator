package com.delizarov.fcalc.ui


import com.delizarov.core.mvc.MvcController
import com.delizarov.core.mvc.MvcFragment
import com.delizarov.core.mvc.MvcView
import com.delizarov.fcalc.R
import com.delizarov.fcalc.mvc.CalculatorView

class CalcFragment : MvcFragment<CalculatorView, CalculatorView.Controller>() {

    override val layoutResId: Int
        get() = R.layout.fragment_calculator

    override fun initMvcViewFactory() {
        mvcViewFactory = object : MvcView.Factory<CalculatorView>() {

            override fun create() = CalculatorView(view!!, mvcController)
        }
    }

    override fun initMvcControllerFactory() {
        mvcControllerFactory = object : MvcController.Factory<CalculatorView.Controller>() {

            override fun create() = CalculatorView.Controller(context!!)
        }
    }
}
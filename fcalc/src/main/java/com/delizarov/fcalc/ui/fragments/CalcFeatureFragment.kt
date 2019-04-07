package com.delizarov.fcalc.ui.fragments


import com.delizarov.core.mvc.MvcController
import com.delizarov.core.mvc.MvcFragment
import com.delizarov.core.mvc.MvcView
import com.delizarov.fcalc.R
import com.delizarov.fcalc.mvc.CalculatorMvcView

class CalcFeatureFragment : MvcFragment<CalculatorMvcView, CalculatorMvcView.Controller>() {

    override val layoutResId: Int
        get() = R.layout.fragment_calculator

    override fun initMvcViewFactory() {
        mvcViewFactory = object : MvcView.Factory<CalculatorMvcView>() {

            override fun create() = CalculatorMvcView(view!!, mvcController)
        }
    }

    override fun initMvcControllerFactory() {
        mvcControllerFactory = object : MvcController.Factory<CalculatorMvcView.Controller>() {

            override fun create() = CalculatorMvcView.Controller(context!!)
        }
    }
}
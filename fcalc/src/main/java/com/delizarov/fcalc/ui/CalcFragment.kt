package com.delizarov.fcalc.ui


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.delizarov.core.fragments.BaseFragment
import com.delizarov.fcalc.R
import com.delizarov.fcalc.mvc.CalculatorView

class CalcFragment : BaseFragment() {

    private val controller = CalculatorView.Controller()
    private lateinit var view: CalculatorView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_calculator, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.view = CalculatorView(controller, view)
        controller.attachView(this.view)
    }
}
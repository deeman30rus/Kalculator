package com.delizarov.core.mvc

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.delizarov.core.android.fragment.BaseFragment

abstract class MvcFragment : BaseFragment() {

    protected abstract val layoutResId: Int

    protected abstract val mvcContainers: MutableCollection<MvcContainer<*, *>>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(layoutResId, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        for (combination in mvcContainers) {

            combination.initialize()
        }
    }

    override fun onStop() {
        super.onStop()

        mvcContainers.forEach {
            it.view!!.onStop()
        }
    }

    override fun onPause() {
        super.onPause()

        mvcContainers.forEach {
            it.view!!.onPause()
        }
    }

    override fun onResume() {
        super.onResume()

        mvcContainers.forEach {
            it.view!!.onResume()
        }
    }

    override fun onStart() {
        super.onStart()

        mvcContainers.forEach {
            it.view!!.onStart()
        }
    }
}
package com.delizarov.core.mvc

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.delizarov.core.android.BaseFragment

abstract class MvcFragment<TView: MvcView, TController : MvcController<TView>> : BaseFragment() {

    protected abstract val layoutResId: Int

    protected lateinit var mvcViewFactory: MvcView.Factory<TView>
    protected lateinit var mvcControllerFactory: MvcController.Factory<TController>
    protected lateinit var mvcView: TView
    protected val mvcController: TController by lazy { mvcControllerFactory.create() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(layoutResId, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initMvcViewFactory()
        initMvcControllerFactory()

        mvcView = mvcViewFactory.create()
        mvcController.attachView(mvcView)
    }

    override fun onStop() {
        super.onStop()
        mvcView.onStop()
    }

    override fun onPause() {
        super.onPause()
        mvcView.onPause()
    }

    override fun onResume() {
        super.onResume()
        mvcView.onResume()
    }

    override fun onStart() {
        super.onStart()
        mvcView.onStart()
    }

    protected abstract fun initMvcViewFactory()
    protected abstract fun initMvcControllerFactory()
}
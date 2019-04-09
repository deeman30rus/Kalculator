package com.delizarov.core.mvc

class MvcCombination<TView : MvcView, TController : MvcController<TView>>(
    private val controllerFactory: MvcController.Factory<TController>,
    private val viewFactory: MvcView.Factory<TView>
) {

    var view: TView? = null
    private var controller: TController? = null

    fun initialize() {
        this.view = viewFactory.create()
        controller = controllerFactory.create()
    }

    fun combine() {
        this.view!!.listener = controller
        controller!!.attachView(this.view!!)
    }
}
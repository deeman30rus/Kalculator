package com.delizarov.core.mvc

/**
 * Class container for controller view pairing. Contains factories for controller and view.
 * Call [MvcContainer.initialize] when other objects available for mvc feature.
 * */
class MvcContainer<TView : MvcView, TController : MvcController<TView>>(
    private val controllerFactory: MvcController.Factory<TController>,
    private val viewFactory: MvcView.Factory<TView>
) {

    var view: TView? = null
    private var controller: TController? = null

    fun initialize() {

        this.view = viewFactory.create()
        controller = controllerFactory.create()
        this.view!!.listener = controller
        controller!!.attachView(this.view!!)
    }
}
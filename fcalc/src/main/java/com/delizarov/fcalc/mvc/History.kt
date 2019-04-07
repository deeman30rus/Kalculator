package com.delizarov.fcalc.mvc

import android.view.View
import com.delizarov.core.mvc.MvcController
import com.delizarov.core.mvc.MvcView

class HistoryMvcView(
    root: View,
    eventListener: EventListener
) : MvcView(root, eventListener) {

    interface EventListener : MvcView.EventListener

    class Controller : MvcController<HistoryMvcView>() {


        override fun attachView(view: HistoryMvcView) {

            // do all subscriptions here
        }

        override fun detachView() {

        }
    }
}
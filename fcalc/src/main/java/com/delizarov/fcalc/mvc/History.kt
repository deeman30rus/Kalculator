package com.delizarov.fcalc.mvc

import android.view.View
import com.delizarov.core.dataaccess.ObservableRepository
import com.delizarov.core.mvc.MvcController
import com.delizarov.core.mvc.MvcView
import com.delizarov.core.observable.Cancelable
import com.delizarov.domain.math.expression.Expression
import com.delizarov.fcalc.R
import com.delizarov.fcalc.repo.HistoryRepository
import com.delizarov.fcalc.ui.views.HistoryView

class HistoryMvcView(
    root: View
) : MvcView(root), ObservableRepository.CrudOperationsListener<Expression> {

    private val historyView = root.findViewById<HistoryView>(R.id.history_view)

    override fun onAdd(obj: Expression) = historyView.addItem(obj)

    interface EventListener : MvcView.EventListener

    class Controller : MvcController<HistoryMvcView>(), EventListener {

        private var subscription: Cancelable? = null

        private val historyRepo = HistoryRepository

        override fun attachView(view: HistoryMvcView) {

            subscription?.cancel()
            subscription = null
            subscription = historyRepo.subscribe(view)
        }

        override fun detachView() {

            subscription?.cancel()
        }
    }
}
package com.delizarov.fcalc.mvc

import com.delizarov.core.observable.Observable

class CalculationModel(
    expression: String
) : Observable<String>(expression)
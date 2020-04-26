package com.antogian.athena.entities


class ModEntry {
    var name: String? = null
    var index: Int = 0
    var halfCost = ArrayList<Double>()
    var cost = ArrayList<Double>()
    var isTaxable: Boolean = false

    constructor() {}

    constructor(name: String) {
        this.name = name
    }
}

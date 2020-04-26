package com.antogian.athena.dto.entities

class SizeDTO {
    var name: String? = null
    var index: Int = 0
    var selected = false
    var cost: Double = 0.toDouble()

    constructor() {}

    constructor(name: String, selected: Boolean, cost: Double) {
        this.name = name
        this.selected = selected
        this.cost = cost
    }
}


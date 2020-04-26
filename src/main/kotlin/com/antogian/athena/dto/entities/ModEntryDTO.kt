package com.antogian.athena.dto.entities

class ModEntryDTO {
    var name: String? = null
    var index: Int = 0
    var cost: DoubleArray? = null
    var halfCost: DoubleArray? = null
    var included = false
        set(included) {
            if (included) {
                this.selected = true
                this.cost = DoubleArray(5)
                this.halfCost = DoubleArray(5)
            }
            field = included
        }
    var selected = false
    var halfOption = "Full"
        set(halfOption) {
            if (halfOption.equals("Full", ignoreCase = true) ||
                    halfOption.equals("F1/2", ignoreCase = true) ||
                    halfOption.equals("S1/2", ignoreCase = true))
                field = halfOption
        }
    var qualifier = ""

    constructor() {
        name = ""
    }

    constructor(name: String) {
        this.name = name
    }

    fun getTotalCost(sizeIndex: Int): Double {
        var tempCost = cost!![sizeIndex]
        if (!this.halfOption.equals("Full", ignoreCase = true))
            tempCost = halfCost!![sizeIndex]
        if (qualifier.equals("Extra", ignoreCase = true))
            tempCost *= 2.0

        return tempCost
    }
}

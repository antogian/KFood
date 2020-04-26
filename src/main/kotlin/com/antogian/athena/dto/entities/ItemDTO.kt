package com.antogian.athena.dto.entities

import java.math.BigDecimal
import java.math.RoundingMode

class ItemDTO {
    var name: String? = null
    var id: String? = null
    var index: Int = 0
    var cost: Double = 0.toDouble()
    //return bd.doubleValue();
    var totalCost: Double = 0.toDouble()
        get() {
            var bd = BigDecimal(field.toString())
            bd = bd.setScale(2, RoundingMode.HALF_UP)

            return bd.toDouble()
        }

    var allSizes: List<SizeDTO>? = null
    var modifiers: List<ModifierDTO>? = null
    var freeModEntries: IntArray? = null

    fun calculateTotalCost() {
        var tempCost = cost
        var index = 1
        if (!(allSizes == null || allSizes!!.isEmpty())) {
            for (size in allSizes!!) {
                if (size.selected) {
                    tempCost = size.cost
                    index = size.index
                }
            }
        } else {
            tempCost = cost
        }
        if (!(modifiers == null || modifiers!!.isEmpty())) {
            for (modifier in modifiers!!) {
                if (!(modifier.entries == null || modifier.entries!!.isEmpty())) {
                    for (entry in modifier.entries!!) {
                        if (entry.selected) {
                            tempCost += entry.getTotalCost(index - 1)
                        }
                    }
                }
            }
        }
        this.totalCost = tempCost
    }
}

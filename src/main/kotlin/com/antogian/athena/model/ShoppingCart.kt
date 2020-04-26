package com.antogian.athena.model

import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component

import java.math.BigDecimal
import java.math.RoundingMode
import java.util.ArrayList

@Component
@Scope("session")
class ShoppingCart {
    private var entries: MutableList<CartEntry>? = null
    private var totalCost: Double = 0.toDouble()

    init {
        entries = ArrayList()
    }

    fun getEntries(): List<CartEntry>? {
        return entries
    }

    fun setEntries(entries: MutableList<CartEntry>) {
        this.entries = entries
    }

    fun getTotalCost(): Double {
        var bd = BigDecimal(totalCost.toString())
        bd = bd.setScale(2, RoundingMode.HALF_UP)

        //return bd.doubleValue();

        return bd.toDouble()
    }

    fun setTotalCost(totalCost: Double) {
        this.totalCost = totalCost
    }

    private fun calculateCost() {
        totalCost = 0.0
        for (entry in entries!!) {
            totalCost += entry.quantity * entry.item!!.totalCost
        }
    }

    fun addEntry(newEntry: CartEntry) {
        entries!!.add(newEntry)
        calculateCost()
    }

    fun removeEntryById(id: String) {
        for (i in entries!!.indices) {
            if (entries!![i].item!!.id.equals(id, ignoreCase = true)) {
                entries!!.removeAt(i)
                break
            }
        }
        calculateCost()
    }


}

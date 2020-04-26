package com.antogian.athena.entities

import java.util.ArrayList


class Item {
    var filename: String? = null
    var name: String? = null
    var index: Int = 0
    var cost: ArrayList<Double>? = null
    var size: Size? = null
    var modifiers: ArrayList<Modifier>? = null
    var freeModEntries: ArrayList<Int>? = null
    var inclusions1: ArrayList<Int>? = null
    var inclusions2: ArrayList<Int>? = null
    var inclusions3: ArrayList<Int>? = null
    var inclusions4: ArrayList<Int>? = null
    var inclusions5: ArrayList<Int>? = null
    var inclusions6: ArrayList<Int>? = null
    var requiredModEntries: ArrayList<Int>? = null
    var taxable: Boolean = false

    constructor() {}

    constructor(filename: String, name: String, cost: ArrayList<Double>, size: Size, modifiers: ArrayList<Modifier>,
                freeModEntries: ArrayList<Int>, requiredModEntries: ArrayList<Int>) {
        this.filename = filename
        this.name = name
        this.cost = cost
        this.size = size
        this.modifiers = modifiers
        this.freeModEntries = freeModEntries
        this.requiredModEntries = requiredModEntries
    }

    fun getSpecificInclusion(i: Int): List<Int>? {
        return if (i == 1)
            inclusions1
        else if (i == 2)
            inclusions2
        else if (i == 3)
            inclusions3
        else if (i == 4)
            inclusions4
        else if (i == 5)
            inclusions5
        else
            inclusions6
    }
}
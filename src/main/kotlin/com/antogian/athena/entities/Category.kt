package com.antogian.athena.entities

import java.util.ArrayList

class Category {
    var name: String? = null
    var index: Int = 0
    var allItems: ArrayList<Item>? = null

    constructor() {
        name = ""
        allItems = ArrayList()
    }

    constructor(name: String, allItems: ArrayList<Item>) {
        this.name = name
        this.allItems = ArrayList()
        this.allItems = allItems
    }
}

package com.antogian.athena.entities


class Modifier {
    var filename: String? = null
    var name: String? = null
    var entries= ArrayList<ModEntry>()
    var isPlatter = false
    var platterPrice = 0.0
    var halfEnabled = false
    var qualifiersEnabled = true

    constructor() {}

    constructor(filename: String, name: String, entries: ArrayList<ModEntry>, isPlatter: Boolean, platterPrice: Double,
                halfEnabled: Boolean, qualifiersEnabled: Boolean) {
        this.filename = filename
        this.name = name
        this.entries = entries
        this.isPlatter = isPlatter
        this.platterPrice = platterPrice
        this.halfEnabled = halfEnabled
        this.qualifiersEnabled = qualifiersEnabled
    }


}
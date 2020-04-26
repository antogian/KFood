package com.antogian.athena.entities


class Size {
    var filename: String? = null
    var names: List<String>? = null

    constructor() {}

    constructor(filename: String, names: List<String>) {
        this.filename = filename
        this.names = names
    }
}

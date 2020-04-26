package com.antogian.athena.dto.entities


class ModifierDTO {
    var name: String? = null
    var entries: ArrayList<ModEntryDTO>? = null
    var isPlatter = false
    var platterPrice = 0.0
    var halfEnabled = false
    var qualifiersEnabled = true
    var freeEntries: Int = 0
    var requiredEntries: Int = 0
}

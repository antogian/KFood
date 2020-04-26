package com.antogian.athena.dto

import com.antogian.athena.entities.Item
import com.antogian.athena.dto.entities.ModEntryDTO
import com.antogian.athena.dto.entities.ModifierDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service


@Service
class ModifierConverter

@Autowired
constructor(private val modEntryConverter: ModEntryConverter) {

    fun getModifiers(item: Item): List<ModifierDTO> {
        val allModifiers = ArrayList<ModifierDTO>()

        for (i in item.modifiers!!.indices) {
            val modifierDto = ModifierDTO()

            modifierDto.halfEnabled = item.modifiers!![i].halfEnabled
            modifierDto.name = item.modifiers!![i].name
            modifierDto.isPlatter = item.modifiers!![i].isPlatter
            modifierDto.platterPrice = item.modifiers!![i].platterPrice
            modifierDto.qualifiersEnabled = item.modifiers!![i].qualifiersEnabled

            var allEntries = ArrayList<ModEntryDTO>()
            if (item.modifiers!![i].entries == null || item.modifiers!![i].entries.isEmpty()) {
                modifierDto.entries = allEntries
            }
            else {
                allEntries = item.getSpecificInclusion(i + 1)?.let {
                    modEntryConverter.getModEntries(item.modifiers!![i], it) as ArrayList<ModEntryDTO>
                }!!
                modifierDto.entries = allEntries
            }
            if (item.requiredModEntries!![i] > 0) {
                modifierDto.freeEntries = item.freeModEntries!![i]
                modifierDto.requiredEntries = item.requiredModEntries!![i]
            }

            allModifiers.add(modifierDto)
        }

        return allModifiers
    }

    fun getValues(mod: ModifierDTO): ModifierDTO {
        val newModifier = ModifierDTO()

        newModifier.name = mod.name
        newModifier.halfEnabled = mod.halfEnabled
        newModifier.isPlatter = mod.isPlatter
        newModifier.platterPrice = mod.platterPrice
        newModifier.qualifiersEnabled = mod.qualifiersEnabled
        newModifier.freeEntries = mod.freeEntries
        newModifier.requiredEntries = mod.requiredEntries

        val allEntries = ArrayList<ModEntryDTO>()
        for (entryDto in mod.entries!!) {
            allEntries.add(modEntryConverter.getValues(entryDto, mod.halfEnabled))
        }
        newModifier.entries = allEntries

        return newModifier
    }
}
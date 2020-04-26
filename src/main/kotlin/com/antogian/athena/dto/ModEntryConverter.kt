package com.antogian.athena.dto

import com.antogian.athena.entities.Modifier
import com.antogian.athena.dto.entities.ModEntryDTO
import org.springframework.stereotype.Service
import java.util.ArrayList

@Service
class ModEntryConverter {
    fun getModEntries(modifier: Modifier, inclusions: List<Int>): List<ModEntryDTO> {
        val allEntries = ArrayList<ModEntryDTO>()

        for (modEntry in modifier.entries) {
            val entryDto = ModEntryDTO()

            entryDto.name = modEntry.name
            entryDto.index = modEntry.index

            entryDto.included = getInclusion(entryDto.index, inclusions)

            if (entryDto.included)
                entryDto.cost = DoubleArray(5)
            else {
                val cost = DoubleArray(5)
                for (i in modEntry.cost.indices) {
                    if(modEntry.cost[i] != null) {
                        cost[i] = modEntry.cost[i]
                    }
                }
                entryDto.cost = cost
            }

            if (modifier.halfEnabled) {
                if (entryDto.included)
                    entryDto.halfCost = DoubleArray(5)
                else {
                    val halfCost = DoubleArray(5)
                    if(modEntry.halfCost != null && modEntry.halfCost.isNotEmpty()){
                        for (i in modEntry.halfCost.indices) {
                            if(modEntry.halfCost[i] != null){
                                halfCost[i] = modEntry.halfCost[i]
                            }
                        }
                    }
                    entryDto.halfCost = halfCost
                }
            }

            allEntries.add(entryDto)
        }

        return allEntries
    }

    private fun getInclusion(index: Int, inclusions: List<Int>): Boolean {
        for (i in inclusions.indices) {
            if (inclusions[i] == index) {
                return true
            }
        }
        return false
    }

    fun getValues(modEntry: ModEntryDTO, isHalfEnabled: Boolean): ModEntryDTO {
        val newEntry = ModEntryDTO()
        newEntry.name = modEntry.name

        val cost = DoubleArray(5)
        for (i in modEntry.cost!!.indices) {
            cost[i] = modEntry.cost!![i]
        }
        newEntry.cost = cost

        if (isHalfEnabled) {
            val halfCost = DoubleArray(5)
            for (i in modEntry.halfCost?.indices!!) {
                halfCost[i] = modEntry.halfCost?.get(i)!!
            }
            newEntry.halfCost = halfCost
        }

        newEntry.index = modEntry.index
        newEntry.selected = modEntry.selected
        newEntry.included = modEntry.included

        return newEntry
    }
}

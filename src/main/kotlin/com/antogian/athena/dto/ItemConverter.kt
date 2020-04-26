package com.antogian.athena.dto

import com.antogian.athena.entities.Category
import com.antogian.athena.dto.entities.ItemDTO
import com.antogian.athena.dto.entities.ModifierDTO
import com.antogian.athena.dto.entities.SizeDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class ItemConverter

@Autowired
constructor(private val modifierConverter: ModifierConverter, private val sizeConverter: SizeConverter) {

    internal fun getItems(category: Category): List<ItemDTO> {
        val allItems = ArrayList<ItemDTO>()

        for (item in category.allItems!!) {
            val itemDto = ItemDTO()
            //----------------------------------------------------------------------------------------------------------
            itemDto.name = item.name
            itemDto.id = UUID.randomUUID().toString()
            itemDto.index = item.index
            itemDto.freeModEntries = item.freeModEntries?.toIntArray()
            //----------------------------------------------------------------------------------------------------------
            var allSizes: List<SizeDTO> = ArrayList<SizeDTO>()
            if (item.size == null) {
                itemDto.allSizes = allSizes
            } else {
                allSizes = sizeConverter.getSizes(item.size!!)
                for (i in allSizes.indices) {
                    allSizes[i].cost = item.cost!![i]
                }
                allSizes[0].selected = true

                itemDto.allSizes = allSizes
            }

            itemDto.cost = item.cost!![0]
            itemDto.totalCost = item.cost!![0]
            //----------------------------------------------------------------------------------------------------------
            var allModifiers: List<ModifierDTO> = ArrayList<ModifierDTO>()
            if (item.modifiers == null || item.modifiers!!.isEmpty()) {
                itemDto.modifiers = allModifiers
            } else {
                allModifiers = modifierConverter.getModifiers(item)
                itemDto.modifiers = allModifiers
            }
            //----------------------------------------------------------------------------------------------------------
            allItems.add(itemDto)
        }
        return allItems
    }

    fun getItemByValue(item: ItemDTO): ItemDTO {
        val newItem = ItemDTO()

        newItem.name = item.name
        newItem.index = item.index
        newItem.cost = item.cost
        newItem.totalCost = item.totalCost
        newItem.freeModEntries = item.freeModEntries

        val allModifiers = ArrayList<ModifierDTO>()
        for (mod in item.modifiers!!) {
            allModifiers.add(modifierConverter.getValues(mod))
        }
        newItem.modifiers = allModifiers

        val allSizes = ArrayList<SizeDTO>()
        if (!(item.allSizes == null || item.allSizes!!.isEmpty())) {
            for (size in item.allSizes!!) {
                val newSize = SizeDTO()
                newSize.name = size.name
                newSize.index = size.index
                newSize.selected = size.selected
                newSize.cost = size.cost
                allSizes.add(newSize)
            }
        }
        newItem.allSizes = allSizes

        return newItem
    }

}

package com.antogian.athena.services

import com.antogian.athena.dto.CategoryConverter
import com.antogian.athena.dto.entities.CategoryDTO
import com.antogian.athena.dto.entities.ItemDTO
import com.antogian.athena.dto.entities.ModEntryDTO
import com.antogian.athena.dto.entities.ModifierDTO
import com.antogian.athena.model.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.ArrayList

@Service
class MenuService

@Autowired
constructor(private val categoryConverter: CategoryConverter) {

    fun getItemById(allCats: List<CategoryDTO>, id: String): ItemDTO {
        for (i in allCats.indices) {
            for (j in allCats[i].allItems!!.indices) {
                if (id.equals(allCats[i].allItems!![j].id, ignoreCase = true)) {
                    return allCats[i].allItems!![j]
                }
            }
        }
        return ItemDTO()
    }

    fun removeItemById(shoppingCart: ShoppingCart, name: String) {
        shoppingCart.removeEntryById(name)
    }

    fun checkModifiers(item: ItemDTO, modifiers: List<ModifierDTO>) {
        val selectedEntries = ArrayList<ModEntryDTO>()
        for (mod in modifiers) {
            for (entry in mod.entries!!) {
                if (entry.selected)
                    selectedEntries.add(entry)
            }
        }
        for (mod in item.modifiers!!) {
            for (entry in mod.entries!!) {
                entry.selected = false
                for (selectedEntry in selectedEntries) {
                    if (entry.name.equals(selectedEntry.name, ignoreCase = true)) {
                        entry.selected = selectedEntry.selected
                        entry.halfOption = selectedEntry.halfOption
                        entry.qualifier = selectedEntry.qualifier
                    }
                }
            }
        }
    }

    fun checkSizes(item: ItemDTO, name: String?) {
        if(item.allSizes == null || item.allSizes!!.isEmpty())
            return
            for (size in item.allSizes!!) {
                size.selected = size.name.equals(name, ignoreCase = true)
            }
    }

    fun getEntryFromCart(shoppingCart: ShoppingCart, id: String): CartEntry {
        for (entry in shoppingCart.getEntries()!!) {
            if (id.equals(entry.item?.id, ignoreCase = true)) {
                return entry
            }
        }
        return CartEntry()
    }


}

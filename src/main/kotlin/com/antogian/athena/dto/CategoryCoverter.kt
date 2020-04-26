package com.antogian.athena.dto

import com.antogian.athena.dao.CategoryDAO
import com.antogian.athena.dto.entities.CategoryDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import java.util.ArrayList

@Service
class CategoryCoverter

@Autowired
constructor(private val catDao: CategoryDAO, private val itemConverter: ItemConverter) {

    val allCats: List<CategoryDTO>
        get() {
            val allCats = catDao.parseCategories()
            val allCatsDTO = ArrayList<CategoryDTO>()
            for (cat in allCats) {
                val catDto = CategoryDTO()
                catDto.name = cat.name
                catDto.index = cat.index
                val allItems = itemConverter.getItems(cat)
                if (cat.allItems == null || cat.allItems!!.isEmpty())
                    continue
                catDto.allItems = allItems

                allCatsDTO.add(catDto)
            }

            return allCatsDTO
        }

}
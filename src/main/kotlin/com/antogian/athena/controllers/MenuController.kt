package com.antogian.athena.controllers

import com.antogian.athena.dto.CategoryConverter
import com.antogian.athena.dto.ItemConverter
import com.antogian.athena.dto.entities.CategoryDTO
import com.antogian.athena.dto.entities.ItemDTO
import com.antogian.athena.model.ShoppingCart
import com.antogian.athena.services.MenuService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Lazy
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import java.util.ArrayList

@RestController
class MenuController
    @Autowired
    @Lazy
    constructor(private val categoryConverter: CategoryConverter){

    @RequestMapping("/api/menu", method = [RequestMethod.GET])
    fun getMenu(): List<CategoryDTO>{
        return categoryConverter.allCats
    }
}
package com.antogian.athena.controllers

import com.antogian.athena.entities.Order
import com.antogian.athena.model.CartEntry
import com.antogian.athena.dto.entities.CategoryDTO
import com.antogian.athena.model.ShoppingCart
import com.antogian.athena.dto.CategoryCoverter
import com.antogian.athena.services.OrderService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

import java.util.ArrayList

@RestController
class ApiController

@Autowired
constructor(private val orderService: OrderService) {
    private var allCats: List<CategoryDTO>? = null

    @Autowired
    private val categoryCoverter: CategoryCoverter? = null

    val api: List<Order>
        @RequestMapping("/api")
        get() {
            initialize()

            val allShoppingCarts = ArrayList<ShoppingCart>()
            val cart = ShoppingCart()

            for (i in 0..9) {
                val cartEntry = CartEntry()
                cartEntry.item = allCats!![i + 1].allItems?.get(i + 2)
                cartEntry.quantity = 1
                cart.addEntry(cartEntry)
                allShoppingCarts.add(cart)
            }

            return orderService.allOrders
        }

    private fun initialize() {
        allCats = ArrayList<CategoryDTO>()
        try {
            allCats = categoryCoverter!!.allCats
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
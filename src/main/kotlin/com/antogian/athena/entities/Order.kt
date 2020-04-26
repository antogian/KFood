package com.antogian.athena.entities

import com.antogian.athena.model.ShoppingCart

class Order {
    var user: User? = null
    var cart: ShoppingCart? = null
    var isPaid: Boolean = false

    init {
        user = User()
        cart = ShoppingCart()
    }
}

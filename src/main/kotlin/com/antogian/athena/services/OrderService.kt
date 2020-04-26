package com.antogian.athena.services

import com.antogian.athena.dao.OrderDAO
import com.antogian.athena.entities.Order
import com.antogian.athena.entities.User
import com.antogian.athena.model.ShoppingCart
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class OrderService @Autowired
constructor(private val orderDAO: OrderDAO) {

    val allOrders: List<Order>
        get() = orderDAO.getAllOrders()

    fun insertOrder(newOrder: Order) {
        orderDAO.insertOrder(newOrder)
    }

    fun setOrderInfo(currentUser: User, cart: ShoppingCart) {
        val newOrder = Order()
        newOrder.user = currentUser
        newOrder.cart = cart
    }
}

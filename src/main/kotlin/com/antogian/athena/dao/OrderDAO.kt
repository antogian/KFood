package com.antogian.athena.dao

import com.antogian.athena.entities.Category
import com.antogian.athena.entities.Order
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import org.springframework.stereotype.Repository

import java.io.FileWriter
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.util.ArrayList


@Repository
class OrderDAO {
    internal var allOrders: MutableList<Order> = ArrayList<Order>()

    fun insertOrder(newOrder: Order) {
        //        Gson gson = new Gson();
        //        ClassLoader classLoader = getClass().getClassLoader();
        //        InputStream in = classLoader.getResourceAsStream("data/orders.json");
        //        JsonReader reader = new JsonReader(new InputStreamReader(in));
        //        JsonWriter writer = new JsonWriter();
        //        List<Category> allCategories = gson.fromJson(reader, new TypeToken<List<Category>>(){}.getType());
        //        try
        //        {
        //            gson.toJson(newOrder, "data/orders.json"); //error
        //        }
        //        catch(Exception e)
        //        {
        //            e.printStackTrace();
        //        }
        allOrders.add(newOrder)
    }

    fun getAllOrders(): List<Order> {
        return this.allOrders
    }
}

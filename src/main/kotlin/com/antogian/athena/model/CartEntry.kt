package com.antogian.athena.model

import com.antogian.athena.dto.entities.ItemDTO
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component

@Component
@Scope("session")
class CartEntry {
    final var quantity = 1
    final var item: ItemDTO? = null

    init {
        item = ItemDTO()
        quantity = 1
    }

}

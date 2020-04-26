package com.antogian.athena.dto

import com.antogian.athena.entities.Size
import com.antogian.athena.dto.entities.SizeDTO
import org.springframework.stereotype.Service

import java.util.ArrayList

@Service
class SizeConverter {
    fun getSizes(size: Size): List<SizeDTO> {
        val allSizes = ArrayList<SizeDTO>()

        if (size.names == null || size.names!!.isEmpty()) {
            return allSizes
        }
        var index = 1
        for (name in size.names!!) {
            val newSize = SizeDTO()
            newSize.index = index
            newSize.name = name
            allSizes.add(newSize)
            index++
        }

        return allSizes
    }
}

package com.carles.carleskotlin.poi.data

import com.carles.carleskotlin.common.test.createEmptyPoiListResponseDto
import com.carles.carleskotlin.common.test.createPoiDetailResponseDto
import com.carles.carleskotlin.common.test.createPoiListResponseDto
import com.carles.carleskotlin.common.test.createPoiResponseDto
import com.carles.carleskotlin.poi.domain.Poi
import com.carles.carleskotlin.poi.model.toModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.util.*

class DtoTest {

    @Test
    fun poiListResponseDto_toModel() {
        assertEquals(Collections.emptyList<Poi>(), createEmptyPoiListResponseDto().toModel())

        val dto = createPoiListResponseDto()
        assertTrue(with(dto.toModel()) { size == dto.list!!.size && get(0).id == dto.list!!.get(0).id })
    }

    @Test
    fun poiResponseDto_toModel() {
        val dto = createPoiResponseDto()
        assertTrue(with(dto.toModel()) { id == dto.id && title == dto.title && geocoordinates == dto.geocoordinates })
    }

    @Test
    fun poiDetailResponseDto_toModel() {
        val dto = createPoiDetailResponseDto()
        assertTrue(with(dto.toModel()) { id == dto.id && transport == dto.transport && email == dto.email && phone == dto.phone })

        val dtoWithNulls = dto.copy(transport = "", email = "null", phone = "undefined")
        assertTrue(with(dtoWithNulls.toModel()) { transport == null && email == null && phone == null })
    }
}
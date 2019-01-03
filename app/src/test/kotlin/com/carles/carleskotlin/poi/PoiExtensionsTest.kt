package com.carles.carleskotlin.poi

import com.carles.carleskotlin.createEmptyPoiListResponseDto
import com.carles.carleskotlin.createPoi
import com.carles.carleskotlin.createPoiListResponseDto
import com.carles.carleskotlin.createPoiResponseDto
import com.carles.carleskotlin.poi.datasource.entity.PoiRealmObject
import com.carles.carleskotlin.poi.model.Poi
import org.junit.Assert.*
import org.junit.Test
import java.util.Collections.emptyList

class PoiExtensionsTest {

    @Test
    fun poiListResponseDto_toModel() {
        assertEquals(emptyList<Poi>(), createEmptyPoiListResponseDto().toModel())

        val dto = createPoiListResponseDto()
        assertTrue(with(dto.toModel()) {
            size == dto.list!!.size && get(0).id == dto.list!!.get(0).id
        })
    }

    @Test
    fun poiResponseDto_toModel() {
        val dto = createPoiResponseDto()
        assertTrue(with(dto.toModel()) {
            id == dto.id && title == dto.title && transport == dto.transport && email == dto.email && phone == dto.phone
        })

        dto.transport = ""; assertNull(dto.toModel().transport)
        dto.email = "null"; assertNull(dto.toModel().email)
        dto.phone = "undefined"; assertNull(dto.toModel().phone)
    }

    @Test
    fun poi_toRealmObject() {
        val poi = createPoi(System.currentTimeMillis().toString())
        assertEquals(poi.id, poi.toRealmObject().id)
    }

    @Test
    fun poiRealmObject_toModel() {
        val poiRealmObject = PoiRealmObject(id = System.currentTimeMillis().toString())
        assertEquals(poiRealmObject.id, poiRealmObject.toModel().id)
    }
}
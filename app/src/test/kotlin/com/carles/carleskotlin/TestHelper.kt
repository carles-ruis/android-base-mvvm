package com.carles.carleskotlin

import com.carles.carleskotlin.poi.datasource.dto.PoiListResponseDto
import com.carles.carleskotlin.poi.datasource.dto.PoiResponseDto
import com.carles.carleskotlin.poi.model.Poi

internal fun createPoi(id: String) = Poi(id = id)

internal fun createPoiList(vararg ids: String) = mutableListOf<Poi>().apply {
    for (id in ids) add(createPoi(id))
}

internal fun createEmptyPoiListResponseDto() = PoiListResponseDto().apply { list = listOf() }

internal fun createPoiListResponseDto() = PoiListResponseDto().apply {
    list = listOf(
        createPoiResponseDto(),
        createPoiResponseDto()
    )
}

internal fun createPoiResponseDto() = PoiResponseDto(id = System.currentTimeMillis().toString()).apply {
    title = "the_title"
    transport = "the_transport"
    email = "the_email"
    phone = "the_phone"
}






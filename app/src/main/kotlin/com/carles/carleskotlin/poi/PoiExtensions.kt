package com.carles.carleskotlin.poi

import com.carles.carleskotlin.poi.datasource.dto.PoiListResponseDto
import com.carles.carleskotlin.poi.datasource.dto.PoiResponseDto
import com.carles.carleskotlin.poi.model.Poi

fun PoiListResponseDto.toModel(): List<Poi> =
    if (list == null) listOf() else mutableListOf<Poi>().apply {
        for (dtoItem in list!!) add(dtoItem.toModel())
    }

fun PoiResponseDto.toModel(): Poi =
    Poi(id, title, address, sanitize(transport), description, sanitize(email), sanitize(phone), geocoordinates)

private fun sanitize(source: String?): String? =
    if (source == null || source.isEmpty() || source == "null" || source == "undefined") null else source
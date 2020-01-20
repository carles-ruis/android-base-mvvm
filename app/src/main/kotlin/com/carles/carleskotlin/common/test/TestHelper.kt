package com.carles.carleskotlin.common.test

import com.carles.carleskotlin.poi.data.PoiDetailResponseDto
import com.carles.carleskotlin.poi.data.PoiListResponseDto
import com.carles.carleskotlin.poi.data.PoiResponseDto
import com.carles.carleskotlin.poi.model.Poi
import com.carles.carleskotlin.poi.model.PoiDetail

const val POI_ID = "1"

private fun createPoi(id: String) = Poi(id, "the title", "the geocoordinates")

fun createPoiList() = mutableListOf<Poi>().apply {
    for (id in 1..2) add(createPoi(id.toString()))
}.toList()

fun createPoiDetail() = PoiDetail(
        id = POI_ID,
        title = "the_title",
        transport = "the_transport",
        email = "the_email",
        phone = "the_phone"
)

fun createEmptyPoiListResponseDto() = PoiListResponseDto(listOf())

fun createPoiListResponseDto() = PoiListResponseDto(listOf(createPoiResponseDto(POI_ID), createPoiResponseDto("2")))

fun createPoiResponseDto(id: String = POI_ID) = PoiResponseDto(id, "the title", "the geocoordinates")

fun createPoiDetailResponseDto() = PoiDetailResponseDto(id = POI_ID,
        title = "the_title",
        transport = "the_transport",
        email = "the_email",
        phone = "the_phone")

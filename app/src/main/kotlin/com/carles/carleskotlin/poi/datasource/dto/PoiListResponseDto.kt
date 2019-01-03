package com.carles.carleskotlin.poi.datasource.dto

import com.google.gson.annotations.SerializedName

data class PoiListResponseDto (
    @SerializedName("list") var list: List<PoiResponseDto>? = null)
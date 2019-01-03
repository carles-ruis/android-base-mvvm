package com.carles.carleskotlin.poi.datasource.dto

import com.google.gson.annotations.SerializedName

data class PoiResponseDto(
    @SerializedName("id") var id: String,
    var title: String? = null,
    var address: String? = null,
    var transport: String? = null,
    var description: String? = null,
    var email: String? = null,
    var phone: String? = null,
    var geocoordinates: String? = null
)
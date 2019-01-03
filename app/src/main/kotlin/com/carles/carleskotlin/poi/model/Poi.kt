package com.carles.carleskotlin.poi.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class Poi(
    @PrimaryKey var id: String,
    var title: String?,
    var address: String?,
    var transport: String?,
    var description: String?,
    var email: String?,
    var phone: String?,
    var geocoordinates: String?
)
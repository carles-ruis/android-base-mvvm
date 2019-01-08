package com.carles.carleskotlin.poi.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class Poi(
    @PrimaryKey var id: String,
    var title: String? = null,
    var address: String? = null,
    var transport: String? = null,
    var description: String? = null,
    var email: String? = null,
    var phone: String? = null,
    var geocoordinates: String? = null
)
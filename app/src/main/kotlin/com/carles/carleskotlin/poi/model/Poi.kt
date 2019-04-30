package com.carles.carleskotlin.poi.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "poi")
data class Poi(@PrimaryKey var id: String, var title: String, var geocoordinates: String)

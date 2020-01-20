package com.carles.carleskotlin.poi.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "poi")
data class Poi(@PrimaryKey var id: String, var title: String, var geocoordinates: String)

package com.carles.carleskotlin

import androidx.room.Database
import androidx.room.RoomDatabase
import com.carles.carleskotlin.poi.data.PoiDao
import com.carles.carleskotlin.poi.model.Poi
import com.carles.carleskotlin.poi.model.PoiDetail

@Database(entities = arrayOf(PoiDetail::class, Poi::class), version = 1)
abstract class KotlinDatabase : RoomDatabase() {

    abstract fun poiDao(): PoiDao

}
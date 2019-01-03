package com.carles.carleskotlin

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.carles.carleskotlin.poi.datasource.PoiDao
import com.carles.carleskotlin.poi.model.Poi

@Database(entities = arrayOf(Poi::class), version = 1)
abstract class KotlinDatabase : RoomDatabase() {
    abstract fun PoiDao(): PoiDao
}
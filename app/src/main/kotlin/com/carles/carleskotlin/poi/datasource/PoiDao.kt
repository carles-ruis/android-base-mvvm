package com.carles.carleskotlin.poi.datasource

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.carles.carleskotlin.poi.model.Poi

@Dao
interface PoiDao {
    @Query("SELECT * from poi where id=:id LIMIT 1")
    fun loadPoiById(id: String): Poi

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPoi(poi: Poi) : Long

    @Query("DELETE from poi where id=:id")
    fun deletePoi(id: String) : Int

}
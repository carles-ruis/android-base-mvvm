package com.carles.carleskotlin.poi.data

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query
import com.carles.carleskotlin.poi.model.Poi
import com.carles.carleskotlin.poi.model.PoiDetail

@Dao
interface PoiDao {
    @Query("SELECT * from poi")
    fun loadPois() : LiveData<List<Poi>>

    @Insert(onConflict = REPLACE)
    fun insertPois(poiList: List<Poi>) : List<Long>

    @Query("DELETE from poi")
    fun deletePois() : Int

    @Query("SELECT * from poi_detail where id=:id LIMIT 1")
    fun loadPoiById(id: String): LiveData<PoiDetail>

    @Insert(onConflict = REPLACE)
    fun insertPoi(poi: PoiDetail) : Long

    @Query("DELETE from poi_detail where id=:id")
    fun deletePoi(id: String) : Int

}
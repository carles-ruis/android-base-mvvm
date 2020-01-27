package com.carles.carleskotlin.poi.model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.carles.carleskotlin.poi.domain.Poi
import com.carles.carleskotlin.poi.domain.PoiDetail

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
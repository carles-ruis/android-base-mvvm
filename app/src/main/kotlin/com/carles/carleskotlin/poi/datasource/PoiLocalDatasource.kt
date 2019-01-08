package com.carles.carleskotlin.poi.datasource

import android.content.SharedPreferences
import com.carles.carleskotlin.common.data.datasource.BaseLocalDatasource
import com.carles.carleskotlin.common.setCacheExpirationTime
import com.carles.carleskotlin.poi.model.Poi
import io.reactivex.Maybe

class PoiLocalDatasource(private val dao: PoiDao, sharedPreferences: SharedPreferences) : BaseLocalDatasource(sharedPreferences) {

    fun getPoiList(): Maybe<List<Poi>> = Maybe.empty()

    fun getPoiDetail(id: String): Maybe<Poi> = Maybe.defer {
        if (isExpired(Poi::class.toString(), id)) {
            dao.deletePoi(id)
            Maybe.empty<Poi>()
        } else {
            dao.loadPoiById(id)
        }
    }

    fun persist(poi: Poi) {
        dao.insertPoi(poi)
        sharedPreferences.setCacheExpirationTime(Poi::class.toString(), poi.id, calculateCacheExpirationTime())
    }
}
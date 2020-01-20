package com.carles.carleskotlin.poi.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import android.content.SharedPreferences
import com.carles.carleskotlin.AppExecutors
import com.carles.carleskotlin.common.model.*
import com.carles.carleskotlin.poi.data.*

class PoiRepository(
        val appExecutors: AppExecutors,
        val sharedPreferences: SharedPreferences,
        val poiDao: PoiDao, val poiService: PoiService) {

    fun getPoiList(): LiveData<Resource<List<Poi>>> = object : NetworkBoundResource<List<Poi>, PoiListResponseDto>(appExecutors) {
        override fun saveCallResult(item: PoiListResponseDto) {
            poiDao.deletePois()
            poiDao.insertPois(item.toModel())
        }

        // always getting new data from server
        override fun shouldFetch(data: List<Poi>?) = true

        override fun loadFromDb() = poiDao.loadPois()

        override fun createCall() = poiService.getPoiList()

    }.asLiveData()

    fun getPoiDetail(id: String): LiveData<Resource<PoiDetail>> = object : NetworkBoundResource<PoiDetail, PoiDetailResponseDto>
    (appExecutors) {
        override fun saveCallResult(item: PoiDetailResponseDto) {
            poiDao.deletePoi(item.id)
            val poiDetail = item.toModel()
            sharedPreferences.setCacheExpirationTime(poiDetail.cacheId, poiDetail.id, calculateCacheExpirationTime())
            poiDao.insertPoi(poiDetail)
        }

        // obtain from server if no data or expired data locally
        override fun shouldFetch(data: PoiDetail?) = data == null || isExpired(data.cacheId, data.id)

        override fun loadFromDb() = poiDao.loadPoiById(id)

        override fun createCall() = poiService.getPoiDetail(id)

    }.asLiveData()

    private val EXPIRE_TIME = 1000 * 60

    private fun calculateCacheExpirationTime() = System.currentTimeMillis() + EXPIRE_TIME

    private fun isExpired(className: String, itemId: String): Boolean =
            sharedPreferences.getCacheExpirationTime(className, itemId) < System.currentTimeMillis()
}
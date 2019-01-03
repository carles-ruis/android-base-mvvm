package com.carles.carleskotlin.poi.datasource

import com.carles.carleskotlin.common.data.datasource.BaseCloudDatasource
import com.carles.carleskotlin.poi.model.Poi
import com.carles.carleskotlin.poi.toModel
import io.reactivex.Single

class PoiCloudDatasource(val poiLocalDatasource: PoiLocalDatasource, val poiService: PoiService) : BaseCloudDatasource() {

    fun getPoiList(): Single<List<Poi>> = poiService.getPoiList().map { it.toModel() }

    fun getPoiDetail(id: String): Single<Poi> =
            poiService.getPoiDetail(id).map { it.toModel() }.doOnSuccess { poiLocalDatasource.persist(it) }

}
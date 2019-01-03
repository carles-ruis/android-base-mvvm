package com.carles.carleskotlin.poi.repository

import com.carles.carleskotlin.poi.datasource.PoiCloudDatasource
import com.carles.carleskotlin.poi.datasource.PoiLocalDatasource
import com.carles.carleskotlin.poi.model.Poi
import io.reactivex.Maybe
import io.reactivex.Single

class PoiRepository(val poiLocalDatasource: PoiLocalDatasource, val poiCloudDatasource: PoiCloudDatasource) {

    fun getPoiList(): Single<List<Poi>> =
            Maybe.concat(poiLocalDatasource.getPoiList(), poiCloudDatasource.getPoiList().toMaybe()).firstOrError();

    fun getPoiDetail(id: String): Single<Poi> =
            Maybe.concat(poiLocalDatasource.getPoiDetail(id), poiCloudDatasource.getPoiDetail(id).toMaybe()).firstOrError();
}
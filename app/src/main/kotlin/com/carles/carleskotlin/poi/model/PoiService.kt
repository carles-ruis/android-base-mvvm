package com.carles.carleskotlin.poi.model

import androidx.lifecycle.LiveData
import com.carles.carleskotlin.common.model.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface PoiService {

    @GET("points")
    fun getPoiList(): LiveData<ApiResponse<PoiListResponseDto>>

    @GET("points/{id}")
    fun getPoiDetail(@Path("id") id: String): LiveData<ApiResponse<PoiDetailResponseDto>>

}
package com.carles.carleskotlin.poi.model

import android.content.SharedPreferences
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.carles.carleskotlin.*
import com.carles.carleskotlin.common.model.ApiResponse
import com.carles.carleskotlin.common.model.getCacheExpirationTime
import com.carles.carleskotlin.common.viewmodel.Resource
import com.carles.carleskotlin.poi.domain.Poi
import com.carles.carleskotlin.poi.domain.PoiDetail
import io.mockk.*
import org.junit.Rule
import org.junit.Test
import retrofit2.Response
import java.util.concurrent.Executor

class PoiRepositoryTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    val FUTURE: Long
    val PAST: Long
    val instantExecutor = Executor { it.run() }
    val sharedPreferences: SharedPreferences
    val dao: PoiDao = mockk(relaxed = true)
    val service: PoiService = mockk()

    init {
        FUTURE = System.currentTimeMillis() + 24L * 60 * 60 * 1000
        PAST = System.currentTimeMillis() - 24L * 60 * 60 * 1000
        mockkStatic("com.carles.carleskotlin.common.model.DataExtensionsKt")
        sharedPreferences = mockk(relaxed = true)
    }

    val repository = PoiRepository(AppExecutors(instantExecutor, instantExecutor, instantExecutor), sharedPreferences, dao, service)

    @Test
    fun getPoiList_network() {
        val model = poiListResponseDto.toModel()
        val localData = MutableLiveData<List<Poi>>()
        val networkData = MutableLiveData<ApiResponse<PoiListResponseDto>>().apply {
            value = ApiResponse.create(Response.success(poiListResponseDto))
        } as LiveData<ApiResponse<PoiListResponseDto>>
        every { dao.loadPois() } returns localData
        every { service.getPoiList() } returns networkData

        val observer: Observer<Resource<List<Poi>>> = mockk(relaxed = true)
        repository.getPoiList().observeForever(observer)

        // first : loadFromDb()
        verifyAll { dao.loadPois() }
        verify { service wasNot called }

        // get empty data from the local database
        localData.postValue(null)

        // verify that fetchFromNetwork() and saveCallResult() were called
        verifyAll {
            service.getPoiList()
            dao.loadPois()
            dao.insertPois(model)
            dao.deletePois()
        }
    }

    @Test
    fun getPoiDetail_dataNotExpiredShouldloadFromDb() {
        val localData = MutableLiveData<PoiDetail>().apply { value = poiDetail }
        every { dao.loadPoiById(POI_ID) } returns localData
        every { sharedPreferences.getCacheExpirationTime(any(), any()) } returns FUTURE

        val observer: Observer<Resource<PoiDetail>> = mockk(relaxed = true)
        repository.getPoiDetail(POI_ID).observeForever(observer)
        localData.postValue(null)

        verifyAll { dao.loadPoiById(POI_ID) }
        verify { service wasNot called }
        verify(exactly = 0) { dao.insertPoi(any()) }
    }

    @Test
    fun getPoiDetail_dataNullShouldFetchFromNetwork() {
        val localData = MutableLiveData<PoiDetail>().apply { value = null }
        val networkData = MutableLiveData<ApiResponse<PoiDetailResponseDto>>().apply {
            value = ApiResponse.create(Response.success(poiDetailResponseDto))
        }

        every { dao.loadPoiById(POI_ID) } returns localData
        every { service.getPoiDetail(POI_ID) } returns networkData

        val observer: Observer<Resource<PoiDetail>> = mockk(relaxed = true)
        repository.getPoiDetail(POI_ID).observeForever(observer)
        localData.postValue(null)

        verifyAll {
            dao.loadPoiById(POI_ID)
            service.getPoiDetail(POI_ID)
            dao.deletePoi(POI_ID)
            dao.insertPoi(any())
        }
    }

    @Test
    fun getPoiDetail_dataExpiredShouldFetchFromNetwork() {
        val localData = MutableLiveData<PoiDetail>().apply { value = poiDetail }
        val networkData = MutableLiveData<ApiResponse<PoiDetailResponseDto>>().apply {
            value = ApiResponse.create(Response.success(poiDetailResponseDto))
        }
        every { dao.loadPoiById(POI_ID) } returns localData
        every { service.getPoiDetail(POI_ID) } returns networkData
        every { sharedPreferences.getCacheExpirationTime(any(), any()) } returns PAST

        val observer: Observer<Resource<PoiDetail>> = mockk(relaxed = true)
        repository.getPoiDetail(POI_ID).observeForever(observer)

        verifyAll {
            dao.loadPoiById(POI_ID)
            service.getPoiDetail(POI_ID)
            dao.deletePoi(POI_ID)
            dao.insertPoi(any())
        }
    }

    @Test
    fun getPoiDetail_errorFromNetworkShouldNotInsertPoi() {
        val localData = MutableLiveData<PoiDetail>().apply { value = null }
        val networkData = MutableLiveData<ApiResponse<PoiDetailResponseDto>>().apply {
            value = ApiResponse.create(RuntimeException())
        }
        every { dao.loadPoiById(POI_ID) } returns localData
        every { service.getPoiDetail(POI_ID) } returns networkData

        val observer: Observer<Resource<PoiDetail>> = mockk(relaxed = true)
        repository.getPoiDetail(POI_ID).observeForever(observer)

        verify { service.getPoiDetail(POI_ID) }
        verify(exactly = 0) { dao.insertPoi(any()) }
    }
}
package com.carles.carleskotlin.poi.repository

import com.carles.carleskotlin.createPoiList
import com.carles.carleskotlin.poi.datasource.PoiCloudDatasource
import com.carles.carleskotlin.poi.datasource.PoiLocalDatasource
import com.carles.carleskotlin.poi.model.Poi
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Maybe
import io.reactivex.Single
import org.junit.Before
import org.junit.Test

class PoiRepositoryTest {

    private lateinit var poiRepository: PoiRepository
    private val poiLocalDatasource: PoiLocalDatasource = mockk()
    private val poiCloudDatasource: PoiCloudDatasource = mockk()

    @Before
    fun setup() {
        poiRepository = PoiRepository(poiLocalDatasource, poiCloudDatasource)
    }

    @Test
    fun getPoiList_getListFromLocal() {
        every { poiLocalDatasource.getPoiList() } returns Maybe.just(createPoiList("local"))
        every { poiCloudDatasource.getPoiList() } returns Single.just(createPoiList("cloud"))
        poiRepository.getPoiList().test().assertValue { it.get(0).id == "local" }
    }

    @Test
    fun getPoiList_getListFromCloud() {
        every { poiLocalDatasource.getPoiList() } returns Maybe.empty()
        every { poiCloudDatasource.getPoiList() } returns Single.just(createPoiList("cloud"))
        poiRepository.getPoiList().test().assertValue { it.get(0).id == "cloud" }
    }

    @Test
    fun getPoiList_cloudError() {
        val error = Throwable()
        every { poiLocalDatasource.getPoiList() } returns Maybe.empty()
        every { poiCloudDatasource.getPoiList() } returns Single.error(error)
        poiRepository.getPoiList().test().assertError(error)
    }

    @Test
    fun getPoiDetail_shouldAccessDatasources() {
        every { poiLocalDatasource.getPoiDetail(any()) } returnsMany listOf(Maybe.just(Poi("local")), Maybe.empty())
        every { poiCloudDatasource.getPoiDetail(any()) } returns Single.just(Poi("cloud"))
        poiRepository.getPoiDetail("first").test().assertValue { it.id == "local" }.assertComplete()
        poiRepository.getPoiDetail("second").test().assertValue { it.id == "cloud" }.assertComplete()
    }

}
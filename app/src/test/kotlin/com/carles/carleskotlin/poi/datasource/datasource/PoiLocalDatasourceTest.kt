package com.carles.carleskotlin.poi.datasource.datasource

import android.content.SharedPreferences
import com.carles.carleskotlin.common.setCacheExpirationTime
import com.carles.carleskotlin.poi.datasource.PoiDao
import com.carles.carleskotlin.poi.datasource.PoiLocalDatasource
import com.carles.carleskotlin.poi.model.Poi
import io.mockk.*
import io.reactivex.Maybe
import org.junit.Before
import org.junit.Test

class PoiLocalDatasourceTest {

    private lateinit var datasource: PoiLocalDatasource
    private val sharedPreferences: SharedPreferences = mockk()
    private val dao: PoiDao = mockk(relaxed = true)

    @Before
    fun setup() {
        datasource = PoiLocalDatasource(dao, sharedPreferences)
    }

    @Test
    fun getPoiList_shouldReturnEmpty() {
        datasource.getPoiList().test().assertNoValues().assertComplete()
    }

    @Test
    fun getPoiDetail_shouldReturnNoValuesIfCacheExpired() {
        val spy = spyk(datasource)
        every { spy.isExpired(any(), any()) } returns true
        spy.getPoiDetail("some_id").test().assertNoValues().assertComplete()
        verifyAll { dao.deletePoi(any()) }
    }

    @Test
    fun getPoiDetail_shouldReturnNoValuesIfNoData() {
        val spy = spyk(datasource)
        every { spy.isExpired(any(), any()) } returns false
        every { dao.loadPoiById(any()) } returns Maybe.empty()
        spy.getPoiDetail("some_id").test().assertNoValues().assertComplete()
    }

    @Test
    fun getPoiDetail_shouldReturnStoredValues() {
        val spy = spyk(datasource)
        val poi = Poi("some_id")
        every { spy.isExpired(any(), any()) } returns false
        every { dao.loadPoiById(any()) } returns Maybe.just(poi)
        spy.getPoiDetail("some_id").test().assertValue(poi).assertComplete()
    }

    @Test
    fun persist_shouldPersistToRealm() {
        val poi = Poi("some_id")
        mockkStatic("com.carles.carleskotlin.common.ExtensionsKt")
        every { sharedPreferences.setCacheExpirationTime(any(), any(), any()) } just Runs

        datasource.persist(poi)

        verify {
            dao.insertPoi(poi)
            sharedPreferences.setCacheExpirationTime(any(), "some_id", any())
        }
    }
}
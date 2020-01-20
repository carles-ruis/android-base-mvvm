package com.carles.carleskotlin.poi.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.carles.carleskotlin.KotlinDatabase
import com.carles.carleskotlin.common.test.LiveDataTestUtil.getValue
import com.carles.carleskotlin.common.test.createPoiDetail
import com.carles.carleskotlin.common.test.createPoiList
import com.carles.carleskotlin.poi.model.Poi
import com.carles.carleskotlin.poi.model.PoiDetail
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import org.junit.After
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PoiDaoTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    lateinit var database: KotlinDatabase
    lateinit var dao: PoiDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getInstrumentation().targetContext, KotlinDatabase::class.java)
                .allowMainThreadQueries()
                .build();
        dao = database.poiDao().apply {
            insertPois(createPoiList())
            insertPoi(createPoiDetail())
        }
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun loadPois_shouldQueryAll() {
        val pois = getValue(dao.loadPois())
        assertEquals(2, pois.size)
        assertEquals("1", pois.get(0).id)
        assertEquals("2", pois.get(1).id)
    }

    @Test
    fun insertPois_shouldReplaceOnConflict() {
        dao.insertPois(listOf(
                Poi("1", "new_title", "new_geocoordinates"),
                Poi("2", "new_title", "new_geocoordinates")))
        assertEquals("new_title", getValue(dao.loadPois()).get(0).title)
    }

    @Test
    fun deletePois_shouldDeleteAll() {
        dao.deletePois()
        assertTrue(getValue(dao.loadPois()).isEmpty())
    }

    @Test
    fun loadPoiById_shouldLoadPoiWhenExists() {
        assertEquals("the_title", getValue(dao.loadPoiById("1")).title)
        assertNull(getValue(dao.loadPoiById("2")))
    }

    @Test
    fun insertPoi_shouldReplaceOnConflict() {
        dao.insertPoi(PoiDetail(id = "1", title = "new_title"))
        assertEquals("new_title", getValue(dao.loadPoiById("1")).title)
    }

    @Test
    fun deletePoi_shouldDeleteWhenExists() {
        assertTrue(dao.deletePoi("1") == 1)
        assertTrue(dao.deletePoi("2") == 0)
    }
}
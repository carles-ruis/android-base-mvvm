package com.carles.carleskotlin.poi.viewmodel

import com.carles.carleskotlin.common.test.POI_ID
import com.carles.carleskotlin.poi.data.PoiRepository
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test

class PoiDetailViewModelTest {

    val repository : PoiRepository = mockk(relaxed = true)

    @Before
    fun setup() {
        PoiDetailViewModel(mockk(), POI_ID, repository)
    }

    @Test
    fun init_getPoiDetail() {
        verify { repository.getPoiDetail(POI_ID) }
    }
}
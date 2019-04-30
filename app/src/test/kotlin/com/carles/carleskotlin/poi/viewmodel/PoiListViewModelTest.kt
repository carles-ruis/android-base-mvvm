package com.carles.carleskotlin.poi.viewmodel

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.Observer
import com.carles.carleskotlin.common.model.Resource
import com.carles.carleskotlin.poi.model.Poi
import com.carles.carleskotlin.poi.model.PoiRepository
import io.mockk.clearMocks
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class PoiListViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    val observer: Observer<Resource<List<Poi>>> = mockk()
    val repository: PoiRepository = mockk(relaxed = true)
    lateinit var viewModel: PoiListViewModel

    @Before
    fun setup() {
        viewModel = PoiListViewModel(mockk(), repository)
        viewModel.observablePoiList.observeForever(observer)
    }

    @Test
    fun init_getPoiList() {
        verify { repository.getPoiList() }
    }

    @Test
    fun retry_getPoiList() {
        clearMocks(repository)
        viewModel.retry()
        verify { repository.getPoiList() }
    }
}
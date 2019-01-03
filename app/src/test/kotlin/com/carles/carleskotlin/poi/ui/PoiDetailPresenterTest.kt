package com.carles.carleskotlin.poi.ui

import com.carles.carleskotlin.R
import com.carles.carleskotlin.createPoi
import com.carles.carleskotlin.poi.repository.PoiRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Single
import io.reactivex.schedulers.TestScheduler
import org.junit.Before
import org.junit.Test

class PoiDetailPresenterTest {

    private lateinit var presenter: PoiDetailPresenter
    private val view: PoiDetailView = mockk(relaxed = true)
    private val testScheduler = TestScheduler()
    private val poiRepository: PoiRepository = mockk()

    @Before
    fun setup() {
        presenter = PoiDetailPresenter(view, "some_id", testScheduler, testScheduler, poiRepository)
    }

    @Test
    fun onViewCreated_getPoiDetailSuccess() {
        every { poiRepository.getPoiDetail("some_id") } returns Single.just(createPoi("some_poi"))
        presenter.onViewCreated()
        testScheduler.triggerActions()
        verify { poiRepository.getPoiDetail("some_id") }
        verify { with(view) { showProgress(); hideProgress(); displayPoiDetail(any()) } }
    }

    @Test
    fun onViewCreated_getPoiDetailError() {
        every { poiRepository.getPoiDetail("some_id") } returns Single.error(Throwable())
        presenter.onViewCreated()
        testScheduler.triggerActions()
        verify { poiRepository.getPoiDetail("some_id") }
        verify { with(view) { showProgress(); showError(R.string.error_server_response, any()) } }
    }
}
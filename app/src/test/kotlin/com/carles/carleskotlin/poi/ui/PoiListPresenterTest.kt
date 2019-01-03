package com.carles.carleskotlin.poi.ui

import com.carles.carleskotlin.R
import com.carles.carleskotlin.createPoi
import com.carles.carleskotlin.createPoiList
import com.carles.carleskotlin.poi.repository.PoiRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Single
import io.reactivex.schedulers.TestScheduler
import org.junit.Before
import org.junit.Test

class PoiListPresenterTest {

    private lateinit var presenter: PoiListPresenter
    private val poiRepository: PoiRepository = mockk()
    private val view: PoiListView = mockk(relaxed = true)
    private val testScheduler = TestScheduler()

    @Before
    fun setup() {
        presenter = PoiListPresenter(view, testScheduler, testScheduler, poiRepository)
    }

    @Test
    fun onViewCreated_getPoiListSuccess() {
        every { poiRepository.getPoiList() } returns Single.just(createPoiList("one", "two"))
        presenter.onViewCreated()
        testScheduler.triggerActions()
        verify { poiRepository.getPoiList() }
        verify { with(view) { showProgress(); hideProgress(); displayPoiList(any()) } }
    }

    @Test
    fun onViewCreate_getPoiListError() {
        every { poiRepository.getPoiList() } returns Single.error(Throwable())
        presenter.onViewCreated()
        testScheduler.triggerActions()
        verify { poiRepository.getPoiList() }
        verify { with(view) { showProgress(); showError(R.string.error_server_response, any()) } }
    }

    @Test
    fun onPoiClicked_navigateToDetail() {
        presenter.onPoiClicked(createPoi("some_poi"))
        verify { view.navigateToPoiDetail("some_poi") }
    }

}
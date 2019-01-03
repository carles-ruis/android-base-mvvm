package com.carles.carleskotlin.poi.ui

import com.carles.carleskotlin.common.getMessageId
import com.carles.carleskotlin.common.ui.BasePresenter
import com.carles.carleskotlin.poi.repository.PoiRepository
import io.reactivex.Scheduler

class PoiDetailPresenter(poiDetailView: PoiDetailView, private val id: String, val uiScheduler: Scheduler, val processScheduler: Scheduler, val poiRepository: PoiRepository) : BasePresenter<PoiDetailView>(poiDetailView) {

    override fun onViewCreated() {
        super.onViewCreated()
        getPoiDetail()
    }

    private fun getPoiDetail() {
        view.showProgress()
        addDisposable(poiRepository.getPoiDetail(id).subscribeOn(processScheduler).observeOn(uiScheduler).subscribe(
            { view.hideProgress(); view.displayPoiDetail(it) },
            { view.showError(it.getMessageId(), { getPoiDetail() }) }
        ))
    }
}
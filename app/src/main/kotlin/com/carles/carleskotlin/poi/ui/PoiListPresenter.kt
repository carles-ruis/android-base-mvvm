package com.carles.carleskotlin.poi.ui

import com.carles.carleskotlin.common.getMessageId
import com.carles.carleskotlin.common.ui.BasePresenter
import com.carles.carleskotlin.poi.model.Poi
import com.carles.carleskotlin.poi.repository.PoiRepository
import io.reactivex.Scheduler

class PoiListPresenter(poiListView: PoiListView, val uiScheduler: Scheduler, val processScheduler: Scheduler, val poiRepository: PoiRepository) : BasePresenter<PoiListView>(poiListView) {

    override fun onViewCreated() {
        super.onViewCreated()
        getPoiList()
    }

    private fun getPoiList() {
        view.showProgress()
        addDisposable(poiRepository.getPoiList().subscribeOn(processScheduler).observeOn(uiScheduler).subscribe(
            { view.hideProgress(); view.displayPoiList(it) },
            { view.showError(messageId = it.getMessageId(), onRetry = { getPoiList() }) }
        ))
    }

    fun onPoiClicked(poi: Poi) {
        view.navigateToPoiDetail(poi.id)
    }


}
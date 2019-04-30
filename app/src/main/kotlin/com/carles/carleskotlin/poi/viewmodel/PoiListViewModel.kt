package com.carles.carleskotlin.poi.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Transformations
import com.carles.carleskotlin.common.livedata.SingleLiveEvent
import com.carles.carleskotlin.common.model.Resource
import com.carles.carleskotlin.poi.model.Poi
import com.carles.carleskotlin.poi.model.PoiRepository

class PoiListViewModel(application: Application, poiRepository: PoiRepository) : AndroidViewModel(application) {

    private val getPoiListEvent = SingleLiveEvent<Void>()
    val observablePoiList : LiveData<Resource<List<Poi>>>

    init {
        observablePoiList = Transformations.switchMap(getPoiListEvent) { poiRepository.getPoiList() }
        getPoiListEvent.call()
    }

    fun retry() {
        getPoiListEvent.call()
    }

}

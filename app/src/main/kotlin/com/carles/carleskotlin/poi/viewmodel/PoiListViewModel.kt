package com.carles.carleskotlin.poi.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.Transformations
import com.carles.carleskotlin.common.viewmodel.ResourceLiveData
import com.carles.carleskotlin.common.viewmodel.SingleLiveEvent
import com.carles.carleskotlin.poi.domain.Poi
import com.carles.carleskotlin.poi.model.PoiRepository

class PoiListViewModel(application: Application, private val poiRepository: PoiRepository) : AndroidViewModel(application) {

    private val getPoiListEvent = SingleLiveEvent<Void>()
    private val _observablePoiList: ResourceLiveData<List<Poi>>
    val observablePoiList get() = _observablePoiList

    init {
        _observablePoiList = Transformations.switchMap(getPoiListEvent) { poiRepository.getPoiList() }
        getPoiListEvent.call()
    }

    fun retry() {
        getPoiListEvent.call()
    }
}

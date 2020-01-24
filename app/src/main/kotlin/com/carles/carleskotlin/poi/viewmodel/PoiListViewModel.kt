package com.carles.carleskotlin.poi.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.carles.carleskotlin.common.viewmodel.SingleLiveEvent
import com.carles.carleskotlin.common.data.Resource
import com.carles.carleskotlin.poi.model.Poi
import com.carles.carleskotlin.poi.data.PoiRepository

class PoiListViewModel(application: Application, private val poiRepository: PoiRepository) : AndroidViewModel(application) {

    private val getPoiListEvent = SingleLiveEvent<Void>()
    private val _observablePoiList: LiveData<Resource<List<Poi>>>
    val observablePoiList: LiveData<Resource<List<Poi>>> get() = _observablePoiList

    init {
        _observablePoiList = Transformations.switchMap(getPoiListEvent) { poiRepository.getPoiList() }
        getPoiListEvent.call()
    }

    fun retry() {
        getPoiListEvent.call()
    }

}

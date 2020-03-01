package com.carles.carleskotlin.poi.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.carles.carleskotlin.common.viewmodel.ResourceLiveData
import com.carles.carleskotlin.poi.domain.PoiDetail
import com.carles.carleskotlin.poi.model.PoiRepository

class PoiDetailViewModel(application: Application, id: String, poiRepository: PoiRepository)
    : AndroidViewModel(application) {

    private val _observablePoiDetail: ResourceLiveData<PoiDetail>
    val observablePoiDetail get() = _observablePoiDetail

    init {
        _observablePoiDetail = poiRepository.getPoiDetail(id)
    }

}
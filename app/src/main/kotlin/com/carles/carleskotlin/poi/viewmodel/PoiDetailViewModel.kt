package com.carles.carleskotlin.poi.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.carles.carleskotlin.common.viewmodel.Resource
import com.carles.carleskotlin.poi.domain.PoiDetail
import com.carles.carleskotlin.poi.model.PoiRepository

class PoiDetailViewModel(application: Application, id: String, poiRepository: PoiRepository)
    : AndroidViewModel(application) {

    private val _observablePoiDetail: LiveData<Resource<PoiDetail>>
    val observablePoiDetail: LiveData<Resource<PoiDetail>> get() = _observablePoiDetail

    init {
        _observablePoiDetail = poiRepository.getPoiDetail(id)
    }

}
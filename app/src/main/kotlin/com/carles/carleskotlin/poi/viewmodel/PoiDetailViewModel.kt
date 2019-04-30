package com.carles.carleskotlin.poi.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.carles.carleskotlin.common.model.Resource
import com.carles.carleskotlin.poi.model.PoiDetail
import com.carles.carleskotlin.poi.model.PoiRepository

class PoiDetailViewModel(application: Application, id: String, poiRepository: PoiRepository) : AndroidViewModel (application) {

    val observablePoiDetail : LiveData<Resource<PoiDetail>>

    init {
        observablePoiDetail = poiRepository.getPoiDetail(id)
    }
}
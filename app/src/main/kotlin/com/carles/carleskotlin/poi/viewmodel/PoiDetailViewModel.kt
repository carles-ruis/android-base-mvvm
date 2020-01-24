package com.carles.carleskotlin.poi.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.carles.carleskotlin.common.data.Resource
import com.carles.carleskotlin.poi.model.PoiDetail
import com.carles.carleskotlin.poi.data.PoiRepository

class PoiDetailViewModel(application: Application,
                         private val id: String,
                         private val poiRepository: PoiRepository)
    : AndroidViewModel(application) {

    private val _observablePoiDetail: LiveData<Resource<PoiDetail>> by lazy {
        poiRepository.getPoiDetail(id)
    }
    val observablePoiDetail: LiveData<Resource<PoiDetail>> get() = _observablePoiDetail

}
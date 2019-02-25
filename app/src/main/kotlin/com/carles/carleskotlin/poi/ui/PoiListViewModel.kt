package com.carles.carleskotlin.poi.ui

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.carles.carleskotlin.poi.model.Poi
import com.carles.carleskotlin.poi.repository.PoiRepository

class PoiListViewModel(val poiRepository: PoiRepository) : ViewModel() {

    val poiData = MutableLiveData<List<Poi>>()

    fun getPoiList() : List<Poi> {
        poiRepository.getPoiList()
    }

    override fun onCleared() {
        super.onCleared()
    }

}
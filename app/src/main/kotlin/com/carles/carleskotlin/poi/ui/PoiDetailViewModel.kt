package com.carles.carleskotlin.poi.ui

import android.arch.lifecycle.ViewModel
import com.carles.carleskotlin.poi.repository.PoiRepository

class PoiDetailViewModel(val id: String, val poiRepository: PoiRepository) : ViewModel() {
}
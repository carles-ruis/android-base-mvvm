package com.carles.carleskotlin.poi.ui

import com.carles.carleskotlin.common.ui.BaseView
import com.carles.carleskotlin.poi.model.Poi

interface PoiListView : BaseView {
    fun displayPoiList(poiList: List<Poi>)
    fun navigateToPoiDetail(id: String)
}
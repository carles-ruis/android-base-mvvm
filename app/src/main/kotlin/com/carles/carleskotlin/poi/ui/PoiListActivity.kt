package com.carles.carleskotlin.poi.ui

import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.DividerItemDecoration.VERTICAL
import com.carles.carleskotlin.R
import com.carles.carleskotlin.common.ui.BaseActivity
import com.carles.carleskotlin.poi.model.Poi
import kotlinx.android.synthetic.main.activity_poi_list.poilist_recyclerview
import kotlinx.android.synthetic.main.view_toolbar.toolbar
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class PoiListActivity : BaseActivity<PoiListPresenter>(), PoiListView {

    override val layoutResourceId = R.layout.activity_poi_list
    override val presenter : PoiListPresenter by inject { parametersOf(this) }
    private lateinit var adapter : PoiListAdapter

    override fun initViews() {
        toolbar.setTitle(R.string.poilist_title)
        adapter = PoiListAdapter { presenter.onPoiClicked(it) }
        poilist_recyclerview.addItemDecoration(DividerItemDecoration(this, VERTICAL))
        poilist_recyclerview.adapter = adapter
    }

    override fun displayPoiList(poiList: List<Poi>) {
        adapter.setItems(poiList)
    }

    override fun navigateToPoiDetail(id: String) = startActivity(PoiDetailActivity.newIntent(this, id))

}
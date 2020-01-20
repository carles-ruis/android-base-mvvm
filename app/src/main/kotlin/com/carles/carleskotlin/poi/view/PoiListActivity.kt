package com.carles.carleskotlin.poi.view

import androidx.lifecycle.Observer
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import com.carles.carleskotlin.R
import com.carles.carleskotlin.common.model.Status
import com.carles.carleskotlin.common.view.BaseActivity
import com.carles.carleskotlin.poi.model.Poi
import com.carles.carleskotlin.poi.viewmodel.PoiListViewModel
import kotlinx.android.synthetic.main.activity_poi_list.poilist_recyclerview
import kotlinx.android.synthetic.main.view_toolbar.toolbar
import org.koin.android.viewmodel.ext.android.viewModel

class PoiListActivity : BaseActivity() {

    override val layoutResourceId = R.layout.activity_poi_list
    val viewModel by viewModel<PoiListViewModel>()
    private lateinit var adapter: PoiListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.observablePoiList.observe(this, Observer { it?.let { handlePoiList(it.status, it.data, it.message) } })
    }

    override fun initViews() {
        setSupportActionBar(toolbar)
        toolbar.setTitle(R.string.poilist_title)
        toolbar.setNavigationIcon(R.drawable.ic_close_white_24dp)
        toolbar.setNavigationOnClickListener { finish() }

        adapter = PoiListAdapter { poi -> navigateToPoiDetail(poi.id) }
        poilist_recyclerview.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        poilist_recyclerview.adapter = adapter
    }

    private fun handlePoiList(status: Status, poiList: List<Poi>?, errorMessage: String?) {
        when (status) {
            Status.SUCCESS -> {
                hideProgress()
                adapter.setItems(poiList ?: emptyList())
            }
            Status.ERROR -> {
                hideProgress()
                showError(errorMessage) { viewModel.retry() }
            }
            Status.LOADING -> showProgress()
        }
    }

    private fun navigateToPoiDetail(id: String) = startActivity(PoiDetailActivity.newIntent(this, id))
}
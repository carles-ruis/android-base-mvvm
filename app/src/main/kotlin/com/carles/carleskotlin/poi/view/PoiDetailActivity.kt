package com.carles.carleskotlin.poi.view

import androidx.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import com.carles.carleskotlin.R
import com.carles.carleskotlin.common.model.Status
import com.carles.carleskotlin.common.view.BaseActivity
import com.carles.carleskotlin.common.view.initDefaultToolbar
import com.carles.carleskotlin.poi.model.PoiDetail
import com.carles.carleskotlin.poi.viewmodel.PoiDetailViewModel
import kotlinx.android.synthetic.main.activity_poi_detail.*
import kotlinx.android.synthetic.main.view_toolbar.toolbar
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class PoiDetailActivity : BaseActivity() {

    override val layoutResourceId = R.layout.activity_poi_detail
    val viewModel by viewModel<PoiDetailViewModel> { parametersOf(intent.getStringExtra(EXTRA_ID)) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.observablePoiDetail.observe(this, Observer { it?.let { handlePoiDetail(it.status, it.data, it.message) } })
    }

    override fun initViews() {
        initDefaultToolbar()
    }

    private fun handlePoiDetail(status: Status, poiDetail: PoiDetail?, errorMessage: String?) {
        when (status) {
            Status.SUCCESS -> {
                hideProgress()
                if (poiDetail != null) displayPoiDetail(poiDetail)
            }
            Status.ERROR -> {
                hideProgress()
                showError(errorMessage)
            }
            Status.LOADING -> showProgress()
        }
    }

    private fun displayPoiDetail(poi: PoiDetail) {
        toolbar.title = poi.title
        poidetail_contentview.visibility = VISIBLE
        poidetail_address_textview.text = poi.address
        poidetail_description_textview.text = poi.description

        poidetail_transport_textview.text = poi.transport ?: ""
        poidetail_transport_textview.visibility = if (poi.transport == null) GONE else VISIBLE
        poidetail_mail_textview.text = poi.email ?: ""
        poidetail_mail_textview.visibility = if (poi.email == null) GONE else VISIBLE
        poidetail_phone_textview.text = poi.phone ?: ""
        poidetail_phone_textview.visibility = if (poi.phone == null) GONE else VISIBLE
    }

    companion object {
        private const val EXTRA_ID = "poi_detail_extra_id"
        fun newIntent(context: Context, id: String) = Intent(context, PoiDetailActivity::class.java).apply { putExtra(EXTRA_ID, id) }
    }
}
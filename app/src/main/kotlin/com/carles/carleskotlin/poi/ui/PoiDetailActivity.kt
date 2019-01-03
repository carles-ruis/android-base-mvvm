package com.carles.carleskotlin.poi.ui

import android.content.Context
import android.content.Intent
import android.view.View.GONE
import android.view.View.VISIBLE
import com.carles.carleskotlin.R
import com.carles.carleskotlin.common.ui.BaseActivity
import com.carles.carleskotlin.poi.model.Poi
import kotlinx.android.synthetic.main.activity_poi_detail.*
import kotlinx.android.synthetic.main.view_toolbar.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class PoiDetailActivity : BaseActivity<PoiDetailPresenter>(), PoiDetailView {

    override val layoutResourceId = R.layout.activity_poi_detail
    override val presenter: PoiDetailPresenter by inject { parametersOf(this, intent.getStringExtra(EXTRA_ID)) }

    override fun initViews() {
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowCustomEnabled(true)
        toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    override fun displayPoiDetail(poi: Poi) {
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
        private val EXTRA_ID = "poi_detail_extra_id"
        fun newIntent(context: Context, id: String): Intent {
            val intent = Intent(context, PoiDetailActivity::class.java)
            intent.putExtra(EXTRA_ID, id)
            return intent
        }
    }
}
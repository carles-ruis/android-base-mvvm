package com.carles.carleskotlin.poi.ui

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.carles.carleskotlin.R
import com.carles.carleskotlin.common.inflate
import com.carles.carleskotlin.poi.model.Poi
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_poi_list.*

class PoiListAdapter : RecyclerView.Adapter<PoiListAdapter.ViewHolder>() {

    var listener : Listener? = null
    private val items = ArrayList<Poi>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
            ViewHolder(parent.inflate(R.layout.item_poi_list))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) { holder.onBindView(items.get(position)) }

    override fun getItemCount(): Int = items.size

    fun setItems(items:List<Poi>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    inner class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        init {
            containerView.setOnClickListener {  listener?.onPoiClicked(items.get(adapterPosition))}
        }

        fun onBindView(item:Poi) {
            poilist_item_title_textview.text = item.title
        }
    }
    interface Listener {
        fun onPoiClicked(poi: Poi)
    }
}
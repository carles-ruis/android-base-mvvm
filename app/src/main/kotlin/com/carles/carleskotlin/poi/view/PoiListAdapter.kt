package com.carles.carleskotlin.poi.view

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.carles.carleskotlin.R
import com.carles.carleskotlin.common.view.inflate
import com.carles.carleskotlin.poi.model.Poi
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_poi_list.*

class PoiListAdapter(private val onPoiClicked: (Poi) -> Unit) : RecyclerView.Adapter<PoiListAdapter.ViewHolder>() {

    private val items = ArrayList<Poi>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(parent.inflate(R.layout.item_poi_list))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) { holder.onBindView(items.get(position)) }

    override fun getItemCount(): Int = items.size

    fun setItems(items:List<Poi>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    inner class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        init {
            containerView.setOnClickListener { onPoiClicked(items.get(adapterPosition)) }
        }

        fun onBindView(item:Poi) {
            poilist_item_title_textview.text = item.title
        }
    }
}
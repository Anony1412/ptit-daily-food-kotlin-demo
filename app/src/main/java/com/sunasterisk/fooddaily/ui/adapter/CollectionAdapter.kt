package com.sunasterisk.fooddaily.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sunasterisk.fooddaily.R
import com.sunasterisk.fooddaily.data.model.Collection
import com.sunasterisk.fooddaily.data.model.FoodDetail

class CollectionAdapter(
    private val collections: List<Collection>,
    private val onClickListener: (FoodDetail) -> Unit
) : RecyclerView.Adapter<CollectionViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectionViewHolder {
        return CollectionViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_collection, parent, false),
            onClickListener
        ) { changeItemExpandState(it) }
    }

    override fun getItemCount(): Int = collections.size

    override fun onBindViewHolder(holder: CollectionViewHolder, position: Int) {
        holder.bind(collections[position])
    }

    private fun changeItemExpandState(position: Int) {
        notifyItemChanged(position)
    }
}

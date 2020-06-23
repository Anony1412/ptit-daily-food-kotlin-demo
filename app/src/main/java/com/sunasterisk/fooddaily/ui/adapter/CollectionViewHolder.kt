package com.sunasterisk.fooddaily.ui.adapter

import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.sunasterisk.fooddaily.data.model.Collection
import com.sunasterisk.fooddaily.data.model.FoodDetail
import com.sunasterisk.fooddaily.utils.FoodType
import kotlinx.android.synthetic.main.item_collection.view.*

class CollectionViewHolder(
    view: View,
    private val onClickListener: (FoodDetail) -> Unit,
    private val onClickChangeExpand: (Int) -> Unit
) : RecyclerView.ViewHolder(view) {

    private val adapterContent: FoodAdapter by lazy {
        FoodAdapter(FoodType.OTHER_LIST) { onClickListener(it) }
    }

    private var collection: Collection? = null

    init {
        itemView.apply {
            setOnClickListener {
                collection?.apply {
                    onClickChangeExpand(adapterPosition)
                    isExpanded = !isExpanded
                }
            }
            recyclerViewFoodOfCollection.run {
                setRecycledViewPool(RecyclerView.RecycledViewPool())
                adapter = adapterContent
            }
        }
    }

    fun bind(collection: Collection) {
        this.collection = collection
        itemView.apply {
            textCollectionName.text = collection.name
            imageCollection.setImageResource(collection.imgRes)
            recyclerViewFoodOfCollection.apply {
                isVisible = collection.isExpanded
            }
            adapterContent.updateData(collection.content)
        }
    }
}

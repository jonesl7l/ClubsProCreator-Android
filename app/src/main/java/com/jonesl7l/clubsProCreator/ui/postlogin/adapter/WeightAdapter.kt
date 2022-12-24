package com.jonesl7l.clubsProCreator.ui.postlogin.adapter

import androidx.recyclerview.widget.RecyclerView
import com.jonesl7l.clubsProCreator.R
import com.jonesl7l.clubsProCreator.model.virtualpro.VirtualProWeight
import com.jonesl7l.clubsProCreator.model.WeightItemsGroup
import com.jonesl7l.clubsProCreator.ui.postlogin.viewholder.SelectionViewHolder
import com.jonesl7l.clubsProCreator.ui.postlogin.viewholder.TitleViewHolder
import com.jonesl7l.clubsProCreator.util.Consts

class WeightAdapter(private val itemsGroup: WeightItemsGroup = WeightItemsGroup(), private val itemClickInterface: ItemClickInterface) : SelectionAdapter(itemsGroup) {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is TitleViewHolder -> {
                val displayValue = holder.itemView.context.getString(R.string.weight_template).replace(VirtualProWeight.WEIGHT_TEMPLATE_KEY, itemsGroup.title)
                holder.bind(displayValue, isExpanded, onHeaderClickListener)
            }
            is SelectionViewHolder -> {
                val item: VirtualProWeight? = itemsGroup.items[position.minus(1)] as? VirtualProWeight
                val displayValue = if (Consts.isUsingLbs) {
                    item?.getPoundsValue().orEmpty()
                } else {
                    item?.getGramsValue().orEmpty()
                }
                val isSelected: Boolean = itemsGroup.title == displayValue
                holder.bind(item?.getFullIdentifier().orEmpty(), displayValue, isSelected, itemClickInterface)
            }
            else -> super.onBindViewHolder(holder, position)
        }
    }
}
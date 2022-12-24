package com.jonesl7l.clubsProCreator.ui.postlogin.adapter

import androidx.recyclerview.widget.RecyclerView
import com.jonesl7l.clubsProCreator.R
import com.jonesl7l.clubsProCreator.model.PositionItemsGroup
import com.jonesl7l.clubsProCreator.model.virtualpro.VirtualProPosition
import com.jonesl7l.clubsProCreator.model.virtualpro.VirtualProWeight
import com.jonesl7l.clubsProCreator.ui.postlogin.viewholder.SelectionViewHolder
import com.jonesl7l.clubsProCreator.ui.postlogin.viewholder.TitleViewHolder

class PositionAdapter(private val itemsGroup: PositionItemsGroup = PositionItemsGroup(), private val itemClickInterface: ItemClickInterface) : SelectionAdapter(itemsGroup) {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is TitleViewHolder -> {
                val displayValue = holder.itemView.context.getString(R.string.position_template).replace(VirtualProPosition.POSITION_TEMPLATE_KEY, itemsGroup.title)
                holder.bind(displayValue, isExpanded, onHeaderClickListener)
            }
            is SelectionViewHolder -> {
                val item: VirtualProPosition? = itemsGroup.items[position.minus(1)] as? VirtualProPosition
                val displayValue = item?.getPositionValue().orEmpty()
                val isSelected: Boolean = itemsGroup.title == displayValue
                holder.bind(item?.getFullIdentifier().orEmpty(), displayValue, isSelected, itemClickInterface)
            }
            else -> super.onBindViewHolder(holder, position)
        }
    }
}
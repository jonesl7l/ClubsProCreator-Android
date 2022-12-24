package com.jonesl7l.clubsProCreator.ui.postlogin.adapter

import androidx.recyclerview.widget.RecyclerView
import com.jonesl7l.clubsProCreator.R
import com.jonesl7l.clubsProCreator.model.HeightItemsGroup
import com.jonesl7l.clubsProCreator.model.virtualpro.VirtualProHeight
import com.jonesl7l.clubsProCreator.model.virtualpro.VirtualProPosition
import com.jonesl7l.clubsProCreator.ui.postlogin.viewholder.SelectionViewHolder
import com.jonesl7l.clubsProCreator.ui.postlogin.viewholder.TitleViewHolder
import com.jonesl7l.clubsProCreator.util.Consts

class HeightAdapter(private val itemsGroup: HeightItemsGroup = HeightItemsGroup(), private val itemClickInterface: ItemClickInterface) : SelectionAdapter(itemsGroup) {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is TitleViewHolder -> {
                val displayValue = holder.itemView.context.getString(R.string.height_template).replace(VirtualProHeight.HEIGHT_TEMPLATE_KEY, itemsGroup.title)
                holder.bind(displayValue, isExpanded, onHeaderClickListener)
            }
            is SelectionViewHolder -> {
                val item: VirtualProHeight? = itemsGroup.items[position.minus(1)] as? VirtualProHeight
                val displayValue = if (Consts.isUsingInches) {
                    item?.getInchesValue().orEmpty()
                } else {
                    item?.getCmValue().orEmpty()
                }
                val isSelected: Boolean = itemsGroup.title == displayValue
                holder.bind(item?.getFullIdentifier().orEmpty(), displayValue, isSelected, itemClickInterface)
            }
            else -> super.onBindViewHolder(holder, position)
        }
    }
}
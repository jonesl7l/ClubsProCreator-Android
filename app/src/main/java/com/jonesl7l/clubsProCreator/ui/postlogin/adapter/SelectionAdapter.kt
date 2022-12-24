package com.jonesl7l.clubsProCreator.ui.postlogin.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.jonesl7l.clubsProCreator.databinding.ItemSelectionBinding
import com.jonesl7l.clubsProCreator.databinding.ItemSwitchBinding
import com.jonesl7l.clubsProCreator.databinding.ItemTitleBinding
import com.jonesl7l.clubsProCreator.model.virtualpro.ItemInterface
import com.jonesl7l.clubsProCreator.model.SelectionItemGroups
import com.jonesl7l.clubsProCreator.ui.postlogin.viewholder.SelectionViewHolder
import com.jonesl7l.clubsProCreator.ui.postlogin.viewholder.SwitchViewHolder
import com.jonesl7l.clubsProCreator.ui.postlogin.viewholder.TitleViewHolder
import com.jonesl7l.clubsProCreator.util.Consts
import com.jonesl7l.clubsProCreator.util.orFalse
import com.jonesl7l.clubsProCreator.util.then
import kotlin.properties.Delegates
import kotlin.reflect.KProperty

abstract class SelectionAdapter(private val itemsGroup: SelectionItemGroups) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    protected var isExpanded: Boolean by Delegates.observable(false) { _: KProperty<*>, _: Boolean, newExpandedValue: Boolean ->
        if (newExpandedValue) {
            notifyItemRangeInserted(1, itemsGroup.items.size)
            notifyItemChanged(0)
        } else {
            notifyItemRangeRemoved(1, itemsGroup.items.size)
            notifyItemChanged(0)
        }
    }

    protected val onHeaderClickListener = View.OnClickListener {
        isExpanded = !isExpanded
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            AdapterViewHolderType.TITLE.getTypeValue() -> TitleViewHolder(ItemTitleBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            AdapterViewHolderType.SELECTION.getTypeValue() -> SelectionViewHolder(ItemSelectionBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            AdapterViewHolderType.SWITCH.getTypeValue() -> SwitchViewHolder(ItemSwitchBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            else -> throw Exception("Wrong View Type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is TitleViewHolder -> holder.bind(itemsGroup.title, isExpanded, onHeaderClickListener)
        }
    }

    override fun getItemCount(): Int = if (isExpanded) itemsGroup.items.size.plus(1) else 1

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) {
            AdapterViewHolderType.TITLE.getTypeValue()
        } else {
            AdapterViewHolderType.SELECTION.getTypeValue()
        }
    }

    open fun updateDataSet(newItemsGroup: SelectionItemGroups) {
        updateTitle(newItemsGroup.title)
        val oldSelectionIndex = itemsGroup.items.indexOf(itemsGroup.items.firstOrNull { it.isSelected }).plus(1)
        val newSelectionIndex = newItemsGroup.items.indexOf(newItemsGroup.items.firstOrNull { it.isSelected }).plus(1)
        itemsGroup.items.clear()
        itemsGroup.items.addAll(newItemsGroup.items)
        notifyItemChanged(oldSelectionIndex)
        notifyItemChanged(newSelectionIndex)
    }

    private fun updateTitle(newTitle: String) {
        itemsGroup.title = newTitle
        notifyItemChanged(0)
    }
}
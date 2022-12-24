package com.jonesl7l.clubsProCreator.ui.postlogin.viewholder

import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.jonesl7l.clubsProCreator.R
import com.jonesl7l.clubsProCreator.TemplateApplication.Companion.appContext
import com.jonesl7l.clubsProCreator.databinding.ItemSelectionBinding
import com.jonesl7l.clubsProCreator.ui.postlogin.adapter.ItemClickInterface

class SelectionViewHolder(private val binding: ItemSelectionBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(id: String = "", displayString: String, isSelected: Boolean = false, itemClickInterface: ItemClickInterface? = null) {
        binding.itemTitleText.text = displayString
        binding.itemTitleText.setTextColor(ContextCompat.getColor(appContext, if (isSelected) R.color.color_accent else R.color.color_primary))
        binding.root.setOnClickListener { itemClickInterface?.onItemClick(id) }
    }

    fun updateIsItemSelected(isSelected: Boolean) {
        binding.itemTitleText.setTextColor(ContextCompat.getColor(appContext, if (isSelected) R.color.color_accent else R.color.color_primary))
    }
}
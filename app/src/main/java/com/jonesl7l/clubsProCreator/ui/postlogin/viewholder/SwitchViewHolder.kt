package com.jonesl7l.clubsProCreator.ui.postlogin.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.jonesl7l.clubsProCreator.databinding.ItemSwitchBinding
import com.jonesl7l.clubsProCreator.ui.postlogin.adapter.ItemClickInterface

class SwitchViewHolder(private val binding: ItemSwitchBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(id: String = "", displayString: String, isChecked: Boolean = false, itemClickInterface: ItemClickInterface? = null) {
        binding.itemSwitchText.text = displayString
        binding.itemSwitch.isChecked = isChecked
        binding.root.setOnClickListener {
            binding.itemSwitch.isChecked = !binding.itemSwitch.isChecked
            itemClickInterface?.onItemClick(id)
        }
    }
}
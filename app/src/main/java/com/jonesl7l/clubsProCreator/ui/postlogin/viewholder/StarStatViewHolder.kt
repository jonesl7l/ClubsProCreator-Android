package com.jonesl7l.clubsProCreator.ui.postlogin.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.jonesl7l.clubsProCreator.databinding.ItemStarStatBarBinding

class StarStatViewHolder(private val binding: ItemStarStatBarBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(displayString: String, baseValue: Int = 3, currentValue: Int = 3) {
        binding.statBarTitle.text = displayString
        binding.statBaseBarRating.rating = baseValue.toFloat()
        binding.statBarRating.rating = currentValue.toFloat()
    }

    fun updateRating(newValue: Int) {
        binding.statBarRating.rating = newValue.toFloat()
    }
}
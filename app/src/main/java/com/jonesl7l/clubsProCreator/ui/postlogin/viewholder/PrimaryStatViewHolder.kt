package com.jonesl7l.clubsProCreator.ui.postlogin.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.jonesl7l.clubsProCreator.R
import com.jonesl7l.clubsProCreator.databinding.ItemPrimaryStatBarBinding
import com.jonesl7l.clubsProCreator.model.virtualpro.VirtualPro

class PrimaryStatViewHolder(private val binding: ItemPrimaryStatBarBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(displayString: String, baseProgress: Int = 0, progress: Int = 0, itemClickInterface: View.OnClickListener? = null) {
        binding.statBarTitle.text = displayString
        binding.statBarBaseProgress.progress = baseProgress
        binding.statBarProgress.progress = progress
        generateDisplayValue(progress)
        binding.root.setOnClickListener(itemClickInterface)
    }

    fun updateBaseStat(baseProgress: Int = 0) {
        val currentProgress = binding.statBarProgress.progress
        binding.statBarBaseProgress.progress = baseProgress
        generateDisplayValue(currentProgress)
    }

    fun updateBoostedStat(progress: Int = 0) {
        binding.statBarProgress.progress = progress
        generateDisplayValue(progress)
    }

    private fun generateDisplayValue(progress: Int) {
        binding.statBarValue.text = itemView.context.getString(R.string.base_stat_value_template).replace(VirtualPro.BASE_STAT_TEMPLATE_KEY, (progress).toString())
    }
}
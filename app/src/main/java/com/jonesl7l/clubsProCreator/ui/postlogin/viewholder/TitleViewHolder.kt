package com.jonesl7l.clubsProCreator.ui.postlogin.viewholder

import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.jonesl7l.clubsProCreator.R
import com.jonesl7l.clubsProCreator.TemplateApplication
import com.jonesl7l.clubsProCreator.databinding.ItemTitleBinding
import com.jonesl7l.clubsProCreator.util.gone
import com.jonesl7l.clubsProCreator.util.show

class TitleViewHolder(private val binding: ItemTitleBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(displayString: String, isExpanded: Boolean? = null, itemClickInterface: View.OnClickListener?) {
        binding.itemTitleText.text = displayString
        if (isExpanded != null) {
            binding.itemTitleArrow.show()
            binding.itemTitleArrow.rotation = if (isExpanded) IC_EXPANDED_ROTATION_DEG else IC_COLLAPSED_ROTATION_DEG
        } else {
            binding.itemTitleArrow.gone()
        }
        binding.root.setOnClickListener(itemClickInterface)
    }

    fun updateOnTitleChanged(newTitle: String) {
        binding.itemTitleText.text = newTitle
    }

    companion object {
        private const val IC_EXPANDED_ROTATION_DEG = 0F
        private const val IC_COLLAPSED_ROTATION_DEG = 180F
    }
}
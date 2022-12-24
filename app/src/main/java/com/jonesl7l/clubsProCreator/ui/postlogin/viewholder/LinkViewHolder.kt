package com.jonesl7l.clubsProCreator.ui.postlogin.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.jonesl7l.clubsProCreator.databinding.ItemLinkBinding
import com.jonesl7l.clubsProCreator.util.hide
import com.jonesl7l.clubsProCreator.util.showIf

class LinkViewHolder(private val binding: ItemLinkBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(topVisible: Boolean, bottomVisible: Boolean, startVisible: Boolean, endVisible: Boolean) {
        binding.topLinkDivider.showIf(View.INVISIBLE) { topVisible }
        binding.bottomLinkDivider.showIf(View.INVISIBLE) { bottomVisible }
        binding.startLinkDivider.showIf(View.INVISIBLE) { startVisible }
        binding.endLinkDivider.showIf(View.INVISIBLE) { endVisible }
    }
}
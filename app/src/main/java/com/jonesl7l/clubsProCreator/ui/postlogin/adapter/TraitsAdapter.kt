package com.jonesl7l.clubsProCreator.ui.postlogin.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.jonesl7l.clubsProCreator.databinding.ItemLinkBinding
import com.jonesl7l.clubsProCreator.databinding.ItemTraitBinding
import com.jonesl7l.clubsProCreator.model.StatsItemGroups
import com.jonesl7l.clubsProCreator.model.virtualpro.VirtualProTrait
import com.jonesl7l.clubsProCreator.model.virtualpro.VirtualProTraitDivider
import com.jonesl7l.clubsProCreator.model.virtualpro.VirtualProTraitInterface
import com.jonesl7l.clubsProCreator.model.virtualpro.VirtualProTraitItemType
import com.jonesl7l.clubsProCreator.ui.postlogin.viewholder.*
import com.jonesl7l.clubsProCreator.util.orFalse
import com.jonesl7l.clubsProCreator.util.orZero
import com.jonesl7l.clubsProCreator.util.then

class TraitsAdapter(
    private var items: MutableList<VirtualProTraitInterface> = mutableListOf(),
    private var currentTraits: MutableList<VirtualProTrait> = mutableListOf(),
    private val itemClickInterface: TraitClickInterface
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            AdapterViewHolderType.TRAIT.getTypeValue() -> TraitViewHolder(ItemTraitBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            AdapterViewHolderType.LINK.getTypeValue() -> LinkViewHolder(ItemLinkBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            else -> {
                throw Exception("Wrong View Type")
            }
        }


    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is TraitViewHolder) {
            val trait = (items[position] as? VirtualProTrait) ?: return
            val isSelected = currentTraits.find { it.traitType == trait.traitType && it.id == trait.id } != null
            holder.bind(trait, trait.isSelected, trait.canAfford, trait.meetsRequirements, itemClickInterface)
        }
        if (holder is LinkViewHolder) {
            val divider = (items[position] as? VirtualProTraitDivider)
            holder.bind(divider?.topVisible.orFalse(), divider?.bottomVisible.orFalse(), divider?.startVisible.orFalse(), divider?.endVisible.orFalse())
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int, payloads: List<Any>) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            val payload = payloads[0] as Bundle
            for (key in payload.keySet()) {
                if (holder is TraitViewHolder) {
                    when (key) {
                        IS_SELECTED_TRAIT_UPDATE_KEY -> {
                            val trait: VirtualProTrait = (items.getOrNull(position) as? VirtualProTrait?) ?: return
                            holder.updateIsSelected(trait.isSelected)
                        }
                        CAN_AFFORD_TRAIT_UPDATE_KEY,
                        MEETS_REQUIREMENTS_TRAIT_UPDATE_KEY -> {
                            val trait: VirtualProTrait = (items.getOrNull(position) as? VirtualProTrait?) ?: return
                            holder.updateOnClick(trait, itemClickInterface)
                        }
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int = items.size

    override fun getItemViewType(position: Int): Int {
        return if (items.getOrNull(position)?.itemType == VirtualProTraitItemType.TRAIT) {
            AdapterViewHolderType.TRAIT.getTypeValue()
        } else {
            AdapterViewHolderType.LINK.getTypeValue()
        }
    }

    fun updateItems(newItems: List<VirtualProTraitInterface>, newCurrentTraits: List<VirtualProTrait> = mutableListOf(), newCurrentRemainingSkillPoints: Int) {
        currentTraits.clear()
        currentTraits.addAll(newCurrentTraits)

        val diffResult = DiffUtil.calculateDiff(object : DiffUtil.Callback() {

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                val newItem = newItems.getOrNull(newItemPosition)
                val oldItem = items.getOrNull(oldItemPosition)
                return (newItem != null && oldItem != null) &&
                        newItem.itemType == oldItem.itemType
            }

            override fun getOldListSize(): Int = items.size

            override fun getNewListSize(): Int = newItems.size

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                val newItem = newItems.getOrNull(newItemPosition)
                val oldItem = items.getOrNull(oldItemPosition)
                if (newItem?.itemType == VirtualProTraitItemType.TRAIT && oldItem?.itemType == VirtualProTraitItemType.TRAIT) {
                    (newItem as? VirtualProTrait)?.let { newTrait ->
                        (oldItem as? VirtualProTrait)?.let { oldTrait ->
                            newTrait.isSelected == oldTrait.isSelected &&
                                    newTrait.canAfford == oldTrait.canAfford &&
                                    newTrait.meetsRequirements == oldTrait.meetsRequirements
                        }
                    }
                } else if (newItem?.itemType == VirtualProTraitItemType.DIVIDER && oldItem?.itemType == VirtualProTraitItemType.DIVIDER) {
                    (newItem as? VirtualProTraitDivider)?.let { newTrait ->
                        (oldItem as? VirtualProTraitDivider)?.let {
                            return newTrait.startVisible == oldItem.startVisible &&
                                    newTrait.endVisible == oldItem.endVisible &&
                                    newTrait.topVisible == oldItem.topVisible &&
                                    newTrait.bottomVisible == oldItem.bottomVisible
                        }
                    }
                }
                return false
            }

            override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
                val newValue: VirtualProTrait = (newItems.getOrNull(newItemPosition) as? VirtualProTrait) ?: return null
                val oldValue: VirtualProTrait = (items.getOrNull(oldItemPosition) as? VirtualProTrait) ?: return null
                val diff = Bundle()
                if (newValue.isSelected != oldValue.isSelected) {
                    diff.putBoolean(IS_SELECTED_TRAIT_UPDATE_KEY, newValue.isSelected.orFalse())
                }
                if (newValue.canAfford != oldValue.canAfford) {
                    diff.putBoolean(CAN_AFFORD_TRAIT_UPDATE_KEY, newValue.canAfford.orFalse())
                }
                if (newValue.meetsRequirements != oldValue.meetsRequirements) {
                    diff.putBoolean(MEETS_REQUIREMENTS_TRAIT_UPDATE_KEY, newValue.meetsRequirements.orFalse())
                }
                return (diff.isEmpty then null) ?: diff
            }
        })

        diffResult.dispatchUpdatesTo(this)
        items.clear()
        items.addAll(newItems)
    }

    companion object {

        private const val IS_SELECTED_TRAIT_UPDATE_KEY = "isSelected"
        private const val CAN_AFFORD_TRAIT_UPDATE_KEY = "canAfford"
        private const val MEETS_REQUIREMENTS_TRAIT_UPDATE_KEY = "meetsRequirements"
    }
}
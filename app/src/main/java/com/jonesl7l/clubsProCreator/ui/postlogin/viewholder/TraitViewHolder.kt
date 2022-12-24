package com.jonesl7l.clubsProCreator.ui.postlogin.viewholder

import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.jonesl7l.clubsProCreator.R
import com.jonesl7l.clubsProCreator.TemplateApplication.Companion.appContext
import com.jonesl7l.clubsProCreator.databinding.ItemTraitBinding
import com.jonesl7l.clubsProCreator.model.virtualpro.VirtualProTrait
import com.jonesl7l.clubsProCreator.ui.postlogin.adapter.TraitClickInterface
import com.jonesl7l.clubsProCreator.util.toast

class TraitViewHolder(private val binding: ItemTraitBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(trait: VirtualProTrait, isSelected: Boolean, canAfford: Boolean, meetsRequirements: Boolean, itemClickInterface: TraitClickInterface) {
        binding.traitValue.text = trait.name
        binding.traitCost.text = appContext.getString(R.string.trait_cost_template).replace(TRAIT_COST_TEMPLATE_REPLACEMENT_KEY, trait.cost.toString())
        if (isSelected) {
            binding.traitImage.background = ContextCompat.getDrawable(appContext, R.drawable.fill_background)
        } else {
            binding.traitImage.background = ContextCompat.getDrawable(appContext, R.drawable.outline_background)
        }
        binding.root.setOnClickListener {
            if (!canAfford) {
                toast(appContext.getString(R.string.trait_not_enough))
                return@setOnClickListener
            }
            if (!meetsRequirements) {
                toast(appContext.getString(R.string.trait_requirements_not_met))
                return@setOnClickListener
            }
            itemClickInterface.onTraitClick(trait)
        }
    }

    fun updateIsSelected(isSelected: Boolean) {
        if (isSelected) {
            binding.traitImage.background = ContextCompat.getDrawable(appContext, R.drawable.fill_background)
        } else {
            binding.traitImage.background = ContextCompat.getDrawable(appContext, R.drawable.outline_background)
        }
    }

    fun updateOnClick(trait: VirtualProTrait, itemClickInterface: TraitClickInterface) {
        binding.root.setOnClickListener {
            if (!trait.canAfford) {
                toast(appContext.getString(R.string.trait_not_enough))
                return@setOnClickListener
            }
            if (!trait.meetsRequirements) {
                toast(appContext.getString(R.string.trait_requirements_not_met))
                return@setOnClickListener
            }
            itemClickInterface.onTraitClick(trait)
        }
    }

    companion object {
        private const val TRAIT_COST_TEMPLATE_REPLACEMENT_KEY = "{COST}"
    }
}
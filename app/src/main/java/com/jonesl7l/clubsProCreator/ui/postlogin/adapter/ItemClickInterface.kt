package com.jonesl7l.clubsProCreator.ui.postlogin.adapter

import com.jonesl7l.clubsProCreator.model.virtualpro.VirtualProTrait

interface ItemClickInterface {
    fun onItemClick(value: String)
}

interface TraitClickInterface {
    fun onTraitClick(trait: VirtualProTrait)
}
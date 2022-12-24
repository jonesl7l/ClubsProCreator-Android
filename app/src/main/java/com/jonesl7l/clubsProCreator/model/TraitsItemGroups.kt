package com.jonesl7l.clubsProCreator.model

import com.jonesl7l.clubsProCreator.model.virtualpro.VirtualProTraitInterface

interface TraitsItemGroupsInterface {
    var primaryTraits: MutableList<VirtualProTraitInterface>
}

data class PhysicalTraitsItemsGroup(override var primaryTraits: MutableList<VirtualProTraitInterface> = mutableListOf()) : TraitsItemGroupsInterface
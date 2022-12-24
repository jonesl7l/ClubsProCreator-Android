package com.jonesl7l.clubsProCreator.model.virtualpro

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Ignore

@Entity
data class VirtualProTrait(
    @ColumnInfo(name = "id") var id: Int = 0,
    @ColumnInfo(name = "name") var name: String = "",
    @ColumnInfo(name = "cost") var cost: Int = 0,
    @ColumnInfo(name = "prerequisite") var prerequisite: List<Int> = listOf(),
    @Ignore var isSelected: Boolean = false,
    @Ignore var canAfford: Boolean = false,
    @Ignore var meetsRequirements: Boolean = false,
    @Ignore var traitType: VirtualProTraitType = VirtualProTraitType.PHYSICAL_LEFT,
    @Ignore override var itemType: VirtualProTraitItemType = VirtualProTraitItemType.TRAIT,
    @Embedded var physical: PhysicalStats? = null,
    @Embedded var defending: DefendingStats? = null,
    @Embedded var dribbling: DribblingStats? = null,
    @Embedded var passing: PassingStats? = null,
    @Embedded var shooting: ShootingStats? = null,
    @Embedded var pace: PaceStats? = null,
    @Embedded var goalkeeping: GoalkeepingStats? = null
) : VirtualProTraitInterface {

    constructor(virtualProTrait: VirtualProTrait) : this() {
        id = virtualProTrait.id
        name = virtualProTrait.name
        cost = virtualProTrait.cost
        prerequisite = virtualProTrait.prerequisite
        isSelected = virtualProTrait.isSelected
        traitType = virtualProTrait.traitType
        itemType = virtualProTrait.itemType
        physical = virtualProTrait.physical
        defending = virtualProTrait.defending
        dribbling = virtualProTrait.dribbling
        passing = virtualProTrait.passing
        shooting = virtualProTrait.shooting
        pace = virtualProTrait.pace
        goalkeeping = virtualProTrait.goalkeeping
    }
}

data class VirtualProTraitDivider(override var itemType: VirtualProTraitItemType = VirtualProTraitItemType.DIVIDER, var startVisible: Boolean = false, var endVisible: Boolean = false, var topVisible: Boolean = false, var bottomVisible: Boolean = false) :
    VirtualProTraitInterface

interface VirtualProTraitInterface {
    var itemType: VirtualProTraitItemType
}

enum class VirtualProTraitType {
    PHYSICAL_LEFT,
    PHYSICAL_RIGHT
}

enum class VirtualProTraitItemType {
    TRAIT,
    DIVIDER
}
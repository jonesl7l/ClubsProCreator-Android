package com.jonesl7l.clubsProCreator.model.virtualpro

data class VirtualProSkillPoints(
    override var isSelected: Boolean = false,
     var totalSkillPoints: Int = 185,
     var totalTraitsCost: Int = 0
) : ItemInterface {

    companion object {
        const val SKILL_POINTS_BASE_TEMPLATE_KEY = "{BASE_POINTS}"
        const val SKILL_POINTS_TOTAL_TEMPLATE_KEY = "{TOTAL_POINTS}"
    }
}
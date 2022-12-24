package com.jonesl7l.clubsProCreator.model

import com.jonesl7l.clubsProCreator.model.virtualpro.*

data class GlobalVirtualProOptions(
    val heights: List<GlobalVirtualProHeight> = listOf()
)

data class GlobalVirtualProHeight(
    val value: String = "",
    val weights: List<GlobalVirtualProWeight> = listOf()
)

data class GlobalVirtualProWeight(
    val value: String = "",
    val positions: List<GlobalVirtualProPosition> = listOf()
)

data class GlobalVirtualProPosition(
    val value: String = "",
    val physical: PhysicalStats = PhysicalStats(),
    val defending: DefendingStats = DefendingStats(),
    val dribbling: DribblingStats = DribblingStats(),
    val passing: PassingStats = PassingStats(),
    val shooting: ShootingStats = ShootingStats(),
    val pace: PaceStats = PaceStats(),
    val goalkeeping: GoalkeepingStats = GoalkeepingStats()
)


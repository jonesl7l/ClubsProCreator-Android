package com.jonesl7l.clubsProCreator.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.ColumnInfo
import com.jonesl7l.clubsProCreator.data.LoginRepository
import com.jonesl7l.clubsProCreator.data.VirtualProRepository
import com.jonesl7l.clubsProCreator.model.*
import com.jonesl7l.clubsProCreator.model.virtualpro.*
import com.jonesl7l.clubsProCreator.util.Consts
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class VirtualProStatsViewModel @Inject constructor(
    private val repository: VirtualProRepository,
    physicalItemsGroup: PhysicalItemsGroup,
    defendingItemsGroup: DefendingItemsGroup,
    dribblingItemsGroup: DribblingItemsGroup,
    passingItemsGroup: PassingItemsGroup,
    shootingItemsGroup: ShootingItemsGroup,
    paceItemsGroup: PaceItemsGroup
) : ViewModel() {

    //region Physical

    private var _currentVirtualProPhysicalStats: MutableStateFlow<PhysicalItemsGroup> = MutableStateFlow(PhysicalItemsGroup())
    fun getVirtualProPhysicalStats(): StateFlow<PhysicalItemsGroup> = _currentVirtualProPhysicalStats

    //endregion

    //region Defending

    private var _currentVirtualProDefendingStats: MutableStateFlow<DefendingItemsGroup> = MutableStateFlow(DefendingItemsGroup())
    fun getVirtualProDefendingStats(): StateFlow<DefendingItemsGroup> = _currentVirtualProDefendingStats

    //endregion

    //region Dribbling

    private var _currentVirtualProDribblingStats: MutableStateFlow<DribblingItemsGroup> = MutableStateFlow(DribblingItemsGroup())
    fun getVirtualProDribblingStats(): StateFlow<DribblingItemsGroup> = _currentVirtualProDribblingStats

    //endregion

    //region Passing
    private var _currentVirtualProPassingStats: MutableStateFlow<PassingItemsGroup> = MutableStateFlow(PassingItemsGroup())
    fun getVirtualProPassingStats(): StateFlow<PassingItemsGroup> = _currentVirtualProPassingStats

    //endregion

    //region Shooting
    private var _currentVirtualProShootingStats: MutableStateFlow<ShootingItemsGroup> = MutableStateFlow(ShootingItemsGroup())
    fun getVirtualProShootingStats(): StateFlow<ShootingItemsGroup> = _currentVirtualProShootingStats

    //endregion

    //region Pace
    private var _currentVirtualProPaceStats: MutableStateFlow<PaceItemsGroup> = MutableStateFlow(PaceItemsGroup())
    fun getVirtualProPaceStats(): StateFlow<PaceItemsGroup> = _currentVirtualProPaceStats

    //endregion

    //region Goalkeeping
//    private var _currentVirtualProGoalkeepingStat: MutableStateFlow<Int> = MutableStateFlow(0)
//    fun getVirtualProGoalkeepingStat(): StateFlow<Int> = _currentVirtualProPaceStat
    //endregion

    init {
        _currentVirtualProPhysicalStats.value = physicalItemsGroup
        _currentVirtualProDefendingStats.value = defendingItemsGroup
        _currentVirtualProDribblingStats.value = dribblingItemsGroup
        _currentVirtualProPassingStats.value = passingItemsGroup
        _currentVirtualProShootingStats.value = shootingItemsGroup
        _currentVirtualProPaceStats.value = paceItemsGroup
    }

    fun getCurrentVirtualProBuildStats() {
        viewModelScope.launch {
            repository.getCurrentVirtualPro().collect {
                updatePhysicalStats(it.overallBasePhysical(), it.overallPhysical(), it.basePhysical, it.physical())
                updateDefendingStats(it.overallBaseDefending(), it.overallDefending(), it.baseDefending, it.defending())
                updateDribblingStats(it.overallBaseDribbling(), it.overallDribbling(), it.baseDribbling, it.dribbling())
                updatePassingStats(it.overallBasePassing(), it.overallPassing(), it.basePassing, it.passing())
                updateShootingStats(it.overallBaseShooting(), it.overallShooting(), it.baseShooting, it.shooting())
                updatePaceStats(it.overallBasePace(), it.overallPace(), it.basePace, it.pace())
            }
        }
    }

    private fun updatePhysicalStats(baseStat: Int, boostedStat: Int, basePhysicalStats: PhysicalStats, physicalStats: PhysicalStats) {
        val newStats = PhysicalItemsGroup()
        _currentVirtualProPhysicalStats.value.items.forEach {
            (it as? PhysicalItemsGroup)?.let { currentPhysical ->
                newStats.items.add(PhysicalItemsGroup(currentPhysical.title, currentPhysical.baseStat, currentPhysical.stat))
            }
        }
        newStats.items.forEachIndexed { index, statsItemGroups ->
            var baseStatValue = 0
            var boostedStatValue = 0
            when (index) {
                0 -> {
                    baseStatValue = basePhysicalStats.jumping
                    boostedStatValue = physicalStats.jumping
                }
                1 -> {
                    baseStatValue = basePhysicalStats.stamina
                    boostedStatValue = physicalStats.stamina
                }
                2 -> {
                    baseStatValue = basePhysicalStats.strength
                    boostedStatValue = physicalStats.strength
                }
                3 -> {
                    baseStatValue = basePhysicalStats.reactions
                    boostedStatValue = physicalStats.reactions
                }
                4 -> {
                    baseStatValue = basePhysicalStats.aggression
                    boostedStatValue = physicalStats.aggression
                }
            }
            statsItemGroups.baseStat = baseStatValue
            statsItemGroups.stat = boostedStatValue
        }
        newStats.title = _currentVirtualProPhysicalStats.value.title
        newStats.baseStat = baseStat
        newStats.stat = boostedStat
        _currentVirtualProPhysicalStats.value = newStats
    }

    private fun updateDefendingStats(baseStat: Int, boostedStat: Int, baseDefendingStats: DefendingStats, defendingStats: DefendingStats) {
        val newStats = DefendingItemsGroup()
        _currentVirtualProDefendingStats.value.items.forEach {
            (it as? DefendingItemsGroup)?.let { currentStat ->
                newStats.items.add(DefendingItemsGroup(currentStat.title, currentStat.baseStat, currentStat.stat))
            }
        }
        newStats.items.forEachIndexed { index, statsItemGroups ->
            var baseStatValue = 0
            var boostedStatValue = 0
            when (index) {
                0 -> {
                    baseStatValue = baseDefendingStats.defAwareness
                    boostedStatValue = defendingStats.defAwareness
                }
                1 -> {
                    baseStatValue = baseDefendingStats.interceptions
                    boostedStatValue = defendingStats.interceptions
                }
                2 -> {
                    baseStatValue = baseDefendingStats.standingTackle
                    boostedStatValue = defendingStats.standingTackle
                }
                3 -> {
                    baseStatValue = baseDefendingStats.standingTackle
                    boostedStatValue = defendingStats.standingTackle
                }
            }
            statsItemGroups.baseStat = baseStatValue
            statsItemGroups.stat = boostedStatValue
        }
        newStats.title = _currentVirtualProDefendingStats.value.title
        newStats.baseStat = baseStat
        newStats.stat = boostedStat
        _currentVirtualProDefendingStats.value = newStats
    }

    private fun updateDribblingStats(baseStat: Int, boostedStat: Int, baseDribblingStats: DribblingStats, dribblingStats: DribblingStats) {
        val newStats = DribblingItemsGroup()
        _currentVirtualProDribblingStats.value.items.forEach {
            (it as? DribblingItemsGroup)?.let { currentStat ->
                newStats.items.add(DribblingItemsGroup(currentStat.title, currentStat.baseStat, currentStat.stat))
            }
        }
        newStats.items.forEachIndexed { index, statsItemGroups ->
            var baseStatValue = 0
            var boostedStatValue = 0
            when (index) {
                0 -> {
                    baseStatValue = baseDribblingStats.agility
                    boostedStatValue = dribblingStats.agility
                }
                1 -> {
                    baseStatValue = baseDribblingStats.balance
                    boostedStatValue = dribblingStats.balance
                }
                2 -> {
                    baseStatValue = baseDribblingStats.attPosition
                    boostedStatValue = dribblingStats.attPosition
                }
                3 -> {
                    baseStatValue = baseDribblingStats.ballControl
                    boostedStatValue = dribblingStats.ballControl
                }
                4 -> {
                    baseStatValue = baseDribblingStats.dribbling
                    boostedStatValue = dribblingStats.dribbling
                }
                5 -> {
                    baseStatValue = baseDribblingStats.skillMoves
                    boostedStatValue = dribblingStats.skillMoves
                }
            }
            statsItemGroups.baseStat = baseStatValue
            statsItemGroups.stat = boostedStatValue
        }
        newStats.title = _currentVirtualProDribblingStats.value.title
        newStats.baseStat = baseStat
        newStats.stat = boostedStat
        _currentVirtualProDribblingStats.value = newStats
    }

    private fun updatePassingStats(baseStat: Int, boostedStat: Int, basePassingStats: PassingStats, passingStats: PassingStats) {
        val newStats = PassingItemsGroup()
        _currentVirtualProPassingStats.value.items.forEach {
            (it as? PassingItemsGroup)?.let { currentStat ->
                newStats.items.add(PassingItemsGroup(currentStat.title, currentStat.baseStat, currentStat.stat))
            }
        }
        newStats.items.forEachIndexed { index, statsItemGroups ->
            var baseStatValue = 0
            var boostedStatValue = 0
            when (index) {
                0 -> {
                    baseStatValue = basePassingStats.vision
                    boostedStatValue = passingStats.vision
                }
                1 -> {
                    baseStatValue = basePassingStats.crossing
                    boostedStatValue = passingStats.crossing
                }
                2 -> {
                    baseStatValue = basePassingStats.shortPass
                    boostedStatValue = passingStats.shortPass
                }
                3 -> {
                    baseStatValue = basePassingStats.longPass
                    boostedStatValue = passingStats.longPass
                }
                4 -> {
                    baseStatValue = basePassingStats.curve
                    boostedStatValue = passingStats.curve
                }
            }
            statsItemGroups.baseStat = baseStatValue
            statsItemGroups.stat = boostedStatValue
        }
        newStats.title = _currentVirtualProPassingStats.value.title
        newStats.baseStat = baseStat
        newStats.stat = boostedStat
        _currentVirtualProPassingStats.value = newStats
    }

    private fun updateShootingStats(baseStat: Int, boostedStat: Int, baseShootingStats: ShootingStats, shootingStats: ShootingStats) {
        val newStats = ShootingItemsGroup()
        _currentVirtualProShootingStats.value.items.forEach {
            (it as? ShootingItemsGroup)?.let { currentStat ->
                newStats.items.add(ShootingItemsGroup(currentStat.title, currentStat.baseStat, currentStat.stat))
            }
        }
        newStats.items.forEachIndexed { index, statsItemGroups ->
            var baseStatValue = 0
            var boostedStatValue = 0
            when (index) {
                0 -> {
                    baseStatValue = baseShootingStats.finishing
                    boostedStatValue = shootingStats.finishing
                }
                1 -> {
                    baseStatValue = baseShootingStats.fkAccuracy
                    boostedStatValue = shootingStats.fkAccuracy
                }
                2 -> {
                    baseStatValue = baseShootingStats.headingAccuracy
                    boostedStatValue = shootingStats.headingAccuracy
                }
                3 -> {
                    baseStatValue = baseShootingStats.shotPower
                    boostedStatValue = shootingStats.shotPower
                }
                4 -> {
                    baseStatValue = baseShootingStats.longShot
                    boostedStatValue = shootingStats.longShot
                }
                5 -> {
                    baseStatValue = baseShootingStats.volleys
                    boostedStatValue = shootingStats.volleys
                }
                6 -> {
                    baseStatValue = baseShootingStats.penalties
                    boostedStatValue = shootingStats.penalties
                }
                7 -> {
                    baseStatValue = baseShootingStats.weakFoot
                    boostedStatValue = shootingStats.weakFoot
                }
            }
            statsItemGroups.baseStat = baseStatValue
            statsItemGroups.stat = boostedStatValue
        }
        newStats.title = _currentVirtualProShootingStats.value.title
        newStats.baseStat = baseStat
        newStats.stat = boostedStat
        _currentVirtualProShootingStats.value = newStats
    }

    private fun updatePaceStats(baseStat: Int, boostedStat: Int, basePaceStats: PaceStats, paceStats: PaceStats) {
        val newStats = PaceItemsGroup()
        _currentVirtualProPaceStats.value.items.forEach {
            (it as? PaceItemsGroup)?.let { currentStat ->
                newStats.items.add(PaceItemsGroup(currentStat.title, currentStat.baseStat, currentStat.stat))
            }
        }
        newStats.items.forEachIndexed { index, statsItemGroups ->
            var baseStatValue = 0
            var boostedStatValue = 0
            when (index) {
                0 -> {
                    baseStatValue = basePaceStats.acceleration
                    boostedStatValue = paceStats.acceleration
                }
                1 -> {
                    baseStatValue = basePaceStats.sprintSpeed
                    boostedStatValue = paceStats.sprintSpeed
                }
            }
            statsItemGroups.baseStat = baseStatValue
            statsItemGroups.stat = boostedStatValue
        }
        newStats.title = _currentVirtualProPaceStats.value.title
        newStats.baseStat = baseStat
        newStats.stat = boostedStat
        _currentVirtualProPaceStats.value = newStats
    }

    private fun updateGoalkeepingStats(baseStat: Int, boostedStat: Int) {
    }
}
package com.jonesl7l.clubsProCreator.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jonesl7l.clubsProCreator.data.VirtualProRepository
import com.jonesl7l.clubsProCreator.model.*
import com.jonesl7l.clubsProCreator.model.virtualpro.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VirtualProTraitsViewModel @Inject constructor(private val repository: VirtualProRepository, physicalTraitsItemsGroup: PhysicalTraitsItemsGroup) : ViewModel() {

    private var _currentVirtualProPhysicalTraits: MutableStateFlow<PhysicalTraitsItemsGroup> = MutableStateFlow(PhysicalTraitsItemsGroup())
    private var _currentVirtualProSelectedTraits: MutableStateFlow<MutableList<VirtualProTrait>> = MutableStateFlow(mutableListOf())
    private var _currentVirtualProRemainingSkillPoints: Int = 0

    init {
        _currentVirtualProPhysicalTraits.value = physicalTraitsItemsGroup
    }

    fun getVirtualProPhysicalTraits(): StateFlow<PhysicalTraitsItemsGroup> = _currentVirtualProPhysicalTraits
    fun getVirtualProSelectedTraits(): StateFlow<MutableList<VirtualProTrait>> = _currentVirtualProSelectedTraits
    fun getCurrentVirtualProRemainingSkillPoints(): Int = _currentVirtualProRemainingSkillPoints

    fun getCurrentVirtualProBuildStats() {
        viewModelScope.launch {
            repository.getCurrentVirtualPro().collect {
                var totalSkillPointsCost = 0
                it.selectedTraits.forEach { trait -> totalSkillPointsCost += trait.cost }
                _currentVirtualProRemainingSkillPoints = it.totalSkillPoints - totalSkillPointsCost
                val newTraits = mutableListOf<VirtualProTrait>()
                newTraits.addAll(it.selectedTraits)
                updatePhysicalLeft(it.selectedTraits.filter { selectedTrait -> selectedTrait.traitType == VirtualProTraitType.PHYSICAL_LEFT })
                _currentVirtualProSelectedTraits.value = newTraits
            }
        }
    }

    fun onTraitClicked(trait: VirtualProTrait) {
        repository.onTraitClicked(trait)
    }

    private fun updatePhysicalLeft(selectedPhysicalTraits: List<VirtualProTrait>?) {
        val newStats = PhysicalTraitsItemsGroup()
        _currentVirtualProPhysicalTraits.value.primaryTraits.forEach {
            when (it) {
                is VirtualProTrait -> {
                    VirtualProTrait(it).also { trait ->
                        trait.isSelected = selectedPhysicalTraits?.find { selectedTrait -> it.id == selectedTrait.id } != null
                        trait.canAfford = (_currentVirtualProRemainingSkillPoints >= trait.cost)
                        trait.meetsRequirements = trait.prerequisite.isEmpty() || selectedPhysicalTraits?.find { it.traitType == trait.traitType && trait.prerequisite.contains(it.id) } != null
                        newStats.primaryTraits.add(trait)
                    }
                }
                is VirtualProTraitDivider -> newStats.primaryTraits.add(VirtualProTraitDivider(it.itemType, it.startVisible, it.endVisible, it.topVisible, it.bottomVisible))
            }
        }
        _currentVirtualProPhysicalTraits.value = newStats
    }
}
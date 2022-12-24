package com.jonesl7l.clubsProCreator.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
class VirtualProBuildSelectionViewModel @Inject constructor(
    private val repository: VirtualProRepository,
    heightItemsGroup: HeightItemsGroup,
    weightItemsGroup: WeightItemsGroup,
    positionItemsGroup: PositionItemsGroup
) : ViewModel() {

    private var _currentVirtualProHeightStats: MutableStateFlow<HeightItemsGroup> = MutableStateFlow(HeightItemsGroup())
    private var _currentVirtualProWeightStats: MutableStateFlow<WeightItemsGroup> = MutableStateFlow(WeightItemsGroup())
    private var _currentVirtualProPositionStats: MutableStateFlow<PositionItemsGroup> = MutableStateFlow(PositionItemsGroup())

    //region Build
    private var _currentVirtualProSkillPoints: MutableStateFlow<VirtualProSkillPoints> = MutableStateFlow(VirtualProSkillPoints())
    private var _currentVirtualProRunningStyle: MutableStateFlow<VirtualProRunningStyle> = MutableStateFlow(VirtualProRunningStyle())
    private var _currentVirtualProPreferredFooted: MutableStateFlow<VirtualProPreferredFoot> = MutableStateFlow(VirtualProPreferredFoot())
    //endregion

    fun getVirtualProHeightOptions(): StateFlow<HeightItemsGroup> = _currentVirtualProHeightStats
    fun getVirtualProWeightOptions(): StateFlow<WeightItemsGroup> = _currentVirtualProWeightStats
    fun getVirtualProPositionOptions(): StateFlow<PositionItemsGroup> = _currentVirtualProPositionStats
    fun getVirtualProSkillPoints(): StateFlow<VirtualProSkillPoints> = _currentVirtualProSkillPoints
    fun getVirtualProRunningStyle(): StateFlow<VirtualProRunningStyle> = _currentVirtualProRunningStyle
    fun getVirtualProPreferredFootLeft(): StateFlow<VirtualProPreferredFoot> = _currentVirtualProPreferredFooted

    init {
        _currentVirtualProHeightStats.value = heightItemsGroup
        _currentVirtualProWeightStats.value = weightItemsGroup
        _currentVirtualProPositionStats.value = positionItemsGroup
    }

    fun getCurrentVirtualProBuildStats() {
        viewModelScope.launch {
            repository.getCurrentVirtualPro().collect {
                updateHeight(it.height)
                updateWeight(it.weight)
                updatePosition(it.position)
                _currentVirtualProSkillPoints.value = VirtualProSkillPoints(totalSkillPoints = it.totalSkillPoints, totalTraitsCost = it.totalTraitsCost())
                _currentVirtualProRunningStyle.value = VirtualProRunningStyle(currentType = it.runningStyle())
                _currentVirtualProPreferredFooted.value = it.preferredFoot
            }
        }
    }

    private fun updateHeight(newHeight: VirtualProHeight) {
        val newStats = HeightItemsGroup("", mutableListOf())
        _currentVirtualProHeightStats.value.items.forEach {
            (it as? VirtualProHeight)?.let { currentHeight ->
                newStats.items.add(VirtualProHeight(currentHeight.isSelected, currentHeight.currentType))
            }
        }
        newStats.items.find { it.isSelected }?.let { it.isSelected = !it.isSelected }
        newStats.items.find { it.getFullIdentifier() == newHeight.getFullIdentifier() }?.let {
            it.isSelected = true
            val item: VirtualProHeight? = it as? VirtualProHeight
            val displayValue = if (Consts.isUsingInches) {
                item?.getInchesValue().orEmpty()
            } else {
                item?.getCmValue().orEmpty()
            }
            newStats.title = displayValue
            _currentVirtualProHeightStats.value = newStats
        }
    }

    private fun updateWeight(newWeight: VirtualProWeight) {
        val newStats = WeightItemsGroup("", mutableListOf())
        _currentVirtualProWeightStats.value.items.forEach {
            (it as? VirtualProWeight)?.let { currentWeight -> newStats.items.add(VirtualProWeight(currentWeight.isSelected, currentWeight.currentType)) }
        }
        newStats.items.find { it.isSelected }?.let { it.isSelected = !it.isSelected }
        newStats.items.find { newWeight.getFullIdentifier() == it.getFullIdentifier() }?.let {
            it.isSelected = true
            val item: VirtualProWeight? = it as? VirtualProWeight
            val displayValue = if (Consts.isUsingLbs) {
                item?.getPoundsValue().orEmpty()
            } else {
                item?.getGramsValue().orEmpty()
            }
            newStats.title = displayValue
            _currentVirtualProWeightStats.value = newStats
        }
    }

    private fun updatePosition(newPosition: VirtualProPosition) {
        val newStats = PositionItemsGroup("", mutableListOf())
        _currentVirtualProPositionStats.value.items.forEach {
            (it as? VirtualProPosition)?.let { currentPosition -> newStats.items.add(VirtualProPosition(currentPosition.isSelected, currentPosition.currentType)) }
        }
        newStats.items.find { it.isSelected }?.let { it.isSelected = !it.isSelected }
        newStats.items.find { newPosition.getFullIdentifier() == it.getFullIdentifier() }?.let {
            it.isSelected = true
            newStats.title = newPosition.getPositionValue()
            _currentVirtualProPositionStats.value = newStats
        }
    }

    fun onHeightSelected(newHeightId: Int) {
        repository.onHeightSelected(newHeightId)
    }

    fun onWeightSelected(newWeightId: Int) {
        repository.onWeightSelected(newWeightId)
    }

    fun onPositionSelected(newPositionId: Int) {
        repository.onPositionSelected(newPositionId)
    }

    fun onPreferredFootChanged(newFootId: Int) {
//        repository.onPreferredFootChanged(newFootId)
    }
}
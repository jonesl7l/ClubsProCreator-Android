package com.jonesl7l.clubsProCreator.data

import androidx.room.Room
import com.jonesl7l.clubsProCreator.TemplateApplication.Companion.appContext
import com.jonesl7l.clubsProCreator.model.*
import com.jonesl7l.clubsProCreator.model.virtualpro.*
import com.jonesl7l.clubsProCreator.room.AppDatabase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.*
import javax.inject.Inject


class VirtualProDataSource @Inject constructor() {

    private val virtualProDatabaseDao = Room.databaseBuilder(appContext, AppDatabase::class.java, DATABASE_ID).build().virtualProDao()

    private val virtualPros: MutableList<VirtualPro> = mutableListOf()

    private val _virtualProOptions: MutableStateFlow<GlobalVirtualProOptions> = MutableStateFlow(GlobalVirtualProOptions())
    private var _currentVirtualPro: MutableStateFlow<VirtualPro> = MutableStateFlow(VirtualPro())

    fun getCurrentVirtualPro(): StateFlow<VirtualPro> = _currentVirtualPro
    fun getVirtualProOptions(): StateFlow<GlobalVirtualProOptions> = _virtualProOptions

    fun setGlobalVirtualProOptions(globalVirtualProOptions: GlobalVirtualProOptions) {
        _virtualProOptions.value = globalVirtualProOptions
        initCurrentPro()
    }

    fun onHeightSelected(newHeightId: Int) {
        val newPro = VirtualPro(_currentVirtualPro.value)
        EnumSet.allOf(VirtualProHeightType::class.java).toList().find { it.id == newHeightId }?.let { newPro.height.currentType = it }
        setNewProStats(newPro)
        _currentVirtualPro.value = newPro
    }

    fun onWeightSelected(newWeightId: Int) {
        val newPro = VirtualPro(_currentVirtualPro.value)
        EnumSet.allOf(VirtualProWeightType::class.java).toList().find { it.id == newWeightId }?.let { newPro.weight.currentType = it }
        setNewProStats(newPro)
        _currentVirtualPro.value = newPro
    }

    fun onPositionSelected(newPositionId: Int) {
        val newPro = VirtualPro(_currentVirtualPro.value)
        EnumSet.allOf(VirtualProPositionType::class.java).toList().find { it.id == newPositionId }?.let { newPro.position.currentType = it }
        setNewProStats(newPro)
        _currentVirtualPro.value = newPro
    }

    fun onTraitClicked(trait: VirtualProTrait) {
        val newPro = VirtualPro(_currentVirtualPro.value)
        newPro.selectedTraits
        newPro.selectedTraits.find { it.traitType == trait.traitType && it.id == trait.id }.let { newTrait ->
            if (newTrait != null) {
                newPro.selectedTraits.filter { it.prerequisite.contains(newTrait.id) }.let { newPro.selectedTraits.removeAll(it) }
                newPro.selectedTraits.remove(newTrait)
            } else {
                newPro.selectedTraits.add(trait)
            }
        }



        newPro.selectedTraits //todo prerequisite stuff
        setNewProStats(newPro)
        _currentVirtualPro.value = newPro
    }

    //region Room

    fun fetchVirtualProsLocally(): List<VirtualPro> {
        virtualPros.addAll(virtualProDatabaseDao.getAll())
        return virtualPros
    }

    fun saveVirtualProLocally(virtualPro: VirtualPro) {
        virtualProDatabaseDao.insertAll(virtualPro)
    }

    fun deleteVirtualProLocally(virtualPro: VirtualPro) {
        virtualProDatabaseDao.delete(virtualPro)
    }

    fun findVirtualProByNameLocally(name: String): VirtualPro {
        return virtualProDatabaseDao.findByName(name)
    }

    //endregion

    private fun initCurrentPro() {
        _currentVirtualPro.value.let {
            onHeightSelected(it.height.currentType.id)
            onWeightSelected(it.weight.currentType.id)
            onPositionSelected(it.position.currentType.id)
        }
    }

    private fun setNewProStats(newPro: VirtualPro) {
        _virtualProOptions.value.heights.find {
            it.value == newPro.height.getInchesValue()
        }?.let { globalHeight ->
            globalHeight.weights.find { globalWeight ->
                globalWeight.value == newPro.weight.getPoundsValue()
            }?.positions?.find {
                it.value == newPro.position.getPositionValue()
            }?.let {
                newPro.basePhysical = PhysicalStats(it.physical)
                newPro.baseDefending = DefendingStats(it.defending)
                newPro.baseDribbling = DribblingStats(it.dribbling)
                newPro.basePassing = PassingStats(it.passing)
                newPro.baseShooting = ShootingStats(it.shooting)
                newPro.basePace = PaceStats(it.pace)
                newPro.baseGoalkeeping = GoalkeepingStats(it.goalkeeping)
            }
        }
    }

    companion object {
        private const val DATABASE_ID: String = "virtual-pro-database"
    }
}
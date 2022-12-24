package com.jonesl7l.clubsProCreator.data

import com.jonesl7l.clubsProCreator.model.GlobalVirtualProOptions
import com.jonesl7l.clubsProCreator.model.RestResult
import com.jonesl7l.clubsProCreator.model.virtualpro.VirtualPro
import com.jonesl7l.clubsProCreator.model.virtualpro.VirtualProTrait
import com.jonesl7l.clubsProCreator.model.virtualpro.VirtualProTraitType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VirtualProRepository @Inject constructor(private val virtualProDataSource: VirtualProDataSource, private val firebaseDataSource: FirebaseDataSource) {

    fun getCurrentVirtualPro(): StateFlow<VirtualPro> = virtualProDataSource.getCurrentVirtualPro()

    fun onHeightSelected(newHeightId: Int) {
        virtualProDataSource.onHeightSelected(newHeightId)
    }

    fun onWeightSelected(newWeightId: Int) {
        virtualProDataSource.onWeightSelected(newWeightId)
    }

    fun onPositionSelected(newPositionId: Int) {
        virtualProDataSource.onPositionSelected(newPositionId)
    }

    fun onTraitClicked(trait: VirtualProTrait) {
        virtualProDataSource.onTraitClicked(trait)
    }

    //region Room

    fun fetchVirtualProsLocally(): List<VirtualPro> = virtualProDataSource.fetchVirtualProsLocally()

    fun saveVirtualProLocally(virtualPro: VirtualPro) = virtualProDataSource.saveVirtualProLocally(virtualPro)

    fun deleteVirtualProLocally(virtualPro: VirtualPro) = virtualProDataSource.deleteVirtualProLocally(virtualPro)

    fun findVirtualProByNameLocally(name: String): VirtualPro = virtualProDataSource.findVirtualProByNameLocally(name)


    //endregion

    //region Firebase

    suspend fun fetchGlobalProStats(): Flow<RestResult<GlobalVirtualProOptions?>> {
        return flow {
            emit(RestResult.Loading)
            val result = firebaseDataSource.fetchGlobalProStats()
            if (result is RestResult.Success) {
                result.data?.let { virtualProDataSource.setGlobalVirtualProOptions(it) }
            }
            emit(result)
        }.flowOn(Dispatchers.IO)
    }

    //endregion

    fun get() {

    }
}
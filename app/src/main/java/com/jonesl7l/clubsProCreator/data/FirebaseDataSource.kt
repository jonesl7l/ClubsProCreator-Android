package com.jonesl7l.clubsProCreator.data

import com.google.firebase.storage.FirebaseStorage
import com.jonesl7l.clubsProCreator.model.GlobalVirtualProOptions
import com.jonesl7l.clubsProCreator.model.RestResult
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import kotlinx.coroutines.tasks.await
import timber.log.Timber
import javax.inject.Inject


class FirebaseDataSource @Inject constructor() {

    private val mFirebaseStorage: FirebaseStorage by lazy { FirebaseStorage.getInstance() }

    //region Firebase

    suspend fun fetchGlobalProStats(): RestResult<GlobalVirtualProOptions?> {
        return try {
            val data = mFirebaseStorage.reference
                .child(GLOBAL_PRO_STATS)
                .getBytes(ONE_MEGABYTE)
                .await()
            val parsedData = parseGlobalVirtualProJsonResponseBody(String(data, Charsets.UTF_8))
            RestResult.Success(parsedData)
        } catch (exception: Exception) {
            Timber.e(exception)
            RestResult.ApiError(exception.message.orEmpty())
        }
    }

    //endregion

    private fun parseGlobalVirtualProJsonResponseBody(json: String): GlobalVirtualProOptions? {
        val moshi = Moshi.Builder().build()
        val jsonAdapter: JsonAdapter<GlobalVirtualProOptions> = moshi.adapter(GlobalVirtualProOptions::class.java)
        return jsonAdapter.fromJson(json)
    }

    companion object {
        private const val ONE_MEGABYTE: Long = 1024 * 1024
        private const val GLOBAL_PRO_STATS: String = "global/clubs_pro_stats.json"
        private const val DATABASE_ID: String = "virtual-pro-database"
    }
}
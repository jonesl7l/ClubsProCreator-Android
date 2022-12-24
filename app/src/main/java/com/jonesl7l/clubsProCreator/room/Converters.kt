package com.jonesl7l.clubsProCreator.room

import androidx.room.TypeConverter
import com.jonesl7l.clubsProCreator.model.virtualpro.VirtualProTrait
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi

object Converters {

    @TypeConverter
    fun listToJsonString(value: MutableList<VirtualProTrait>?): String {
        val moshi = Moshi.Builder().build()
        val jsonAdapter: JsonAdapter<Array<VirtualProTrait>> = moshi.adapter(Array<VirtualProTrait>::class.java)
        (value as? Array<VirtualProTrait>)?.let {
            return jsonAdapter.toJson(it)
        }
        return ""
    }

    @TypeConverter
    fun jsonStringToList(json: String): MutableList<VirtualProTrait> {
        val moshi = Moshi.Builder().build()
        val jsonAdapter: JsonAdapter<Array<VirtualProTrait>> = moshi.adapter(Array<VirtualProTrait>::class.java)
        return jsonAdapter.fromJson(json)?.toMutableList() ?: mutableListOf()
    }
}
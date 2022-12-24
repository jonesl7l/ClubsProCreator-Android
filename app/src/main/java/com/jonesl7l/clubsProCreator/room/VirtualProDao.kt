package com.jonesl7l.clubsProCreator.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.jonesl7l.clubsProCreator.model.virtualpro.VirtualPro

@Dao
interface VirtualProDao {
    @Query("SELECT * FROM virtualpro")
    fun getAll(): List<VirtualPro>

    @Query("SELECT * FROM virtualpro WHERE name IN (:names)")
    fun loadAllByIds(names: List<String>): List<VirtualPro>

    @Query("SELECT * FROM virtualpro WHERE name LIKE :name LIMIT 1")
    fun findByName(name: String): VirtualPro

    @Insert
    fun insertAll(vararg virtualPros: VirtualPro)

    @Delete
    fun delete(virtualPro: VirtualPro)
}
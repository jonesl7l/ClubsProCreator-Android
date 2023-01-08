package com.jonesl7l.clubsProCreator.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.jonesl7l.clubsProCreator.model.virtualpro.VirtualPro

@Database(entities = [VirtualPro::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun virtualProDao(): VirtualProDao
}
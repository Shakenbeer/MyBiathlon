package com.shakenbeer.biathlon.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.shakenbeer.biathlon.model.Event
import com.shakenbeer.biathlon.model.Race

@Database(entities = [Event::class, Race::class], version = 1)
@TypeConverters(value = [Converters::class])
abstract class BiathlonDatabase: RoomDatabase() {

    abstract fun eventDao(): EventDao

    abstract fun raceDao(): RaceDao
}
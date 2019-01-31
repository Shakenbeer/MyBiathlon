package com.shakenbeer.biathlon.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.shakenbeer.biathlon.model.Race

@Dao
interface RaceDao {

    @Insert
    fun save(races: List<Race>)

    @Query("select * from race where eventId = :eventId order by startTime")
    fun loadEventRaces(eventId: String): List<Race>

    @Query("select count(*) from race where eventId = :eventId")
    fun getRacesCount(eventId: String): Int
}
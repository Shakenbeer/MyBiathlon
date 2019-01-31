package com.shakenbeer.biathlon.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.shakenbeer.biathlon.model.Event

@Dao
interface EventDao {

    @Insert(onConflict = REPLACE)
    fun save(events: List<Event>)

    @Query("select * from event where seasonId = :seasonId order by startDate")
    fun loadSeasonEvents(seasonId: String): List<Event>

    @Query("select count(*) from event where seasonId = :seasonId")
    fun getEventsCount(seasonId: String): Int
}
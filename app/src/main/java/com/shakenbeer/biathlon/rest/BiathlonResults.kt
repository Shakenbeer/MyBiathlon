package com.shakenbeer.biathlon.rest

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import com.shakenbeer.biathlon.rest.model.SIWIEvent
import com.shakenbeer.biathlon.rest.model.SIWIRace

interface BiathlonResults {

    @GET("Events")
    fun events(@Query("SeasonId") seasonId: String, @Query("Level") level: Int): Call<List<SIWIEvent>>

    @GET("Competitions")
    fun races(@Query("EventId") eventId: String): Call<List<SIWIRace>>
}

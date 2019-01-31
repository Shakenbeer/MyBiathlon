package com.shakenbeer.biathlon.rest.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class SIWIEvent(
        @SerializedName("SeasonId")
        val seasonId: String?,
        @SerializedName("EventId")
        val eventId: String?,
        @SerializedName("StartDate")
        val startDate: Date?,
        @SerializedName("EndDate")
        val endDate: Date?,
        @SerializedName("Description")
        val description: String?,
        @SerializedName("ShortDescription")
        val shortDescription: String?,
        @SerializedName("Organizer")
        val organizer: String?,
        @SerializedName("Nat")
        val nat: String?,
        @SerializedName("MedalSetId")
        val medalSetId: Any?,
        @SerializedName("EventClassificationId")
        val eventClassificationId: String?,
        @SerializedName("Level")
        val level: Int?,
        @SerializedName("UTCOffset")
        val utcOffset: Int?,
        @SerializedName("IsActual")
        val isIsActual: Boolean?,
        @SerializedName("IsCurrent")
        val isIsCurrent: Boolean?)

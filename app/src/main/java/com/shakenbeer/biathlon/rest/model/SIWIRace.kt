package com.shakenbeer.biathlon.rest.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class SIWIRace(

        @SerializedName("km")
        val km: String?,

        @SerializedName("Description")
        val description: String?,

        @SerializedName("StatusText")
        val statusText: String?,

        @SerializedName("DisciplineId")
        val disciplineId: String?,

        @SerializedName("HasLiveData")
        val hasLiveData: Boolean?,

        @SerializedName("StartTime")
        val startTime: Date?,

        @SerializedName("ShortDescription")
        val shortDescription: String?,

        @SerializedName("catId")
        val catId: String?,

        @SerializedName("ResultsCredit")
        val resultsCredit: Any?,

        @SerializedName("TimingCredit")
        val timingCredit: Any?,

        @SerializedName("HasAnalysis")
        val hasAnalysis: Boolean?,

        @SerializedName("StatusId")
        val statusId: Int?,

        @SerializedName("IsLive")
        val isLive: Boolean?,

        @SerializedName("RaceId")
        val raceId: String?
)
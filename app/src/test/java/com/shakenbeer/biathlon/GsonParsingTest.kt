@file:Suppress("RemoveRedundantBackticks")

package com.shakenbeer.biathlon

import com.google.gson.GsonBuilder
import com.shakenbeer.biathlon.rest.model.SIWIEvent
import com.shakenbeer.biathlon.shared.Const
import com.shakenbeer.biathlon.shared.dates
import org.apache.commons.lang3.time.DateUtils
import org.junit.Assert.assertEquals
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.*
import java.util.Calendar.DATE

@Suppress("IllegalIdentifier", "RemoveRedundantBackticks")
class GsonParsingTest {
    @Test
    @Throws(Exception::class)
    fun `check gson date conversion`() {

        val simpleDateFormat = SimpleDateFormat(Const.SIWI_DATE_FORMAT)

        println("Hello, World " + simpleDateFormat.parse("2017-11-24T12:00:00Z")?.time)

        println("Hello, World 2 " + DateUtils.truncate(simpleDateFormat.parse("2017-11-24T12:00:00Z"), DATE))

        val gson = GsonBuilder()
                .setDateFormat(Const.SIWI_DATE_FORMAT)
                .create()

        val json = "{\"datetime\" : \"2017-11-24T12:00:00Z\"}"

        val withDate = gson.fromJson(json, WithDate::class.java)

        assertEquals(1511524800000, withDate.datetime.time)
    }

    @Test
    fun `check Event parsing with valid data`() {
        val gson = GsonBuilder()
                .setDateFormat(Const.SIWI_DATE_FORMAT)
                .create()

        val json = """
            {
                "SeasonId": "1718",
                "EventId": "BT1718SWRLCP01",
                "StartDate": "2017-11-24T12:00:00Z",
                "EndDate": "2017-12-03T12:00:00Z",
                "Description": "BMW IBU World Cup Biathlon 1",
                "ShortDescription": "Oestersund",
                "Organizer": "Oestersund",
                "Nat": "SWE",
                "MedalSetId": null,
                "EventClassificationId": "BTSWRLCP",
                "Level": 1,
                "UTCOffset": 1,
                "IsActual": false,
                "IsCurrent": true
            }
            """
        val event = gson.fromJson(json, SIWIEvent::class.java)

        println(event.dates)

        assertEquals("24 Nov - 3 Dec 2017", event.dates)
    }

    @Test
    fun `check Event parsing with no start data`() {
        val gson = GsonBuilder()
                .setDateFormat(Const.SIWI_DATE_FORMAT)
                .create()

        val json = """
            {
                "SeasonId": "1718",
                "EventId": "BT1718SWRLCP01",
                "EndDate": "2017-12-03T12:00:00Z",
                "Description": "BMW IBU World Cup Biathlon 1",
                "ShortDescription": "Oestersund",
                "Organizer": "Oestersund",
                "Nat": "SWE",
                "MedalSetId": null,
                "EventClassificationId": "BTSWRLCP",
                "Level": 1,
                "UTCOffset": 1,
                "IsActual": false,
                "IsCurrent": true
            }
            """
        val event = gson.fromJson(json, SIWIEvent::class.java)

        assertEquals("", event.dates)
    }

    data class WithDate (val datetime: Date)
}

package com.shakenbeer.biathlon.model

import java.util.*

sealed class WhatsNext
class PastSeason(val startDate: Date, val endDate: Date): WhatsNext()
class NextSeason(val startTime: Date): WhatsNext()
class NextEvent(val startTime: Date): WhatsNext()
class NextRace(val startTime: Date): WhatsNext()
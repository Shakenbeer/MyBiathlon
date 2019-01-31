@file:Suppress("unused")
@file:SuppressLint("ConstantLocale")

package com.shakenbeer.biathlon

import android.annotation.SuppressLint
import android.content.res.Resources
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.shakenbeer.biathlon.rest.model.SIWIEvent
import org.apache.commons.lang3.time.DateUtils
import java.text.SimpleDateFormat
import java.util.*


val Int.toPx: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()

val Int.toDp: Int
    get() = (this / Resources.getSystem().displayMetrics.density).toInt()

private val startTimeFormatter = SimpleDateFormat("d MMM", Locale.getDefault())
private val endTimeFormatter = SimpleDateFormat("d MMM yyyy", Locale.getDefault())

val SIWIEvent.dates: String
    get() =
        if (this.startDate != null && this.endDate != null) {
            startTimeFormatter.format(this.startDate) + " - " + endTimeFormatter.format(this.endDate)
        } else {
            ""
        }

@BindingAdapter("nat")
fun loadImage(view: ImageView, nat: String) {
    view.setImageResource(
            when (nat) {
                "SWE" -> R.drawable.sweden_flag_round_48dp
                "AUT" -> R.drawable.austria_flag_round_48dp
                "FRA" -> R.drawable.france_flag_round_48dp
                "GER" -> R.drawable.germany_flag_round_48dp
                "ITA" -> R.drawable.italy_flag_round_48dp
                "KOR" -> R.drawable.south_korea_flag_round_48dp
                "FIN" -> R.drawable.finland_flag_round_48dp
                "NOR" -> R.drawable.norway_flag_round_48dp
                "RUS" -> R.drawable.russia_flag_round_48dp
                "SLO" -> R.drawable.slovenia_flag_round_48dp
                "CZE" -> R.drawable.czech_republic_flag_48dp
                "CAN" -> R.drawable.canada_flag_round_48dp
                "USA" -> R.drawable.usa_flag_round_48dp
                else -> R.drawable.biathlon_icon8
            }
    )
}

fun Date.addDays(amount: Int): Date = DateUtils.addDays(this, amount)

fun Date.addHours(amount: Int): Date = DateUtils.addHours(this, amount)

fun Date.trimTime(): Date = DateUtils.truncate(this, Calendar.DATE)
package com.shakenbeer.biathlon.season.presentation

import android.content.Context
import android.content.res.Resources
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.annotation.DrawableRes
import com.shakenbeer.biathlon.R

class MedalView : LinearLayout {

    private var gold: Int = 0
    private var silver: Int = 0
    private var bronze: Int = 0
    private var medalSize: Int = (10 * Resources.getSystem().displayMetrics.density).toInt()

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        val a = context.theme
                .obtainStyledAttributes(attrs, R.styleable.MedalView, 0, 0)
        try {
            gold = a.getInt(R.styleable.MedalView_gold, 0)
            silver = a.getInt(R.styleable.MedalView_silver, 0)
            bronze = a.getInt(R.styleable.MedalView_bronze, 0)
            medalSize = a.getDimensionPixelSize(R.styleable.MedalView_medalSize, 0)
        } finally {
            a.recycle()
        }

        orientation = HORIZONTAL

        for (i in 1..gold) {
            addMedal(context, R.drawable.gold)
        }

        for (i in 1..silver) {
            addMedal(context, R.drawable.silver)
        }

        for (i in 1..bronze) {
            addMedal(context, R.drawable.bronze)
        }
    }

    private fun addMedal(context: Context, @DrawableRes medalIcon: Int) {
        addView(ImageView(context).apply {
            setImageResource(medalIcon)
            layoutParams = LayoutParams(medalSize, medalSize)
            val margin = medalSize / 8
            (layoutParams as LayoutParams).setMargins(margin, margin, margin, margin)
        })
    }
}

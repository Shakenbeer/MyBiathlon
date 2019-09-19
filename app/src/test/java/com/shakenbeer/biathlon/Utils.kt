package com.shakenbeer.biathlon

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.shakenbeer.biathlon.shared.Const
import java.io.File

object Utils {

    val gson: Gson = GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .setDateFormat(Const.SIWI_DATE_FORMAT)
            .create()

    @Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS",
            "NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    @JvmStatic
    fun stringFromFile(path: String): String {
        val uri = this.javaClass.classLoader?.getResource(path)
        val file = File(uri?.path)
        return String(file.readBytes())
    }
}

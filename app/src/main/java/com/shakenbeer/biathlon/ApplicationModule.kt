package com.shakenbeer.biathlon

import android.content.Context
import androidx.room.Room
import com.google.gson.GsonBuilder
import com.shakenbeer.biathlon.rest.BiathlonResults
import com.shakenbeer.biathlon.room.BiathlonDatabase
import com.shakenbeer.biathlon.room.EventDao
import com.shakenbeer.biathlon.room.RaceDao
import com.shakenbeer.biathlon.shared.Const
import com.shakenbeer.biathlon.shared.Const.DB_NAME
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
internal class ApplicationModule(private val application: BiathlonApplication) {

    @Singleton
    @Provides
    fun provideContext(): Context {
        return application
    }

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        val gson = GsonBuilder()
                .setDateFormat(Const.SIWI_DATE_FORMAT)
                .create()

        val client = OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor())
                .build()

        return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(Const.SIWI_URL)
                .client(client)
                .build()
    }

    @Singleton
    @Provides
    fun provideBiathlonResults(retrofit: Retrofit): BiathlonResults {
        return retrofit.create(BiathlonResults::class.java)
    }

    @Singleton
    @Provides
    fun provideBiathlonDatabase(context: Context): BiathlonDatabase {
        return Room.databaseBuilder(context, BiathlonDatabase::class.java, DB_NAME).build()
    }

    @Singleton
    @Provides
    fun provideEventDao(biathlonDatabase: BiathlonDatabase): EventDao {
        return biathlonDatabase.eventDao()
    }

    @Singleton
    @Provides
    fun provideRaceDao(biathlonDatabase: BiathlonDatabase): RaceDao {
        return biathlonDatabase.raceDao()
    }
}

package com.sanush.starwars.di

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.room.Room
import com.sanush.starwars.data.local.PlanetDatabase
import com.sanush.starwars.data.local.PlanetEntity
import com.sanush.starwars.data.remote.PlanetsAPI
import com.sanush.starwars.data.remote.PlanetRemoteMediator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * AppModule object provide http, retrofit and repository instances
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideHttpClient(): OkHttpClient {
        return OkHttpClient().newBuilder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()
    }

    @Provides
    @Singleton
    fun providePlanetDatabase(@ApplicationContext context: Context): PlanetDatabase {
        return Room.databaseBuilder(
            context,
            PlanetDatabase::class.java,
            "planets.db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun providePlanetsApi(): PlanetsAPI {
        return Retrofit.Builder()
            .client(provideHttpClient())
            .baseUrl("https://swapi.dev/api/planets/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PlanetsAPI::class.java)
    }

    @OptIn(ExperimentalPagingApi::class)
    @Provides
    @Singleton
    fun providePlanetPager(
        planetDB: PlanetDatabase,
        planetsAPI: PlanetsAPI
    ): Pager<Int, PlanetEntity> {
        return Pager(
            config = PagingConfig(pageSize = 10, prefetchDistance = 15, initialLoadSize = 10),
            remoteMediator = PlanetRemoteMediator(
                planetDB, planetsAPI
            ),
            pagingSourceFactory = {
                planetDB.dao.pagingSource()
            }
        )
    }
}
package com.sanush.starwars.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface PlanetDao {

    @Upsert
    suspend fun upsertAll(planets: List<PlanetEntity>)

    @Query("SELECT * FROM planets")
    fun pagingSource(): PagingSource<Int, PlanetEntity>

    @Query("DELETE FROM planets")
    suspend fun clearAll()

    @Query("DELETE FROM sqlite_sequence WHERE name = 'planets'")
    suspend fun resetPrimaryKey()
}
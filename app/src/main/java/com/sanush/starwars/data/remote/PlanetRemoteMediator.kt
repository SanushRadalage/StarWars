package com.sanush.starwars.data.remote

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.sanush.starwars.data.local.PlanetDatabase
import com.sanush.starwars.data.local.PlanetEntity
import com.sanush.starwars.data.mappers.toPlanetEntity
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class PlanetRemoteMediator(
    private val planetDB: PlanetDatabase,
    private val planetsAPI: PlanetsAPI
) : RemoteMediator<Int, PlanetEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PlanetEntity>
    ): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(
                    endOfPaginationReached = true
                )

                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    if (lastItem == null) {
                        1
                    } else {
                        (lastItem.id / state.config.pageSize) + 1
                    }
                }
            }

            val planets = planetsAPI.getPlanets(
                page = loadKey
            ).results

            planetDB.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    planetDB.dao.clearAll()
                    planetDB.dao.resetPrimaryKey()
                }
                val planetEntities = planets.map { it.toPlanetEntity() }
                planetDB.dao.upsertAll(planetEntities)
            }

            MediatorResult.Success(
                endOfPaginationReached = planets.isEmpty()
            )
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }
}
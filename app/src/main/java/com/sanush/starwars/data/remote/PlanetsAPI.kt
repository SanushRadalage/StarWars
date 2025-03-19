package com.sanush.starwars.data.remote

import com.sanush.starwars.data.remote.response.PlanetListResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface PlanetsAPI {
    @GET("?")
    suspend fun getPlanets(@Query("page") page: Int): PlanetListResponse
}
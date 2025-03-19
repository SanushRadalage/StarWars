package com.sanush.starwars.data.remote.response

data class PlanetListResponse(
    val count: Int,
    val next: String,
    val previous: String,
    val results: ArrayList<PlanetDto>
)
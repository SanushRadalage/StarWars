package com.sanush.starwars.data.remote.response

import com.google.gson.annotations.SerializedName

data class PlanetDto(
    val climate: String,
    val created: String,
    val diameter: String,
    val edited: String,
    val films: ArrayList<String>,
    val gravity: String,
    val name: String,
    @SerializedName("orbital_period")
    val orbitalPeriod: String,
    val population: String,
    val residents: ArrayList<String>,
    @SerializedName("rotation_period")
    val rotationPeriod: String,
    @SerializedName("surface_water")
    val surfaceWater: String,
    val terrain: String,
    val url: String
)
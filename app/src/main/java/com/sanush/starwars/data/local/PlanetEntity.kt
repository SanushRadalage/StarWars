package com.sanush.starwars.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "planets")
data class PlanetEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val climate: String,
    val gravity: String,
    val orbitalPeriod: String
)
package com.sanush.starwars.data.mappers

import com.sanush.starwars.data.local.PlanetEntity
import com.sanush.starwars.data.remote.response.PlanetDto
import com.sanush.starwars.domain.Planet

fun PlanetDto.toPlanetEntity(): PlanetEntity {
    return PlanetEntity(
        name = name,
        climate = climate,
        gravity = gravity,
        orbitalPeriod = orbitalPeriod
    )
}

fun PlanetEntity.toPlanet(): Planet {
    return Planet(
        id = id,
        name = name,
        climate = climate,
        gravity = gravity,
        orbitalPeriod = orbitalPeriod
    )
}
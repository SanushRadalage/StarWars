package com.sanush.starwars.presentation.planetList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.cachedIn
import androidx.paging.map
import com.sanush.starwars.data.local.PlanetEntity
import com.sanush.starwars.data.mappers.toPlanet
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class PlanetViewModel @Inject constructor(
    pager: Pager<Int, PlanetEntity>
) : ViewModel() {

    val planetPagingFlow = pager
        .flow
        .map { pagingData ->
            pagingData.map { it.toPlanet() }
        }
        .cachedIn(viewModelScope)
}
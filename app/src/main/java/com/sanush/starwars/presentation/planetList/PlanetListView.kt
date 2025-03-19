package com.sanush.starwars.presentation.planetList

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemKey
import com.sanush.starwars.R
import com.sanush.starwars.domain.Planet
import com.sanush.starwars.presentation.planetList.composables.PlanetItemView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlanetListView(
    planets: LazyPagingItems<Planet>,
    onClickItem: (Planet) -> Unit
) {
    val context = LocalContext.current
    LaunchedEffect(key1 = planets.loadState) {
        if (planets.loadState.refresh is LoadState.Error) {
            Toast.makeText(
                context,
                "Error: " + (planets.loadState.refresh as LoadState.Error).error.message,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(title = {
                Text(stringResource(R.string.planets))
            }, modifier = Modifier.shadow(elevation = 2.dp).background(Color.White))
        }
    ) { innerPadding ->
        Column(Modifier.padding(innerPadding)) {
            Box(modifier = Modifier.fillMaxSize()) {
                if (planets.loadState.refresh is LoadState.Loading) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        items(count = planets.itemCount,
                            key = planets.itemKey { it.id }) { index ->
                            val planet = planets[index]
                            if (planet != null) {
                                PlanetItemView(planet) {
                                    onClickItem(planet)
                                }
                            }
                        }
                        item {
                            if (planets.loadState.append is LoadState.Loading) {
                                CircularProgressIndicator()
                            }
                        }
                    }
                }
            }
        }
    }
}
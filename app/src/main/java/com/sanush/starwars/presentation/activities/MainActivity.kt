package com.sanush.starwars.presentation.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import androidx.paging.compose.collectAsLazyPagingItems
import com.sanush.starwars.presentation.planetDetail.PlanetDetailView
import com.sanush.starwars.presentation.planetList.PlanetListView
import com.sanush.starwars.presentation.planetList.PlanetViewModel
import com.sanush.starwars.ui.theme.StarWarsTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.Serializable

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            StarWarsTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = PlanetListScreen
                ) {
                    composable<PlanetListScreen> {

                        val viewModel = hiltViewModel<PlanetViewModel>()
                        val planets = viewModel.planetPagingFlow.collectAsLazyPagingItems()

                        PlanetListView(planets) { planet ->
                            navController.navigate(
                                PlanetDetailScreen(
                                    id = planet.id,
                                    name = planet.name,
                                    orbitalPeriod = planet.orbitalPeriod,
                                    gravity = planet.gravity
                                )
                            )
                        }
                    }
                    composable<PlanetDetailScreen> {
                        val args = it.toRoute<PlanetDetailScreen>()
                        PlanetDetailView(
                            id = args.id,
                            name = args.name,
                            orbitalPeriod = args.orbitalPeriod,
                            gravity = args.gravity,
                        ) {
                            navController.popBackStack()
                        }
                    }
                }

            }
        }
    }
}

@Serializable
object PlanetListScreen

@Serializable
data class PlanetDetailScreen(
    val id: Int,
    val name: String,
    val orbitalPeriod: String,
    val gravity: String
)

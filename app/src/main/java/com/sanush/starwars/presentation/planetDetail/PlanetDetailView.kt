package com.sanush.starwars.presentation.planetDetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.sanush.starwars.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlanetDetailView(
    id: Int,
    name: String,
    orbitalPeriod: String,
    gravity: String,
    onBackPress: () -> Unit
) {
    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(title = {
                Text(stringResource(R.string.planet_details))
            }, navigationIcon = {
                IconButton(onClick = {
                    onBackPress()
                }) {
                    Icon(
                        Icons.AutoMirrored.Default.ArrowBack,
                        contentDescription = stringResource(R.string.icon_back_description)
                    )
                }
            },
                modifier = Modifier
                    .shadow(elevation = 2.dp)
                    .background(Color.White)
            )
        }
    ) { innerPadding ->
        Column(Modifier
            .padding(innerPadding)
            .padding(16.dp)) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data("https://picsum.photos/600/600?random=${id}")
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(R.drawable.ic_detail_banner_placeholder),
                contentDescription = stringResource(R.string.image_description),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = name,
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "${stringResource(R.string.orbital_period)} $orbitalPeriod",
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "${stringResource(R.string.gravity)} $gravity",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Preview
@Composable
fun PreviewPlanetDetailView() {
    PlanetDetailView(id = 1, name = "Mars", orbitalPeriod = "23", gravity = "12") { }
}
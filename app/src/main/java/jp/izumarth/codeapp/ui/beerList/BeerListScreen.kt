package jp.izumarth.codeapp.ui.beerList

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import jp.izumarth.codeapp.activity.BeerActivity
import jp.izumarth.codeapp.model.Beer
import jp.izumarth.codeapp.utils.getDrawableId

@Composable
fun BeerListScreen(
    viewModel: BeerListViewModel = hiltViewModel()
) {
    val uiState = viewModel.state

    val beerLauncher = rememberLauncherForActivityResult(
        BeerActivity.Launcher()
    ) {}

    LaunchedEffect(Unit) {
        viewModel.onLaunch()
    }

    BeerListContent(
        uiState = uiState,
        onDetail = { beerName ->
            beerLauncher.launch(
                BeerActivity.Extras(
                    beerName = beerName,
                )
            )
        }
    )
}

@Composable
fun BeerListContent(
    uiState: BeerListUiState,
    onDetail: (String) -> Unit,
) {
    when (uiState) {
        is BeerListUiState.Loading ->
            CircularProgressIndicator()

        is BeerListUiState.NotFound ->
            // TODO: Error Screen
            CircularProgressIndicator()

        is BeerListUiState.Loaded ->
            Column {
                BeerList(
                    beers = uiState.beers,
                    onDetail = onDetail,
                )
            }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun BeerList(
    beers: List<Beer>,
    onDetail: (String) -> Unit,
) {
    val groupedBeers = beers
        .groupBy { it.name.first().toString() }
        .toSortedMap()

    LazyColumn {
        groupedBeers.forEach { (initialText, groupedBeers) ->
            stickyHeader {
                BeerListHeader(
                    initialText = initialText,
                )
            }

            items(groupedBeers) {
                BeerListItem(
                    beer = it,
                    onDetail = onDetail,
                )
            }
        }
    }
}

@Composable
private fun BeerListHeader(
    initialText: String,
) {
    Column(
        modifier = Modifier
            .padding(
                horizontal = 16.dp,
            )
    ) {
        Text(
            text = initialText,
        )
    }
}

@Composable
private fun BeerListItem(
    beer: Beer,
    onDetail: (String) -> Unit,
) {
    val drawableId = getDrawableId(imageName = beer.imageName)

    Card(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .fillMaxWidth()
            .clickable { onDetail(beer.name) },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.tertiaryContainer,
        ),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Image(
                modifier = Modifier
                    .height(64.dp)
                    .width(64.dp)
                    .padding(4.dp)
                    .clip(MaterialTheme.shapes.small),
                contentScale = ContentScale.Crop,
                painter = painterResource(drawableId),
                contentDescription = null
            )

            Column(
                modifier = Modifier
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceEvenly,
            ) {
                Text(
                    text = beer.name,
                    style = MaterialTheme.typography.bodyLarge,
                )

                Text(
                    text = beer.beerStyle,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.outline,
                )
            }
        }
    }
}

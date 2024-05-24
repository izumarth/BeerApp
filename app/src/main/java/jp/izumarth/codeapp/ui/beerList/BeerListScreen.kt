package jp.izumarth.codeapp.ui.beerList

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import jp.izumarth.codeapp.activity.BeerActivity
import jp.izumarth.codeapp.model.Beer

@Composable
fun BeerListScreen(
    viewModel: BeerListViewModel = hiltViewModel()
) {
    val uiState = viewModel.state

    val BeerLaucher = rememberLauncherForActivityResult(
        BeerActivity.Launcher()
    ) {}

    LaunchedEffect(Unit) {
        viewModel.onLaunch()
    }

    BeerListContent(
        uiState = uiState,
        onDetail = { beerName ->
            BeerLaucher.launch(
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
    Column(
        modifier = Modifier
            .height(64.dp)
            .padding(
                horizontal = 16.dp,
            )
            .clickable { onDetail(beer.name) },
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = beer.name,
        )
    }
}

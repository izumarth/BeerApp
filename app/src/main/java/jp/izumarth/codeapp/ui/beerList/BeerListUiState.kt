package jp.izumarth.codeapp.ui.beerList

import androidx.compose.runtime.Immutable
import jp.izumarth.codeapp.model.Beer

@Immutable
sealed class BeerListUiState {
    @Immutable
    data object Loading: BeerListUiState()

    @Immutable
    data object NotFound: BeerListUiState()

    @Immutable
    data class Loaded(
        val beers: List<Beer>,
    ) : BeerListUiState()
}
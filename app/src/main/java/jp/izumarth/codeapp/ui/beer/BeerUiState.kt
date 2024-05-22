package jp.izumarth.codeapp.ui.beer

import androidx.compose.runtime.Immutable
import jp.izumarth.codeapp.model.Beer

@Immutable
sealed class BeerUiState {
    @Immutable
    data object Loading: BeerUiState()

    @Immutable
    data object NotFound: BeerUiState()

    @Immutable
    data class Loaded(
        val beerItem: Beer
    ) : BeerUiState()
}
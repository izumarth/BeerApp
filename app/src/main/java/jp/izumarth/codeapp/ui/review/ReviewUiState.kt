package jp.izumarth.codeapp.ui.review

import androidx.compose.runtime.Immutable
import jp.izumarth.codeapp.model.Review

@Immutable
sealed class ReviewUiState {
    @Immutable
    data object Loading: ReviewUiState()

    @Immutable
    data class Loaded(
        val review: Review,
    ) : ReviewUiState()
}
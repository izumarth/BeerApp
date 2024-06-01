package jp.izumarth.codeapp.ui.beer

import androidx.compose.runtime.Immutable

@Immutable
enum class BeerRoute(
    val destination: String,
) {
    Detail(
        destination = "detail",
    ),

    Review(
        destination = "review",
    ),
}
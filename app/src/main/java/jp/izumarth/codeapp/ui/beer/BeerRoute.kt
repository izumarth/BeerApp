package jp.izumarth.codeapp.ui.beer

import androidx.compose.runtime.Immutable

@Immutable
enum class BeerRoute(
    val destinaton: String,
) {
    Detail(
        destinaton = "detail",
    ),

    Review(
        destinaton = "review",
    ),
}
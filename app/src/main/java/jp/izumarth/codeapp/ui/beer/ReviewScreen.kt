package jp.izumarth.codeapp.ui.beer

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import jp.izumarth.codeapp.model.Beer

@Composable
fun ReviewScreen(
    beerItem: Beer,
) {
    Text(beerItem.name)
}
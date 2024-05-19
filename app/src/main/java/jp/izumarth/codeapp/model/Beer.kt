package jp.izumarth.codeapp.model

import androidx.compose.runtime.Immutable

@Immutable
data class Beer(
    val name: String,
    val beerStyle: String,
    val alcohol: Double,
    val volume: Int,
    val country: String,
)
package jp.izumarth.codeapp.model

import androidx.compose.runtime.Immutable

@Immutable
data class Beer(
    val name: String,
    val imageName: String,
    val beerStyle: String,
    val alcohol: Double,
    val volume: Int,
    val ibu: Int?,
    val ebc: Int?,
    val temperature: String?,
    val country: String,
    val hop: List<String>,
    val materials: List<String>,
    val description: String,
)
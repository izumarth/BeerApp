package jp.izumarth.codeapp.model

import androidx.room.Entity

@Entity(
    tableName = "review",
    primaryKeys = ["name"],
)
data class Review(
    val name: String,
    val color: Int?,
    val transparency: Int?,
    val carbonation: Int?,
    val head: Int?,
    val hopsFlavor: Int?,
    val maltFlavor: Int?,
    val esterFlavor: Int?,
    val bitterness: Int?,
    val sweetness: Int?,
    val body: Int?,
    val afterTaste: Int?,
    val totalRate: Int?,
)
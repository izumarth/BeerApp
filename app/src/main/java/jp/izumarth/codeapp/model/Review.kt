package jp.izumarth.codeapp.model

import androidx.room.Entity

@Entity(
    tableName = "review",
    primaryKeys = ["name"],
)
data class Review(
    val name: String,
    val color: Int = 0,
    val transparency: Int = 0,
    val carbonation: Int = 0,
    val head: Int = 0,
    val hopsFlavor: Int = 0,
    val maltFlavor: Int = 0,
    val esterFlavor: Int = 0,
    val bitterness: Int = 0,
    val sweetness: Int = 0,
    val body: Int = 0,
    val afterTaste: Int = 0,
    val totalRate: Int = 0,
)
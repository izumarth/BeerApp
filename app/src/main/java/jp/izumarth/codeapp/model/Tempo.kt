package jp.izumarth.codeapp.model

import androidx.compose.runtime.Immutable

@Immutable
data class Tempo(
    val time: Double,
) {
    val millisecondInterval: Double
        get() = 60_000.0 / time

    companion object {
        val Larghissimo = Tempo(24.0)
        val Adagissimo = Tempo(28.0)
        val Sostenuto = Tempo(31.0)
        val Grave = Tempo(35.0)
        val Largo = Tempo(50.0)
        val Lento = Tempo(52.0)
        val Presto = Tempo(181.0)
        val Prestissimo = Tempo(210.0)
    }
}
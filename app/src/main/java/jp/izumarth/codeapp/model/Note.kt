package jp.izumarth.codeapp.model

import androidx.compose.runtime.Immutable
import kotlin.math.floor

@Immutable
sealed class MusicElement {

    data class Note(
        val note: Float = 1.0f, // C2からの音数
    ) : MusicElement() {

        fun sharp(): Note {
            return Note(note + 0.5f)
        }

        fun flat(): Note {
            return Note(note - 0.5f)
        }

        fun getName(): String {
            val noteValue = note.mod(7.0f)

            return when (val degreeFromC = floor(noteValue).toInt()) {
                0 -> "B"
                1 -> "C"
                2 -> "D"
                3 -> "E"
                4 -> "F"
                5 -> "G"
                6 -> "A"
                else -> throw IllegalArgumentException("Invalid degreeFromC value: $degreeFromC")
            }
        }

        companion object {
            val C2 = Note(1.0f)
            val D2 = Note(2.0f)
            val E2 = Note(3.0f)
            val F2 = Note(4.0f)
            val G2 = Note(5.0f)
            val A2 = Note(6.0f)
            val B2 = Note(7.0f)
        }
    }

    data class Chord(
        val notes: List<Note> = listOf(),
    ) : MusicElement() {
        companion object {
            val CMaj = Chord(
                listOf(
                    Note.C2,
                    Note.C2 + 3.0f,
                    Note.C2 + 5.0f,
                )
            )
        }
    }
}

infix operator fun MusicElement.Note.plus(i: Float): MusicElement.Note {
    // 直感的にC -> Eは3音上としたく-1.0fしている
    return MusicElement.Note(this.note + (i - 1.0f))
}

infix operator fun MusicElement.Chord.plus(i: Float): MusicElement.Chord {
    return MusicElement.Chord(this.notes.map { it + i })
}
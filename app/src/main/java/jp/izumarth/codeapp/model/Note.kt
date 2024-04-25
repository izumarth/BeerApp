package jp.izumarth.codeapp.model

import androidx.compose.runtime.Immutable

@Immutable
sealed class MusicElement {

    data class Note(
        val pitch: int,
    ) : MusicElement() {

        fun sharp(): Note {
            return Note(pitch + 1)
        }

        fun flat(): Note {
            return Note(pitch - 1)
        }

        companion object {
            val C4 = Note(60)
            val D4 = Note(62)
            val E4 = Note(64)
            val F4 = Note(65)
            val G4 = Note(67)
            val A4 = Note(69)
            val B4 = Note(71)
        }
    }

    data class Chord(
        val notes: List<Note> = listOf(),
    ) : MusicElement() {
        companion object {
            val CMaj = Chord(
                listOf(
                    Note.C4,
                    Note.C4 + 4,
                    Note.C4 + 7,
                )
            )
        }
    }
}

infix operator fun MusicElement.Note.plus(i: Int): MusicElement.Note {
    return MusicElement.Note(this.pitch + i)
}

infix operator fun MusicElement.Chord.plus(i: Int): MusicElement.Chord {
    return MusicElement.Chord(this.notes.map { it + i })
}
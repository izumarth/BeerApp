package jp.izumarth.codeapp.ui.notes

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.izumarth.codeapp.model.MusicElement
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(

) : ViewModel() {

    val noteListFlow = MutableStateFlow<List<MusicElement.Note>>(emptyList())

    fun onLaunch() {
        noteListFlow.value = listOf(
            MusicElement.Note.C2,
            MusicElement.Note.D2,
            MusicElement.Note.E2,
            MusicElement.Note.F2,
            MusicElement.Note.G2,
            MusicElement.Note.A2,
            MusicElement.Note.B2,
        )
    }
}
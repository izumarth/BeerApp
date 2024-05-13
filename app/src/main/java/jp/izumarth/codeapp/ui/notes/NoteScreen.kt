package jp.izumarth.codeapp.ui.notes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import jp.izumarth.codeapp.model.MusicElement

@Composable
fun NoteScreen(
    viewModel: NoteViewModel = hiltViewModel()
) {
    val noteList by viewModel.noteListFlow.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.onLaunch()
    }

    NoteContent(
        noteList = noteList,
    )
}

@Composable
fun NoteContent(
    noteList: List<MusicElement.Note>,
) {
    // Add a single item
    LazyColumn {
        // Add a single item
        item {
            Text(text = "First item")
        }

        items(
            items = noteList,
            key = { it.getName() }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(text = "Item: ${it.getName()}")
            }
        }

        // Add another single item
        item {
            Text(text = "Last item")
        }
    }
}

package com.example.mynote

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.benasher44.uuid.uuid4
import com.example.mynote.models.Note

@Composable
fun NoteScreen(modifier: Modifier) {
    val viewModel = hiltViewModel<NoteViewModel>()
    val notes: List<Note> by viewModel.list.observeAsState(initial = listOf())

    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    var isEditing by remember { mutableStateOf(false) }
    var editingNoteId by remember { mutableStateOf("") }


    Column(modifier = modifier.padding(16.dp)) {
        TextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Title") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(8.dp))

        TextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Description") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(8.dp))

        Button(
            onClick = {
                if (title.isNotEmpty() && description.isNotEmpty()) {
                    val note = Note(
                        id = if (isEditing) editingNoteId else uuid4().toString(),
                        title = title,
                        description = description
                    )

                    if (isEditing) {
                        viewModel.updateNote(note)
                        isEditing = false
                        editingNoteId = ""
                    } else {
                        viewModel.insertNote(note)
                    }
                    title = ""
                    description = ""
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (isEditing) "Update Note" else "Save Note")
        }

        Spacer(Modifier.height(16.dp))

        LazyColumn {
            items(notes) { note ->
                Card(
                    modifier =  Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    onClick = {
                        title = note.title
                        description = note.description
                        editingNoteId = note.id
                        isEditing = true
                    }
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(note.title, style = MaterialTheme.typography.titleMedium)
                        Text(note.description, style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }
        }
    }
}
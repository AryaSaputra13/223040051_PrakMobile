package com.example.mynote

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import com.benasher44.uuid.uuid4
import com.example.mynote.models.Note
import kotlinx.coroutines.launch

@Composable
fun NoteScreen(modifier: Modifier) {
    val context = LocalContext.current
    val dao = NoteDatabase.getDatabase(context).noteDao()
    val list : LiveData<List<Note>> = dao.getAllNotes()
    val notes: List<Note> by list.observeAsState(initial = listOf())

    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var editingNote by remember { mutableStateOf<Note?>(null) }
    val scope = rememberCoroutineScope()

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
                    scope.launch {
                        if (editingNote != null) {
                            dao.updateNote(
                                editingNote!!.copy(
                                    title = title,
                                    description = description
                                )
                            )
                            editingNote = null
                        } else {
                            dao.insertNote(Note(uuid4().toString(), title, description))
                        }
                        title = ""
                        description = ""
                    }

                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (editingNote != null) "Update Note" else "Save Note")
        }

        Spacer(Modifier.height(16.dp))

        LazyColumn {
            items(notes) { note ->
                Card(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(note.title, style = MaterialTheme.typography.titleMedium)
                        Text(note.description, style = MaterialTheme.typography.bodyMedium)
                        Spacer(Modifier.height(8.dp))

                        Row {
                            Button(
                                onClick = {
                                    title = note.title
                                    description = note.description
                                    editingNote = note
                                },
                                modifier = Modifier.weight(1f).padding(end = 4.dp)
                            ) {
                                Text("Edit")
                            }

                            Button(
                                onClick = {
                                    scope.launch {
                                        dao.deleteNote(note)
                                    }
                                },
                                modifier = Modifier.weight(1f).padding(start = 4.dp)
                            ) {
                                Text("Delete")
                            }
                        }
                    }
                }
            }
        }
    }
}
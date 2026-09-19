package com.example.myproyectfinal.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myproyectfinal.domain.model.TaskDraft
import com.example.myproyectfinal.ui.viewmodel.DraftViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DraftsScreen(
    onBack: () -> Unit,
    viewModel: DraftViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Drafts") },
                navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back") } }
            )
        }
    ) { padding ->
        LazyColumn(Modifier.padding(padding)) {
            items(uiState.drafts) { draft ->
                DraftItem(draft, onPublish = { viewModel.publishDraft(draft.id) }, onDelete = { viewModel.deleteDraft(draft.id) })
            }
        }
    }
}

@Composable
fun DraftItem(draft: TaskDraft, onPublish: () -> Unit, onDelete: () -> Unit) {
    ListItem(
        headlineContent = { Text(draft.title) },
        supportingContent = { Text(draft.description) },
        trailingContent = {
            Row {
                IconButton(onClick = onPublish) { Icon(Icons.AutoMirrored.Filled.Send, "Publish") }
                IconButton(onClick = onDelete) { Icon(Icons.Default.Delete, "Delete") }
            }
        }
    )
}

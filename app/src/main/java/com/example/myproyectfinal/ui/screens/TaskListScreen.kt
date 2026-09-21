package com.example.myproyectfinal.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myproyectfinal.domain.model.Task
import com.example.myproyectfinal.ui.state.OperationState
import com.example.myproyectfinal.ui.viewmodel.TaskViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskListScreen(
    onAddTask: () -> Unit,
    onViewDrafts: () -> Unit,
    onLogout: () -> Unit,
    viewModel: TaskViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    val opState by viewModel.operationState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    var showLogoutDialog by remember { mutableStateOf(false) }

    LaunchedEffect(opState) {
        when (opState) {
            is OperationState.Success -> {
                (opState as OperationState.Success).message?.let {
                    snackbarHostState.showSnackbar(it)
                    viewModel.resetOperationState()
                }
            }
            is OperationState.Error -> {
                snackbarHostState.showSnackbar((opState as OperationState.Error).message)
                viewModel.resetOperationState()
            }
            else -> {}
        }
    }

    if (showLogoutDialog) {
        AlertDialog(
            onDismissRequest = { showLogoutDialog = false },
            title = { Text("Logout") },
            text = { Text("Are you sure you want to log out?") },
            confirmButton = {
                TextButton(onClick = {
                    showLogoutDialog = false
                    onLogout()
                }) { Text("Logout") }
            },
            dismissButton = {
                TextButton(onClick = { showLogoutDialog = false }) { Text("Cancel") }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Tasks") },
                actions = {
                    IconButton(onClick = onViewDrafts) { Icon(Icons.AutoMirrored.Filled.List, contentDescription = "Drafts") }
                    IconButton(onClick = { showLogoutDialog = true }) { Icon(Icons.AutoMirrored.Filled.ExitToApp, contentDescription = "Logout") }
                }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddTask) {
                Icon(Icons.Default.Add, contentDescription = "Add Task")
            }
        }
    ) { padding ->
        if (uiState.isLoading) {
            Box(Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
                CircularProgressIndicator()
            }
        } else if (uiState.tasks.isEmpty()) {
            Box(Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
                Text("No tasks found. Tap + to add one!")
            }
        } else {
            LazyColumn(modifier = Modifier.padding(padding)) {
                items(uiState.tasks) { task ->
                    TaskItem(
                        task = task,
                        onToggle = { viewModel.toggleTask(task) },
                        onDelete = { viewModel.deleteTask(task.id) }
                    )
                }
            }
        }
    }
}

@Composable
fun TaskItem(task: Task, onToggle: () -> Unit, onDelete: () -> Unit) {
    ListItem(
        headlineContent = { Text(task.title) },
        supportingContent = { Text(task.description) },
        leadingContent = { Checkbox(checked = task.isCompleted, onCheckedChange = { onToggle() }) },
        trailingContent = { IconButton(onClick = onDelete) { Icon(Icons.Default.Delete, "Delete") } }
    )
}

package com.example.myproyectfinal.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myproyectfinal.ui.state.OperationState
import com.example.myproyectfinal.ui.viewmodel.DraftViewModel
import com.example.myproyectfinal.ui.viewmodel.TaskViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskFormScreen(
    onBack: () -> Unit,
    taskViewModel: TaskViewModel = hiltViewModel(),
    draftViewModel: DraftViewModel = hiltViewModel()
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    val opState by taskViewModel.operationState.collectAsState()

    LaunchedEffect(opState) {
        if (opState is OperationState.Success) onBack()
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("New Task") }) }
    ) { padding ->
        Column(Modifier.padding(padding).padding(16.dp)) {
            TextField(value = title, onValueChange = { title = it }, label = { Text("Title") }, modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.height(8.dp))
            TextField(value = description, onValueChange = { description = it }, label = { Text("Description") }, modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.height(16.dp))
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Button(onClick = { draftViewModel.saveDraft(title, description); onBack() }) { Text("Save as Draft") }
                Button(
                    onClick = { taskViewModel.createTask(title, description) },
                    enabled = title.isNotBlank()
                ) { Text("Publish") }
            }
        }
    }
}

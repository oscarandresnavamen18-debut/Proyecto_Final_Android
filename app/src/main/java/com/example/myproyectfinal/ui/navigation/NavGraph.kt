package com.example.myproyectfinal.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.myproyectfinal.ui.screens.*
import com.example.myproyectfinal.ui.viewmodel.AuthViewModel

@Composable
fun NavGraph(navController: NavHostController) {
    val authViewModel: AuthViewModel = hiltViewModel()
    val currentUser by authViewModel.currentUser.collectAsState()
    
    val startDestination = if (currentUser != null) Screen.TaskList.route else Screen.Login.route

    NavHost(navController = navController, startDestination = startDestination) {
        composable(Screen.Login.route) {
            LoginScreen(
                onNavigateToRegister = { navController.navigate(Screen.Register.route) },
                onLoginSuccess = { navController.navigate(Screen.TaskList.route) { popUpTo(Screen.Login.route) { inclusive = true } } }
            )
        }
        composable(Screen.Register.route) {
            RegisterScreen(
                onNavigateToLogin = { navController.navigate(Screen.Login.route) },
                onRegisterSuccess = { navController.navigate(Screen.TaskList.route) { popUpTo(Screen.Register.route) { inclusive = true } } }
            )
        }
        composable(Screen.TaskList.route) {
            TaskListScreen(
                onAddTask = { navController.navigate(Screen.TaskForm.route) },
                onViewDrafts = { navController.navigate(Screen.Drafts.route) },
                onLogout = { 
                    authViewModel.logout()
                    navController.navigate(Screen.Login.route) { popUpTo(Screen.TaskList.route) { inclusive = true } }
                }
            )
        }
        composable(Screen.TaskForm.route) {
            TaskFormScreen(
                onBack = { navController.popBackStack() }
            )
        }
        composable(Screen.Drafts.route) {
            DraftsScreen(
                onBack = { navController.popBackStack() }
            )
        }
    }
}

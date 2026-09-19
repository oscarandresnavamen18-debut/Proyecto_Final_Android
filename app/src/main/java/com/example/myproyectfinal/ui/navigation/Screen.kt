package com.example.myproyectfinal.ui.navigation

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Register : Screen("register")
    object TaskList : Screen("task_list")
    object TaskForm : Screen("task_form")
    object Drafts : Screen("drafts")
}

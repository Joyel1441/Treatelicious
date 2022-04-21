package com.joyel.treatelicious.screens.MyRecipesScreen

import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.joyel.treatelicious.navigation.BottomNavigationScreen
import com.joyel.treatelicious.navigation.Screens

@Composable
fun MyRecipesScreen(navController: NavController) {
    val items = listOf(
        Screens.Categories,
        Screens.Explore,
        Screens.MyRecipes
    )
    Scaffold(bottomBar = { BottomNavigationScreen(navController = navController, items = items) }) {
        Text(text = "My Recipes")
    }
}
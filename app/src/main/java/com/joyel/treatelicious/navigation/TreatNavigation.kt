package com.joyel.treatelicious.navigation

import androidx.annotation.DrawableRes
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.joyel.treatelicious.R
import com.joyel.treatelicious.screens.MainViewModel
import com.joyel.treatelicious.screens.MyRecipesScreen.MyRecipesScreen
import com.joyel.treatelicious.screens.categories.CategoriesScreen
import com.joyel.treatelicious.screens.explore.ExploreScreen
import com.joyel.treatelicious.screens.recipepage.RecipeScreen
import com.joyel.treatelicious.screens.splash.TreatSplashScreen

sealed class Screens(val title: String, val route: String, @DrawableRes val icons: Int) {
    object Explore : Screens(title = "Explore", route = TreatScreens.ExploreScreen.name, icons = R.drawable.baseline_explore_24)
    object Categories : Screens(title = "Categories", route = TreatScreens.CategoriesScreen.name, icons = R.drawable.baseline_category_24)
    object MyRecipes : Screens(title = "My Recipes", route = TreatScreens.MyRecipesScreen.name, icons = R.drawable.baseline_bookmark_24)
}

@Composable
fun BottomNavigationScreen(navController: NavController, items: List<Screens>) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    
    BottomNavigation() {
        items.forEach { screens ->
            BottomNavigationItem(selected = currentDestination?.route == screens.route || currentDestination?.route.toString().split("/")[0] == screens.route, onClick = {
                if (screens.route == TreatScreens.ExploreScreen.name) {
                    navController.navigate(screens.route+"/random"){
                        launchSingleTop = true
                        popUpTo(navController.graph.findStartDestination().id){
                            saveState = true
                        }
                        restoreState = true
                    }
                }
                else {
                    navController.navigate(screens.route) {
                        launchSingleTop = true
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        restoreState = true
                    }
                }}, icon = {
                                   Icon(painter = painterResource(id = screens.icons), contentDescription = null)
            }, label = { Text(text = screens.title) },
            alwaysShowLabel = false
            )
        }
    }
}


@Composable
fun TreatNavigation(navController: NavController) {
    val mainViewModel = hiltViewModel<MainViewModel>()
    NavHost(navController = navController as NavHostController, startDestination = TreatScreens.CategoriesScreen.name) {
        composable(route = TreatScreens.SplashScreen.name) {
            TreatSplashScreen(navController = navController)
        }
        val exploreScreen = TreatScreens.ExploreScreen.name

        composable(route = "$exploreScreen/{category}", arguments = listOf(
            navArgument(name = "category") {
                type = NavType.StringType
            }
        )) { navBack ->
            val category = navBack.arguments?.getString("category")
            ExploreScreen(navController = navController, category = category.toString(), mainViewModel = mainViewModel)
        }
        composable(route = TreatScreens.CategoriesScreen.name) {
            CategoriesScreen(navController = navController, mainViewModel = mainViewModel)
        }
        composable(route = TreatScreens.MyRecipesScreen.name) {
            MyRecipesScreen(navController = navController)
        }
        composable(route = TreatScreens.RecipeScreen.name+"/{mealId}", arguments = listOf(
            navArgument(name = "mealId") {
                type = NavType.StringType
            }
        )) { navBack ->
            val mealId = navBack.arguments?.getString("mealId")
            RecipeScreen(navController = navController, mealId = mealId.toString(), mainViewModel = mainViewModel)
        }
    }
}
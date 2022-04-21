package com.joyel.treatelicious.screens.explore


import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import coil.compose.AsyncImage
import com.joyel.treatelicious.data.DataOrException
import com.joyel.treatelicious.model.meal.Meal
import com.joyel.treatelicious.model.meal.SelectedCategory
import com.joyel.treatelicious.navigation.BottomNavigationScreen
import com.joyel.treatelicious.navigation.Screens
import com.joyel.treatelicious.navigation.TreatScreens
import com.joyel.treatelicious.screens.MainViewModel

@Composable
fun ExploreScreen(navController: NavController, category: String, mainViewModel: MainViewModel) {
    val items = listOf(
        Screens.Categories,
        Screens.Explore,
        Screens.MyRecipes
    )
   Scaffold(bottomBar = { BottomNavigationScreen(navController = navController, items = items) }) {
       if (category == "random") {
           val random_recipies_count = 10
           val recipeList: MutableList<DataOrException<Meal, Boolean, Exception>> = mutableListOf()
           for (i in 0..random_recipies_count - 1) {
               val mealData = produceState<DataOrException<Meal, Boolean, Exception>>(
                   initialValue =
                   DataOrException(loading = true)
               ) {
                   value = mainViewModel.getRandomRecipe()
               }.value
               recipeList.add(mealData)
           }
           Column(modifier = Modifier.padding(bottom = 50.dp)) {
               LazyColumn(
                   modifier = Modifier
                       .padding(10.dp)
                       .fillMaxWidth()
                       .fillMaxHeight(),
                   verticalArrangement = Arrangement.Center,
                   horizontalAlignment = Alignment.CenterHorizontally
               ) {
                   items(items = recipeList) { mealData ->
                       if (mealData.loading == true) {
                           CircularProgressIndicator()
                       } else if (mealData.data != null)
                           RecipeCardMeal(mealData = mealData.data) { mealId ->
                               navController.navigate(TreatScreens.RecipeScreen.name + "/$mealId")
                           }
                   }
               }
           }
       } else {
           val mealData = produceState<DataOrException<SelectedCategory, Boolean, Exception>>(
               initialValue =
               DataOrException(loading = true)
           ) {
               value = mainViewModel.getRecipeByCategory(category)
           }.value
           if (mealData.data != null) {
               Column(modifier = Modifier.padding(bottom = 50.dp)) {
                   LazyColumn(
                       modifier = Modifier
                           .padding(10.dp)
                           .fillMaxWidth()
                           .fillMaxHeight(),
                       verticalArrangement = Arrangement.Center,
                       horizontalAlignment = Alignment.CenterHorizontally
                   ) {
                       items(mealData.data!!.meals) { mealData ->
                           RecipeCardMealCategory(
                               strMeal = mealData.strMeal,
                               strMealThumb = mealData.strMealThumb,
                               idMeal = mealData.idMeal
                           ) { mealId ->
                               navController.navigate(TreatScreens.RecipeScreen.name + "/$mealId") {
                                   popUpTo(navController.graph.findStartDestination().id)
                               }
                           }
                       }
                   }
               }
           } else {
               Column(
                   modifier = Modifier
                       .fillMaxWidth()
                       .fillMaxHeight(),
                   verticalArrangement = Arrangement.Center,
                   horizontalAlignment = Alignment.CenterHorizontally
               ) {
                   CircularProgressIndicator()
               }
           }
       }
   }

}

@Composable
fun RecipeCardMeal(mealData: Meal? = null, onRecipeClick: (String) -> Unit) {
    Card(
        modifier = Modifier
            .padding(10.dp)
            .clickable {
                onRecipeClick(mealData!!.meals[0].idMeal)
            },
        elevation = 10.dp,
        shape = RoundedCornerShape(corner = CornerSize(40.dp)),
        border = BorderStroke(width = 1.dp, color = Color.Blue)
    ) {
        Column(
            modifier = Modifier.padding(10.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = mealData!!.meals[0].strMealThumb,
                contentDescription = null,
                modifier = Modifier
                    .size(300.dp)
                    .clip(shape = RoundedCornerShape(corner = CornerSize(40.dp)))
            )
            Text(text = mealData.meals[0].strMeal)
        }
    }
}

@Composable
fun RecipeCardMealCategory(
    strMeal: String,
    strMealThumb: String,
    idMeal: String,
    onRecipeClick: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .padding(10.dp)
            .clickable {
                onRecipeClick(idMeal)
            },
        elevation = 10.dp,
        shape = RoundedCornerShape(corner = CornerSize(40.dp)),
        border = BorderStroke(width = 1.dp, color = Color.Blue)
    ) {
        Column(
            modifier = Modifier.padding(10.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = strMealThumb,
                contentDescription = null,
                modifier = Modifier
                    .size(300.dp)
                    .clip(shape = RoundedCornerShape(corner = CornerSize(40.dp)))
            )
            Text(text = strMeal)
        }
    }
}
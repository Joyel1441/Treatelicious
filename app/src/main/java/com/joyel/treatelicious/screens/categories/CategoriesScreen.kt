package com.joyel.treatelicious.screens.categories

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.joyel.treatelicious.data.DataOrException
import com.joyel.treatelicious.model.categories.Categories
import com.joyel.treatelicious.navigation.BottomNavigationScreen
import com.joyel.treatelicious.navigation.Screens
import com.joyel.treatelicious.navigation.TreatNavigation
import com.joyel.treatelicious.navigation.TreatScreens
import com.joyel.treatelicious.screens.MainViewModel

@Composable
fun CategoriesScreen(navController: NavController, mainViewModel: MainViewModel) {
    val categories = produceState<DataOrException<Categories, Boolean, Exception>>(
        initialValue = DataOrException(loading = true)
    ) {
        value = mainViewModel.getAllCategories()
    }.value

    val items = listOf(
        Screens.Categories,
        Screens.Explore,
        Screens.MyRecipes
    )
    Scaffold(bottomBar = { BottomNavigationScreen(navController = navController, items = items) }) {
        Column(
            modifier = Modifier.padding(start = 10.dp, top = 10.dp, end = 10.dp, bottom = 70.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (categories.data != null) {
                LazyColumn {
                    items(categories.data!!.categories) { category ->
                        CategoryCard(
                            strCategory = category.strCategory,
                            strCategoryThumb = category.strCategoryThumb
                        ) { strCategory ->
                           navController.navigate(TreatScreens.ExploreScreen.name+"/${strCategory}") {
                               popUpTo(navController.graph.findStartDestination().id)
                           }
                        }
                    }
                }
            }
            else {
                CircularProgressIndicator()
            }
        }
    }
}

@Composable
fun CategoryCard(strCategory: String, strCategoryThumb: String, onItemClicked: (String) -> Unit) {
    Card(
        modifier = Modifier
            .padding(10.dp)
            .clickable {
                onItemClicked(strCategory)
            },
        elevation = 10.dp,
        shape = RoundedCornerShape(corner = CornerSize(40.dp)),
        border = BorderStroke(width = 1.dp, color = Color.Blue)
    ) {
        Row(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            AsyncImage(model = strCategoryThumb, contentDescription = null)
            Spacer(modifier = Modifier.padding(10.dp))
            Text(text = strCategory)
        }
    }
}
package com.joyel.treatelicious.screens.recipepage

import android.util.Log
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.joyel.treatelicious.data.DataOrException
import com.joyel.treatelicious.model.meal.Meal
import com.joyel.treatelicious.screens.MainViewModel
import kotlin.math.log

@Composable
fun RecipeScreen(navController: NavController, mealId: String, mainViewModel: MainViewModel) {
    val scrollState = rememberScrollState()
    val uriHandler = LocalUriHandler.current
    Column(
        modifier = Modifier
            .padding(start = 10.dp, top = 10.dp, end = 10.dp, bottom = 70.dp)
            .fillMaxHeight()
            .fillMaxWidth()
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        val mealData = produceState<DataOrException<Meal, Boolean, Exception>>(
            initialValue = DataOrException(loading = true)
        ) {
            value = mainViewModel.getMealById(id = mealId)
        }.value
        if (mealData.data == null) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator()
            }
        } else {
            val mealInfo = mealData.data!!.meals[0]
            AsyncImage(
                model = mealInfo.strMealThumb,
                contentDescription = null,
                modifier = Modifier
                    .padding(1.dp)
                    .clip(shape = RoundedCornerShape(corner = CornerSize(10)))
            )
            Text(text = mealInfo.strMeal, fontWeight = FontWeight.Bold, fontSize = 25.sp)
            Row(
                modifier = Modifier
                    .padding(20.dp)
                    .height(25.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row() {
                    Text(
                        text = mealInfo.strCategory,
                        fontStyle = FontStyle.Italic,
                        color = Color.Black.copy(alpha = 0.5f)
                    )
                }
                Divider(
                    modifier = Modifier
                        .width(1.dp)
                        .fillMaxHeight()
                )
                Row() {
                    Text(
                        text = mealInfo.strArea,
                        fontStyle = FontStyle.Italic,
                        color = Color.Black.copy(alpha = 0.5f)
                    )
                }
            }
            Column(
                modifier = Modifier
                    .padding(5.dp)
                    .fillMaxWidth(), horizontalAlignment = Alignment.Start
            ) {
                Text(text = "Ingredients", fontWeight = FontWeight.SemiBold, fontSize = 20.sp)
                val listOfIngredients = listOf<String>(
                    mealInfo.strIngredient1,
                    mealInfo.strIngredient2,
                    mealInfo.strIngredient3,
                    mealInfo.strIngredient4,
                    mealInfo.strIngredient5,
                    mealInfo.strIngredient6,
                    mealInfo.strIngredient7,
                    mealInfo.strIngredient8,
                    mealInfo.strIngredient9,
                    mealInfo.strIngredient10,
                    mealInfo.strIngredient11,
                    mealInfo.strIngredient12,
                    mealInfo.strIngredient13,
                    mealInfo.strIngredient14,
                    mealInfo.strIngredient15,
                    mealInfo.strIngredient16,
                    mealInfo.strIngredient17,
                    mealInfo.strIngredient18,
                    mealInfo.strIngredient19,
                    mealInfo.strIngredient20
                )

                val listOfMeasures = listOf<String>(
                    mealInfo.strMeasure1,
                    mealInfo.strMeasure2,
                    mealInfo.strMeasure3,
                    mealInfo.strMeasure4,
                    mealInfo.strMeasure5,
                    mealInfo.strMeasure6,
                    mealInfo.strMeasure7,
                    mealInfo.strMeasure8,
                    mealInfo.strMeasure9,
                    mealInfo.strMeasure10,
                    mealInfo.strMeasure11,
                    mealInfo.strMeasure12,
                    mealInfo.strMeasure13,
                    mealInfo.strMeasure14,
                    mealInfo.strMeasure15,
                    mealInfo.strMeasure16,
                    mealInfo.strMeasure17,
                    mealInfo.strMeasure18,
                    mealInfo.strMeasure19,
                    mealInfo.strMeasure20
                )

                val base_ingredient_name = "strIngredient"
                var count = 0;
                listOfIngredients.forEach { ingredient ->
                    if (ingredient != null && ingredient != "") {
                        Row(horizontalArrangement = Arrangement.SpaceBetween) {
                            Text(
                                text = ingredient,
                                fontWeight = FontWeight.Light,
                                modifier = Modifier.padding(2.dp)
                            )
                            Text(
                                text = "(${listOfMeasures[count++]})",
                                fontWeight = FontWeight.Light,
                                modifier = Modifier.padding(2.dp)
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.padding(10.dp))
                Text(text = "Instructions", fontWeight = FontWeight.SemiBold, fontSize = 20.sp)
                Text(
                    text = mealInfo.strInstructions,
                    fontWeight = FontWeight.W300,
                    fontSize = 15.sp
                )
                Spacer(modifier = Modifier.padding(10.dp))
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (mealInfo.strYoutube != null) {
                        Button(onClick = {
                            uriHandler.openUri(mealInfo.strYoutube)
                        }) {
                            Text(text = "Watch tutorial")
                        }
                    }
                }
            }
        }
    }
}
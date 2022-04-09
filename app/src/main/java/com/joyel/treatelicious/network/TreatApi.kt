package com.joyel.treatelicious.network

import com.joyel.treatelicious.model.categories.Categories
import com.joyel.treatelicious.model.meal.Meal
import com.joyel.treatelicious.model.meal.SelectedCategory
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface TreatApi {
    @GET(value = "random.php")
    suspend fun getRandomRecipe(): Meal

    @GET(value = "lookup.php")
    suspend fun getMealById(
        @Query("i") id: String
    ): Meal

    @GET(value = "categories.php")
    suspend fun getAllCategories(): Categories

    @GET(value = "filter.php")
    suspend fun getRecipeByCategory(
        @Query("c") c: String
    ): SelectedCategory
}
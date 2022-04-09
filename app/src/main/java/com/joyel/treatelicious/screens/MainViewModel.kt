package com.joyel.treatelicious.screens

import androidx.lifecycle.ViewModel
import com.joyel.treatelicious.data.DataOrException
import com.joyel.treatelicious.model.categories.Categories
import com.joyel.treatelicious.model.meal.Meal
import com.joyel.treatelicious.model.meal.SelectedCategory
import com.joyel.treatelicious.repository.TreatRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: TreatRepository): ViewModel() {
    suspend fun getRandomRecipe(): DataOrException<Meal, Boolean, Exception> {
        return repository.getRandomRecipe()
    }

    suspend fun getMealById(id: String): DataOrException<Meal, Boolean, java.lang.Exception> {
        return repository.getMealById(id = id)
    }

    suspend fun getAllCategories(): DataOrException<Categories, Boolean, Exception> {
        return repository.getAllCategories()
    }

    suspend fun getRecipeByCategory(c: String): DataOrException<SelectedCategory, Boolean, Exception> {
        return repository.getRecipeByCategory(c)
    }
}
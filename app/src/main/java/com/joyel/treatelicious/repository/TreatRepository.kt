package com.joyel.treatelicious.repository

import com.joyel.treatelicious.data.DataOrException
import com.joyel.treatelicious.model.categories.Categories
import com.joyel.treatelicious.model.meal.Meal
import com.joyel.treatelicious.model.meal.SelectedCategory
import com.joyel.treatelicious.network.TreatApi
import java.lang.Exception
import javax.inject.Inject

class TreatRepository @Inject constructor(private val api: TreatApi) {
     suspend fun getRandomRecipe(): DataOrException<Meal, Boolean, Exception> {
         val response = try {
             api.getRandomRecipe()
         } catch (e: Exception) {
           return DataOrException(e = e)
         }

         return DataOrException(data = response)
     }

     suspend fun getMealById(id: String): DataOrException<Meal, Boolean, Exception> {
         val response = try {
             api.getMealById(id = id)
         } catch (e: Exception) {
             return DataOrException(e = e)
         }

         return DataOrException(data = response)
     }

    suspend fun getAllCategories(): DataOrException<Categories, Boolean, Exception> {
        val response = try {
            api.getAllCategories()
        } catch (e: Exception) {
            return DataOrException(e = e)
        }

        return DataOrException(data = response)
    }

    suspend fun getRecipeByCategory(c: String): DataOrException<SelectedCategory, Boolean, Exception> {
        val response = try {
            api.getRecipeByCategory(c)
        } catch (e: Exception) {
            return DataOrException(e = e)
        }

        return DataOrException(data = response)
    }
}
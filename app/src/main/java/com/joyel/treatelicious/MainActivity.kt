package com.joyel.treatelicious

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.joyel.treatelicious.navigation.BottomNavigationScreen
import com.joyel.treatelicious.navigation.Screens
import com.joyel.treatelicious.navigation.TreatNavigation
import com.joyel.treatelicious.ui.theme.TreateliciousTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TreateliciousTheme {
                Main()
            }
        }
    }
}

@Composable
fun Main() {
    val items = listOf(
        Screens.Categories,
        Screens.Explore,
        Screens.MyRecipes
    )
    Surface(color = Color.White) {
        val navController = rememberNavController()
        Scaffold(bottomBar = { BottomNavigationScreen(navController = navController, items = items)}) {
            TreatNavigation(navController = navController)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TreateliciousTheme {
        Main()
    }
}
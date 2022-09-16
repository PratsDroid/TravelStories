package com.forevertea.travelstories.navigation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.forevertea.travelstories.ui.theme.TravelStoriesTheme

class TravelActivity : ComponentActivity() {
    @ExperimentalFoundationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TravelApp()
        }
    }
}

@ExperimentalFoundationApi
@Composable
fun TravelApp() {
    TravelStoriesTheme {
        Surface() {
            val navController = rememberNavController()
            NavGraph(navController = navController)
        }
    }
}
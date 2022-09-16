package com.forevertea.travelstories.navigation

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.forevertea.travelstories.navigation.MainDestinations.STORY
import com.forevertea.travelstories.ui.screens.PreviewImages
import com.forevertea.travelstories.ui.screens.HomeScreen
import com.forevertea.travelstories.ui.screens.Story
import com.forevertea.travelstories.ui.screens.TextPage
import com.forevertea.travelstories.viewmodels.TravelActivityModel

object MainDestinations {
    const val HOME = "HOME"
    const val STORY = "STORY"
    const val STORY_ARG = "$STORY/{id}"
    const val PREVIEW = "PREVIEW"
    const val TEXT_PAGE = "TEXT_PAGE"
}

/**
 * Models the navigation actions in the app.
 */
class MainActions(navController: NavHostController) {
    val onAlbumClick: (Long) -> Unit =
        { id: Long -> navController.navigate("$STORY/$id") }
    val onPreviewImages: () -> Unit = { navController.navigate(MainDestinations.PREVIEW) }
    val navigateBack: () -> Unit = { navController.popBackStack() }
    val addTextPage: () -> Unit = { navController.navigate(MainDestinations.TEXT_PAGE) }
}


@ExperimentalFoundationApi
@Composable
fun NavGraph(
    navController: NavHostController,
    startDestination: String = MainDestinations.HOME
) {
    val travelViewModel : TravelActivityModel = viewModel()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val actions = remember(navController) { MainActions(navController) }
    NavHost(
        navController = navController,
        startDestination = startDestination,
    ) {
        composable(
            route = MainDestinations.HOME,
        ) {
            HomeScreen(
                action =
                actions.onAlbumClick, editAction = actions.onPreviewImages, travelViewModel
            )
        }
        composable(
            route = MainDestinations.STORY_ARG,
            arguments = listOf(navArgument("id") { type = NavType.LongType })
        ) { backStackEntry ->
            Log.d("Story", "Story call")
            Story(backStackEntry.arguments?.getLong("id"), travelViewModel)
        }
        composable(
            route = MainDestinations.PREVIEW
        ) {
            PreviewImages(travelViewModel, actions.navigateBack, actions.addTextPage)
        }
        composable(
            route = MainDestinations.TEXT_PAGE
        ){
            TextPage(travelActivityModel = travelViewModel, actions.navigateBack)
        }
    }
}



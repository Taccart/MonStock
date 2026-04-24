package com.monstock.app.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.monstock.app.ui.screens.addedit.AddEditItemScreen
import com.monstock.app.ui.screens.home.HomeScreen
import com.monstock.app.ui.screens.itemdetail.ItemDetailScreen
import com.monstock.app.ui.screens.itemlist.ItemListScreen
import com.monstock.app.ui.screens.pantry.PantryListScreen
import com.monstock.app.ui.screens.shelf.ShelfScreen

sealed class Screen(val route: String) {
    data object Home : Screen("home")
    data object PantryList : Screen("pantries")
    data object ShelfList : Screen("pantries/{pantryId}/shelves") {
        fun createRoute(pantryId: Long) = "pantries/$pantryId/shelves"
    }
    data object ItemList : Screen("shelves/{shelfId}/items") {
        fun createRoute(shelfId: Long) = "shelves/$shelfId/items"
    }
    data object AllItems : Screen("items")
    data object ItemDetail : Screen("items/{itemId}") {
        fun createRoute(itemId: Long) = "items/$itemId"
    }
    data object AddEditItem : Screen("add_edit_item?itemId={itemId}&shelfId={shelfId}") {
        fun createAddRoute(shelfId: Long) = "add_edit_item?shelfId=$shelfId"
        fun createEditRoute(itemId: Long) = "add_edit_item?itemId=$itemId"
    }
}

@Composable
fun MonStockNavHost(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {
            HomeScreen(navController = navController)
        }

        composable(Screen.PantryList.route) {
            PantryListScreen(navController = navController)
        }

        composable(
            route = Screen.ShelfList.route,
            arguments = listOf(navArgument("pantryId") { type = NavType.LongType })
        ) { backStackEntry ->
            val pantryId = backStackEntry.arguments?.getLong("pantryId") ?: return@composable
            ShelfScreen(pantryId = pantryId, navController = navController)
        }

        composable(
            route = Screen.ItemList.route,
            arguments = listOf(navArgument("shelfId") { type = NavType.LongType })
        ) { backStackEntry ->
            val shelfId = backStackEntry.arguments?.getLong("shelfId") ?: return@composable
            ItemListScreen(shelfId = shelfId, navController = navController)
        }

        composable(Screen.AllItems.route) {
            ItemListScreen(shelfId = null, navController = navController)
        }

        composable(
            route = Screen.ItemDetail.route,
            arguments = listOf(navArgument("itemId") { type = NavType.LongType })
        ) { backStackEntry ->
            val itemId = backStackEntry.arguments?.getLong("itemId") ?: return@composable
            ItemDetailScreen(itemId = itemId, navController = navController)
        }

        composable(
            route = Screen.AddEditItem.route,
            arguments = listOf(
                navArgument("itemId") {
                    type = NavType.LongType
                    defaultValue = -1L
                },
                navArgument("shelfId") {
                    type = NavType.LongType
                    defaultValue = -1L
                }
            )
        ) { backStackEntry ->
            val itemId = backStackEntry.arguments?.getLong("itemId")?.takeIf { it != -1L }
            val shelfId = backStackEntry.arguments?.getLong("shelfId")?.takeIf { it != -1L }
            AddEditItemScreen(itemId = itemId, shelfId = shelfId, navController = navController)
        }
    }
}

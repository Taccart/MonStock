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
import com.monstock.app.ui.screens.scanner.BarcodeConfirmScreen
import com.monstock.app.ui.screens.scanner.BarcodeScannerScreen
import com.monstock.app.ui.screens.scanner.BatchResultScreen
import com.monstock.app.ui.screens.scanner.QrLabelScreen
import com.monstock.app.ui.screens.scanner.RestockScanScreen
import com.monstock.app.ui.screens.scanner.ScannerMode
import com.monstock.app.ui.screens.shelf.ShelfScreen
import com.monstock.app.ui.screens.statistics.StatisticsScreen
import com.monstock.app.ui.screens.inventory.InventoryDiscrepancyScreen
import com.monstock.app.ui.screens.inventory.InventoryHistoryDetailScreen
import com.monstock.app.ui.screens.inventory.InventoryListScreen
import com.monstock.app.ui.screens.inventory.InventorySessionScreen

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
    data object AddEditItem : Screen(
        "add_edit_item?itemId={itemId}&shelfId={shelfId}&barcode={barcode}&name={name}&brand={brand}&category={category}&unit={unit}&imageUrl={imageUrl}"
    ) {
        fun createAddRoute(shelfId: Long?) =
            "add_edit_item?shelfId=${shelfId ?: -1L}"

        fun createEditRoute(itemId: Long) =
            "add_edit_item?itemId=$itemId"

        fun createAddRouteWithBarcode(
            shelfId: Long?,
            barcode: String,
            name: String = "",
            brand: String = "",
            category: String = "",
            unit: String = "",
            imageUrl: String? = null
        ): String {
            val sb = StringBuilder("add_edit_item?")
            sb.append("shelfId=${shelfId ?: -1L}")
            sb.append("&barcode=${barcode.urlEncode()}")
            if (name.isNotBlank()) sb.append("&name=${name.urlEncode()}")
            if (brand.isNotBlank()) sb.append("&brand=${brand.urlEncode()}")
            if (category.isNotBlank()) sb.append("&category=${category.urlEncode()}")
            if (unit.isNotBlank()) sb.append("&unit=${unit.urlEncode()}")
            imageUrl?.let { sb.append("&imageUrl=${it.urlEncode()}") }
            return sb.toString()
        }
    }

    // ── Phase 5 ──────────────────────────────────────────────────────────────

    data object BarcodeScanner : Screen("scanner/{mode}?shelfId={shelfId}") {
        fun createRoute(mode: ScannerMode, shelfId: Long? = null) =
            "scanner/${mode.name}?shelfId=${shelfId ?: -1L}"
    }

    data object BarcodeConfirm : Screen("barcode_confirm/{barcode}?shelfId={shelfId}") {
        fun createRoute(barcode: String, shelfId: Long? = null) =
            "barcode_confirm/${barcode.urlEncode()}?shelfId=${shelfId ?: -1L}"
    }

    data object RestockScan : Screen("restock/{barcode}") {
        fun createRoute(barcode: String) = "restock/${barcode.urlEncode()}"
    }

    data object QrLabel : Screen("qr_label/{itemId}") {
        fun createRoute(itemId: Long) = "qr_label/$itemId"
    }

    data object BatchResult : Screen("batch_result?shelfId={shelfId}") {
        fun createRoute(shelfId: Long? = null) = "batch_result?shelfId=${shelfId ?: -1L}"
    }

    // ── Phase 4 ──────────────────────────────────────────────────────────────

    data object Statistics : Screen("statistics")
    // ── Phase 6 ────────────────────────────────────────────────────────────────────────

    data object Inventory : Screen("inventory")

    data object InventorySession : Screen("inventory/session/{sessionId}") {
        fun createRoute(sessionId: Long) = "inventory/session/$sessionId"
    }

    data object InventoryDiscrepancy : Screen("inventory/discrepancy/{sessionId}") {
        fun createRoute(sessionId: Long) = "inventory/discrepancy/$sessionId"
    }

    data object InventoryHistoryDetail : Screen("inventory/history/{sessionId}") {
        fun createRoute(sessionId: Long) = "inventory/history/$sessionId"
    }}

private fun String.urlEncode(): String =
    java.net.URLEncoder.encode(this, "UTF-8")

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
                navArgument("itemId") { type = NavType.LongType; defaultValue = -1L },
                navArgument("shelfId") { type = NavType.LongType; defaultValue = -1L },
                navArgument("barcode") { type = NavType.StringType; defaultValue = "" },
                navArgument("name") { type = NavType.StringType; defaultValue = "" },
                navArgument("brand") { type = NavType.StringType; defaultValue = "" },
                navArgument("category") { type = NavType.StringType; defaultValue = "" },
                navArgument("unit") { type = NavType.StringType; defaultValue = "" },
                navArgument("imageUrl") { type = NavType.StringType; defaultValue = "" }
            )
        ) { backStackEntry ->
            val args = backStackEntry.arguments
            val itemId = args?.getLong("itemId")?.takeIf { it != -1L }
            val shelfId = args?.getLong("shelfId")?.takeIf { it != -1L }
            val barcode = args?.getString("barcode")?.ifBlank { null }
            val name = args?.getString("name")?.ifBlank { null }
            val brand = args?.getString("brand")?.ifBlank { null }
            val category = args?.getString("category")?.ifBlank { null }
            val unit = args?.getString("unit")?.ifBlank { null }
            val imageUrl = args?.getString("imageUrl")?.ifBlank { null }
            AddEditItemScreen(
                itemId = itemId,
                shelfId = shelfId,
                prefillBarcode = barcode,
                prefillName = name,
                prefillBrand = brand,
                prefillCategory = category,
                prefillUnit = unit,
                prefillImageUrl = imageUrl,
                navController = navController
            )
        }

        // ── Phase 5 routes ────────────────────────────────────────────────────

        composable(
            route = Screen.BarcodeScanner.route,
            arguments = listOf(
                navArgument("mode") { type = NavType.StringType },
                navArgument("shelfId") { type = NavType.LongType; defaultValue = -1L }
            )
        ) { backStackEntry ->
            val modeStr = backStackEntry.arguments?.getString("mode") ?: ScannerMode.ADD_ITEM.name
            val mode = runCatching { ScannerMode.valueOf(modeStr) }.getOrDefault(ScannerMode.ADD_ITEM)
            val shelfId = backStackEntry.arguments?.getLong("shelfId")?.takeIf { it != -1L }
            BarcodeScannerScreen(mode = mode, shelfId = shelfId, navController = navController)
        }

        composable(
            route = Screen.BarcodeConfirm.route,
            arguments = listOf(
                navArgument("barcode") { type = NavType.StringType },
                navArgument("shelfId") { type = NavType.LongType; defaultValue = -1L }
            )
        ) { backStackEntry ->
            val barcode = backStackEntry.arguments?.getString("barcode") ?: return@composable
            val shelfId = backStackEntry.arguments?.getLong("shelfId")?.takeIf { it != -1L }
            BarcodeConfirmScreen(barcode = barcode, shelfId = shelfId, navController = navController)
        }

        composable(
            route = Screen.RestockScan.route,
            arguments = listOf(navArgument("barcode") { type = NavType.StringType })
        ) { backStackEntry ->
            val barcode = backStackEntry.arguments?.getString("barcode") ?: return@composable
            RestockScanScreen(barcode = barcode, navController = navController)
        }

        composable(
            route = Screen.QrLabel.route,
            arguments = listOf(navArgument("itemId") { type = NavType.LongType })
        ) { backStackEntry ->
            val itemId = backStackEntry.arguments?.getLong("itemId") ?: return@composable
            QrLabelScreen(itemId = itemId, navController = navController)
        }

        composable(
            route = Screen.BatchResult.route,
            arguments = listOf(
                navArgument("shelfId") { type = NavType.LongType; defaultValue = -1L }
            )
        ) { backStackEntry ->
            val shelfId = backStackEntry.arguments?.getLong("shelfId")?.takeIf { it != -1L }
            BatchResultScreen(shelfId = shelfId, navController = navController)
        }

        // ── Phase 4 routes ────────────────────────────────────────────────────

        composable(Screen.Statistics.route) {
            StatisticsScreen(navController = navController)
        }
        // ── Phase 6 routes ───────────────────────────────────────────────────────────────────────

        composable(Screen.Inventory.route) {
            InventoryListScreen(navController = navController)
        }

        composable(
            route = Screen.InventorySession.route,
            arguments = listOf(navArgument("sessionId") { type = NavType.LongType })
        ) { backStackEntry ->
            val sessionId = backStackEntry.arguments?.getLong("sessionId") ?: return@composable
            InventorySessionScreen(sessionId = sessionId, navController = navController)
        }

        composable(
            route = Screen.InventoryDiscrepancy.route,
            arguments = listOf(navArgument("sessionId") { type = NavType.LongType })
        ) { backStackEntry ->
            val sessionId = backStackEntry.arguments?.getLong("sessionId") ?: return@composable
            InventoryDiscrepancyScreen(sessionId = sessionId, navController = navController)
        }

        composable(
            route = Screen.InventoryHistoryDetail.route,
            arguments = listOf(navArgument("sessionId") { type = NavType.LongType })
        ) { backStackEntry ->
            val sessionId = backStackEntry.arguments?.getLong("sessionId") ?: return@composable
            InventoryHistoryDetailScreen(sessionId = sessionId, navController = navController)
        }    }
}

package com.monstock.app.ui.screens.itemdetail;

import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.material.icons.Icons;
import androidx.compose.material3.CardDefaults;
import androidx.compose.material3.ExperimentalMaterial3Api;
import androidx.compose.runtime.Composable;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.text.font.FontWeight;
import androidx.navigation.NavController;
import com.monstock.app.R;
import com.monstock.app.domain.model.Item;
import com.monstock.app.ui.navigation.Screen;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000B\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a,\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00062\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\tH\u0003\u00f8\u0001\u0000\u00a2\u0006\u0002\b\n\u001a$\u0010\u000b\u001a\u00020\u00042\u0006\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u00062\b\b\u0002\u0010\u000f\u001a\u00020\u0010H\u0003\u001a\"\u0010\u0011\u001a\u00020\u00042\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00152\b\b\u0002\u0010\u0016\u001a\u00020\u0017H\u0007\"\u0016\u0010\u0000\u001a\n \u0002*\u0004\u0018\u00010\u00010\u0001X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u0007\n\u0005\b\u00a1\u001e0\u0001\u00a8\u0006\u0018"}, d2 = {"dateFormatter", "Ljava/time/format/DateTimeFormatter;", "kotlin.jvm.PlatformType", "DetailRow", "", "label", "", "value", "valueColor", "Landroidx/compose/ui/graphics/Color;", "DetailRow-t9lfQc4", "ItemDetailContent", "item", "Lcom/monstock/app/domain/model/Item;", "shelfName", "modifier", "Landroidx/compose/ui/Modifier;", "ItemDetailScreen", "itemId", "", "navController", "Landroidx/navigation/NavController;", "viewModel", "Lcom/monstock/app/ui/screens/itemdetail/ItemDetailViewModel;", "app_debug"})
public final class ItemDetailScreenKt {
    private static final java.time.format.DateTimeFormatter dateFormatter = null;
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    public static final void ItemDetailScreen(long itemId, @org.jetbrains.annotations.NotNull()
    androidx.navigation.NavController navController, @org.jetbrains.annotations.NotNull()
    com.monstock.app.ui.screens.itemdetail.ItemDetailViewModel viewModel) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void ItemDetailContent(com.monstock.app.domain.model.Item item, java.lang.String shelfName, androidx.compose.ui.Modifier modifier) {
    }
}
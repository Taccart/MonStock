package com.monstock.app.ui.screens.addedit;

import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.foundation.text.KeyboardOptions;
import androidx.compose.material.icons.Icons;
import androidx.compose.material3.ExperimentalMaterial3Api;
import androidx.compose.material3.ExposedDropdownMenuDefaults;
import androidx.compose.runtime.Composable;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.text.input.KeyboardCapitalization;
import androidx.compose.ui.text.input.KeyboardType;
import androidx.navigation.NavController;
import com.monstock.app.R;
import com.monstock.app.data.model.ItemCategory;
import com.monstock.app.data.model.ItemUnit;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000J\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a3\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\u00062\u0006\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\n\u001a\u00020\u000bH\u0007\u00a2\u0006\u0002\u0010\f\u001a0\u0010\r\u001a\u00020\u00042\u0006\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u00112\u0014\u0010\u0012\u001a\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u0011\u0012\u0004\u0012\u00020\u00040\u0013H\u0003\u001ac\u0010\u0014\u001a\u00020\u0004\"\u0004\b\u0000\u0010\u00152\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0016\u001a\u0002H\u00152\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u0002H\u00150\u00182\u0012\u0010\u0019\u001a\u000e\u0012\u0004\u0012\u0002H\u0015\u0012\u0004\u0012\u00020\u000f0\u00132\u0012\u0010\u001a\u001a\u000e\u0012\u0004\u0012\u0002H\u0015\u0012\u0004\u0012\u00020\u00040\u00132\b\b\u0002\u0010\u001b\u001a\u00020\u001cH\u0003\u00a2\u0006\u0002\u0010\u001d\"\u0016\u0010\u0000\u001a\n \u0002*\u0004\u0018\u00010\u00010\u0001X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001e"}, d2 = {"dateFormatter", "Ljava/time/format/DateTimeFormatter;", "kotlin.jvm.PlatformType", "AddEditItemScreen", "", "itemId", "", "shelfId", "navController", "Landroidx/navigation/NavController;", "viewModel", "Lcom/monstock/app/ui/screens/addedit/AddEditItemViewModel;", "(Ljava/lang/Long;Ljava/lang/Long;Landroidx/navigation/NavController;Lcom/monstock/app/ui/screens/addedit/AddEditItemViewModel;)V", "DateField", "label", "", "date", "Ljava/time/LocalDate;", "onDateSelected", "Lkotlin/Function1;", "EnumDropdown", "T", "selected", "options", "", "displayName", "onSelect", "modifier", "Landroidx/compose/ui/Modifier;", "(Ljava/lang/String;Ljava/lang/Object;Ljava/util/List;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;Landroidx/compose/ui/Modifier;)V", "app_debug"})
public final class AddEditItemScreenKt {
    private static final java.time.format.DateTimeFormatter dateFormatter = null;
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    public static final void AddEditItemScreen(@org.jetbrains.annotations.Nullable()
    java.lang.Long itemId, @org.jetbrains.annotations.Nullable()
    java.lang.Long shelfId, @org.jetbrains.annotations.NotNull()
    androidx.navigation.NavController navController, @org.jetbrains.annotations.NotNull()
    com.monstock.app.ui.screens.addedit.AddEditItemViewModel viewModel) {
    }
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    private static final <T extends java.lang.Object>void EnumDropdown(java.lang.String label, T selected, java.util.List<? extends T> options, kotlin.jvm.functions.Function1<? super T, java.lang.String> displayName, kotlin.jvm.functions.Function1<? super T, kotlin.Unit> onSelect, androidx.compose.ui.Modifier modifier) {
    }
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    private static final void DateField(java.lang.String label, java.time.LocalDate date, kotlin.jvm.functions.Function1<? super java.time.LocalDate, kotlin.Unit> onDateSelected) {
    }
}
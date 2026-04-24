package com.monstock.app.ui.screens.addedit;

import androidx.lifecycle.ViewModel;
import com.monstock.app.data.model.ItemCategory;
import com.monstock.app.data.model.ItemUnit;
import com.monstock.app.data.repository.ItemRepository;
import com.monstock.app.data.repository.PantryRepository;
import com.monstock.app.data.repository.ShelfRepository;
import com.monstock.app.domain.model.Item;
import com.monstock.app.domain.model.Pantry;
import com.monstock.app.domain.model.Shelf;
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StateFlow;
import java.time.LocalDate;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000d\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0007\u0018\u00002\u00020\u0001B\u001f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\u001f\u0010\u0014\u001a\u00020\u00152\b\u0010\u0016\u001a\u0004\u0018\u00010\u00172\b\u0010\u0018\u001a\u0004\u0018\u00010\u0017\u00a2\u0006\u0002\u0010\u0019J\u000e\u0010\u001a\u001a\u00020\u00152\u0006\u0010\u001b\u001a\u00020\u0017J\u000e\u0010\u001c\u001a\u00020\u00152\u0006\u0010\u001d\u001a\u00020\u001eJ\u000e\u0010\u001f\u001a\u00020\u00152\u0006\u0010\u001d\u001a\u00020\u001eJ\u000e\u0010 \u001a\u00020\u00152\u0006\u0010\u001d\u001a\u00020!J\u0010\u0010\"\u001a\u00020\u00152\b\u0010\u001d\u001a\u0004\u0018\u00010#J\u000e\u0010$\u001a\u00020\u00152\u0006\u0010\u001d\u001a\u00020\u001eJ\u000e\u0010%\u001a\u00020\u00152\u0006\u0010\u001d\u001a\u00020\u001eJ\u000e\u0010&\u001a\u00020\u00152\u0006\u0010\u001d\u001a\u00020\u001eJ\u0010\u0010\'\u001a\u00020\u00152\b\u0010\u001d\u001a\u0004\u0018\u00010#J\u000e\u0010(\u001a\u00020\u00152\u0006\u0010\u001d\u001a\u00020\u001eJ\u000e\u0010)\u001a\u00020\u00152\u0006\u0010\u0018\u001a\u00020\u0017J\u000e\u0010*\u001a\u00020\u00152\u0006\u0010\u001d\u001a\u00020+J\u0015\u0010,\u001a\u00020\u00152\b\u0010-\u001a\u0004\u0018\u00010\u0017\u00a2\u0006\u0002\u0010.R\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000f0\u000e0\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u000b0\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0011\u00a8\u0006/"}, d2 = {"Lcom/monstock/app/ui/screens/addedit/AddEditItemViewModel;", "Landroidx/lifecycle/ViewModel;", "itemRepository", "Lcom/monstock/app/data/repository/ItemRepository;", "shelfRepository", "Lcom/monstock/app/data/repository/ShelfRepository;", "pantryRepository", "Lcom/monstock/app/data/repository/PantryRepository;", "(Lcom/monstock/app/data/repository/ItemRepository;Lcom/monstock/app/data/repository/ShelfRepository;Lcom/monstock/app/data/repository/PantryRepository;)V", "_uiState", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/monstock/app/ui/screens/addedit/AddEditItemUiState;", "allPantries", "Lkotlinx/coroutines/flow/StateFlow;", "", "Lcom/monstock/app/domain/model/Pantry;", "getAllPantries", "()Lkotlinx/coroutines/flow/StateFlow;", "uiState", "getUiState", "init", "", "itemId", "", "shelfId", "(Ljava/lang/Long;Ljava/lang/Long;)V", "loadShelvesForPantry", "pantryId", "onBarcodeChange", "v", "", "onBrandChange", "onCategoryChange", "Lcom/monstock/app/data/model/ItemCategory;", "onExpiryDateChange", "Ljava/time/LocalDate;", "onMinThresholdChange", "onNameChange", "onNotesChange", "onPurchaseDateChange", "onQuantityChange", "onShelfSelected", "onUnitChange", "Lcom/monstock/app/data/model/ItemUnit;", "save", "existingItemId", "(Ljava/lang/Long;)V", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class AddEditItemViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.monstock.app.data.repository.ItemRepository itemRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.monstock.app.data.repository.ShelfRepository shelfRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.monstock.app.data.repository.PantryRepository pantryRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.monstock.app.ui.screens.addedit.AddEditItemUiState> _uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.monstock.app.ui.screens.addedit.AddEditItemUiState> uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.monstock.app.domain.model.Pantry>> allPantries = null;
    
    @javax.inject.Inject()
    public AddEditItemViewModel(@org.jetbrains.annotations.NotNull()
    com.monstock.app.data.repository.ItemRepository itemRepository, @org.jetbrains.annotations.NotNull()
    com.monstock.app.data.repository.ShelfRepository shelfRepository, @org.jetbrains.annotations.NotNull()
    com.monstock.app.data.repository.PantryRepository pantryRepository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.monstock.app.ui.screens.addedit.AddEditItemUiState> getUiState() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.monstock.app.domain.model.Pantry>> getAllPantries() {
        return null;
    }
    
    public final void init(@org.jetbrains.annotations.Nullable()
    java.lang.Long itemId, @org.jetbrains.annotations.Nullable()
    java.lang.Long shelfId) {
    }
    
    public final void loadShelvesForPantry(long pantryId) {
    }
    
    public final void onNameChange(@org.jetbrains.annotations.NotNull()
    java.lang.String v) {
    }
    
    public final void onBrandChange(@org.jetbrains.annotations.NotNull()
    java.lang.String v) {
    }
    
    public final void onCategoryChange(@org.jetbrains.annotations.NotNull()
    com.monstock.app.data.model.ItemCategory v) {
    }
    
    public final void onQuantityChange(@org.jetbrains.annotations.NotNull()
    java.lang.String v) {
    }
    
    public final void onUnitChange(@org.jetbrains.annotations.NotNull()
    com.monstock.app.data.model.ItemUnit v) {
    }
    
    public final void onPurchaseDateChange(@org.jetbrains.annotations.Nullable()
    java.time.LocalDate v) {
    }
    
    public final void onExpiryDateChange(@org.jetbrains.annotations.Nullable()
    java.time.LocalDate v) {
    }
    
    public final void onMinThresholdChange(@org.jetbrains.annotations.NotNull()
    java.lang.String v) {
    }
    
    public final void onBarcodeChange(@org.jetbrains.annotations.NotNull()
    java.lang.String v) {
    }
    
    public final void onNotesChange(@org.jetbrains.annotations.NotNull()
    java.lang.String v) {
    }
    
    public final void onShelfSelected(long shelfId) {
    }
    
    public final void save(@org.jetbrains.annotations.Nullable()
    java.lang.Long existingItemId) {
    }
}
package com.monstock.app.ui.screens.itemlist;

import androidx.lifecycle.ViewModel;
import com.monstock.app.data.model.ItemCategory;
import com.monstock.app.data.repository.ItemRepository;
import com.monstock.app.data.repository.ShelfRepository;
import com.monstock.app.domain.model.Item;
import com.monstock.app.domain.model.Shelf;
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.ExperimentalCoroutinesApi;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StateFlow;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000`\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\u0017\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u0006\u0010\u0015\u001a\u00020\u0016J\u0006\u0010\u0017\u001a\u00020\u0016J\u0015\u0010\u0018\u001a\u00020\u00162\b\u0010\u0019\u001a\u0004\u0018\u00010\r\u00a2\u0006\u0002\u0010\u001aJ\u0010\u0010\u001b\u001a\u00020\u00162\b\u0010\u001c\u001a\u0004\u0018\u00010\u001dJ\u000e\u0010\u001e\u001a\u00020\u00162\u0006\u0010\u001f\u001a\u00020 J\u000e\u0010!\u001a\u00020\u00162\u0006\u0010\"\u001a\u00020#J\u000e\u0010$\u001a\u00020\u00162\u0006\u0010%\u001a\u00020&J\u000e\u0010\'\u001a\u00020\u00162\u0006\u0010(\u001a\u00020\nR\u001a\u0010\u0007\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\u000b\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\r0\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u000f0\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\u0010\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t0\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u000f0\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0012\u00a8\u0006)"}, d2 = {"Lcom/monstock/app/ui/screens/itemlist/ItemListViewModel;", "Landroidx/lifecycle/ViewModel;", "itemRepository", "Lcom/monstock/app/data/repository/ItemRepository;", "shelfRepository", "Lcom/monstock/app/data/repository/ShelfRepository;", "(Lcom/monstock/app/data/repository/ItemRepository;Lcom/monstock/app/data/repository/ShelfRepository;)V", "_rawItems", "Lkotlinx/coroutines/flow/StateFlow;", "", "Lcom/monstock/app/domain/model/Item;", "_shelfId", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "_uiState", "Lcom/monstock/app/ui/screens/itemlist/ItemListUiState;", "items", "getItems", "()Lkotlinx/coroutines/flow/StateFlow;", "uiState", "getUiState", "cancelDelete", "", "confirmDelete", "init", "shelfId", "(Ljava/lang/Long;)V", "onCategoryFilterChange", "cat", "Lcom/monstock/app/data/model/ItemCategory;", "onExpiryFilterChange", "filter", "Lcom/monstock/app/ui/screens/itemlist/ExpiryFilter;", "onSearchQueryChange", "query", "", "onSortOrderChange", "order", "Lcom/monstock/app/ui/screens/itemlist/SortOrder;", "requestDelete", "item", "app_debug"})
@kotlin.OptIn(markerClass = {kotlinx.coroutines.ExperimentalCoroutinesApi.class})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class ItemListViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.monstock.app.data.repository.ItemRepository itemRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.monstock.app.data.repository.ShelfRepository shelfRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.Long> _shelfId = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.monstock.app.ui.screens.itemlist.ItemListUiState> _uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.monstock.app.ui.screens.itemlist.ItemListUiState> uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.monstock.app.domain.model.Item>> _rawItems = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.monstock.app.domain.model.Item>> items = null;
    
    @javax.inject.Inject()
    public ItemListViewModel(@org.jetbrains.annotations.NotNull()
    com.monstock.app.data.repository.ItemRepository itemRepository, @org.jetbrains.annotations.NotNull()
    com.monstock.app.data.repository.ShelfRepository shelfRepository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.monstock.app.ui.screens.itemlist.ItemListUiState> getUiState() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.monstock.app.domain.model.Item>> getItems() {
        return null;
    }
    
    public final void init(@org.jetbrains.annotations.Nullable()
    java.lang.Long shelfId) {
    }
    
    public final void onSearchQueryChange(@org.jetbrains.annotations.NotNull()
    java.lang.String query) {
    }
    
    public final void onSortOrderChange(@org.jetbrains.annotations.NotNull()
    com.monstock.app.ui.screens.itemlist.SortOrder order) {
    }
    
    public final void onCategoryFilterChange(@org.jetbrains.annotations.Nullable()
    com.monstock.app.data.model.ItemCategory cat) {
    }
    
    public final void onExpiryFilterChange(@org.jetbrains.annotations.NotNull()
    com.monstock.app.ui.screens.itemlist.ExpiryFilter filter) {
    }
    
    public final void requestDelete(@org.jetbrains.annotations.NotNull()
    com.monstock.app.domain.model.Item item) {
    }
    
    public final void cancelDelete() {
    }
    
    public final void confirmDelete() {
    }
}
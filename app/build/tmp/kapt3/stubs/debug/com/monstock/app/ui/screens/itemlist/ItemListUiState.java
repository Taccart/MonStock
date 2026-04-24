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

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0015\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u0001BG\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\t\u0012\b\b\u0002\u0010\n\u001a\u00020\u000b\u0012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\r\u00a2\u0006\u0002\u0010\u000eJ\u000b\u0010\u001b\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\t\u0010\u001c\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\u001d\u001a\u00020\u0007H\u00c6\u0003J\u000b\u0010\u001e\u001a\u0004\u0018\u00010\tH\u00c6\u0003J\t\u0010\u001f\u001a\u00020\u000bH\u00c6\u0003J\u000b\u0010 \u001a\u0004\u0018\u00010\rH\u00c6\u0003JK\u0010!\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00072\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\t2\b\b\u0002\u0010\n\u001a\u00020\u000b2\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\rH\u00c6\u0001J\u0013\u0010\"\u001a\u00020#2\b\u0010$\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010%\u001a\u00020&H\u00d6\u0001J\t\u0010\'\u001a\u00020\u0005H\u00d6\u0001R\u0013\u0010\b\u001a\u0004\u0018\u00010\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\n\u001a\u00020\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0013\u0010\f\u001a\u0004\u0018\u00010\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0011\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001a\u00a8\u0006("}, d2 = {"Lcom/monstock/app/ui/screens/itemlist/ItemListUiState;", "", "shelf", "Lcom/monstock/app/domain/model/Shelf;", "searchQuery", "", "sortOrder", "Lcom/monstock/app/ui/screens/itemlist/SortOrder;", "categoryFilter", "Lcom/monstock/app/data/model/ItemCategory;", "expiryFilter", "Lcom/monstock/app/ui/screens/itemlist/ExpiryFilter;", "pendingDeleteItem", "Lcom/monstock/app/domain/model/Item;", "(Lcom/monstock/app/domain/model/Shelf;Ljava/lang/String;Lcom/monstock/app/ui/screens/itemlist/SortOrder;Lcom/monstock/app/data/model/ItemCategory;Lcom/monstock/app/ui/screens/itemlist/ExpiryFilter;Lcom/monstock/app/domain/model/Item;)V", "getCategoryFilter", "()Lcom/monstock/app/data/model/ItemCategory;", "getExpiryFilter", "()Lcom/monstock/app/ui/screens/itemlist/ExpiryFilter;", "getPendingDeleteItem", "()Lcom/monstock/app/domain/model/Item;", "getSearchQuery", "()Ljava/lang/String;", "getShelf", "()Lcom/monstock/app/domain/model/Shelf;", "getSortOrder", "()Lcom/monstock/app/ui/screens/itemlist/SortOrder;", "component1", "component2", "component3", "component4", "component5", "component6", "copy", "equals", "", "other", "hashCode", "", "toString", "app_debug"})
public final class ItemListUiState {
    @org.jetbrains.annotations.Nullable()
    private final com.monstock.app.domain.model.Shelf shelf = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String searchQuery = null;
    @org.jetbrains.annotations.NotNull()
    private final com.monstock.app.ui.screens.itemlist.SortOrder sortOrder = null;
    @org.jetbrains.annotations.Nullable()
    private final com.monstock.app.data.model.ItemCategory categoryFilter = null;
    @org.jetbrains.annotations.NotNull()
    private final com.monstock.app.ui.screens.itemlist.ExpiryFilter expiryFilter = null;
    @org.jetbrains.annotations.Nullable()
    private final com.monstock.app.domain.model.Item pendingDeleteItem = null;
    
    @org.jetbrains.annotations.Nullable()
    public final com.monstock.app.domain.model.Shelf component1() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component2() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.monstock.app.ui.screens.itemlist.SortOrder component3() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.monstock.app.data.model.ItemCategory component4() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.monstock.app.ui.screens.itemlist.ExpiryFilter component5() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.monstock.app.domain.model.Item component6() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.monstock.app.ui.screens.itemlist.ItemListUiState copy(@org.jetbrains.annotations.Nullable()
    com.monstock.app.domain.model.Shelf shelf, @org.jetbrains.annotations.NotNull()
    java.lang.String searchQuery, @org.jetbrains.annotations.NotNull()
    com.monstock.app.ui.screens.itemlist.SortOrder sortOrder, @org.jetbrains.annotations.Nullable()
    com.monstock.app.data.model.ItemCategory categoryFilter, @org.jetbrains.annotations.NotNull()
    com.monstock.app.ui.screens.itemlist.ExpiryFilter expiryFilter, @org.jetbrains.annotations.Nullable()
    com.monstock.app.domain.model.Item pendingDeleteItem) {
        return null;
    }
    
    @java.lang.Override()
    public boolean equals(@org.jetbrains.annotations.Nullable()
    java.lang.Object other) {
        return false;
    }
    
    @java.lang.Override()
    public int hashCode() {
        return 0;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public java.lang.String toString() {
        return null;
    }
    
    public ItemListUiState(@org.jetbrains.annotations.Nullable()
    com.monstock.app.domain.model.Shelf shelf, @org.jetbrains.annotations.NotNull()
    java.lang.String searchQuery, @org.jetbrains.annotations.NotNull()
    com.monstock.app.ui.screens.itemlist.SortOrder sortOrder, @org.jetbrains.annotations.Nullable()
    com.monstock.app.data.model.ItemCategory categoryFilter, @org.jetbrains.annotations.NotNull()
    com.monstock.app.ui.screens.itemlist.ExpiryFilter expiryFilter, @org.jetbrains.annotations.Nullable()
    com.monstock.app.domain.model.Item pendingDeleteItem) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.monstock.app.domain.model.Shelf getShelf() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getSearchQuery() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.monstock.app.ui.screens.itemlist.SortOrder getSortOrder() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.monstock.app.data.model.ItemCategory getCategoryFilter() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.monstock.app.ui.screens.itemlist.ExpiryFilter getExpiryFilter() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.monstock.app.domain.model.Item getPendingDeleteItem() {
        return null;
    }
    
    public ItemListUiState() {
        super();
    }
}
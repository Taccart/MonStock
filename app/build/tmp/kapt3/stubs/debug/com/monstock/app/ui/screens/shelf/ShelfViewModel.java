package com.monstock.app.ui.screens.shelf;

import androidx.lifecycle.ViewModel;
import com.monstock.app.data.repository.PantryRepository;
import com.monstock.app.data.repository.ShelfRepository;
import com.monstock.app.domain.model.Pantry;
import com.monstock.app.domain.model.Shelf;
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StateFlow;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\t\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0017\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u0006\u0010\u0015\u001a\u00020\u0016J\u0006\u0010\u0017\u001a\u00020\u0016J\u0006\u0010\u0018\u001a\u00020\u0016J\u000e\u0010\u0019\u001a\u00020\u00162\u0006\u0010\r\u001a\u00020\u000eJ\u0006\u0010\u001a\u001a\u00020\u0016J\u000e\u0010\u001b\u001a\u00020\u00162\u0006\u0010\u001c\u001a\u00020\nJ\u000e\u0010\u001d\u001a\u00020\u00162\u0006\u0010\u001c\u001a\u00020\nJ\u001d\u0010\u001e\u001a\u00020\u00162\u0006\u0010\u001f\u001a\u00020 2\b\u0010!\u001a\u0004\u0018\u00010\"\u00a2\u0006\u0002\u0010#R\u001a\u0010\u0007\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\f0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\u000f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t0\u0010\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0017\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\f0\u0010\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0012\u00a8\u0006$"}, d2 = {"Lcom/monstock/app/ui/screens/shelf/ShelfViewModel;", "Landroidx/lifecycle/ViewModel;", "shelfRepository", "Lcom/monstock/app/data/repository/ShelfRepository;", "pantryRepository", "Lcom/monstock/app/data/repository/PantryRepository;", "(Lcom/monstock/app/data/repository/ShelfRepository;Lcom/monstock/app/data/repository/PantryRepository;)V", "_shelves", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "Lcom/monstock/app/domain/model/Shelf;", "_uiState", "Lcom/monstock/app/ui/screens/shelf/ShelfUiState;", "pantryId", "", "shelves", "Lkotlinx/coroutines/flow/StateFlow;", "getShelves", "()Lkotlinx/coroutines/flow/StateFlow;", "uiState", "getUiState", "cancelDelete", "", "confirmDelete", "dismissDialog", "init", "openAddDialog", "openEditDialog", "shelf", "requestDelete", "saveShelf", "name", "", "capacity", "", "(Ljava/lang/String;Ljava/lang/Integer;)V", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class ShelfViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.monstock.app.data.repository.ShelfRepository shelfRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.monstock.app.data.repository.PantryRepository pantryRepository = null;
    private long pantryId = -1L;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.monstock.app.ui.screens.shelf.ShelfUiState> _uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.monstock.app.ui.screens.shelf.ShelfUiState> uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.util.List<com.monstock.app.domain.model.Shelf>> _shelves = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.monstock.app.domain.model.Shelf>> shelves = null;
    
    @javax.inject.Inject()
    public ShelfViewModel(@org.jetbrains.annotations.NotNull()
    com.monstock.app.data.repository.ShelfRepository shelfRepository, @org.jetbrains.annotations.NotNull()
    com.monstock.app.data.repository.PantryRepository pantryRepository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.monstock.app.ui.screens.shelf.ShelfUiState> getUiState() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.monstock.app.domain.model.Shelf>> getShelves() {
        return null;
    }
    
    public final void init(long pantryId) {
    }
    
    public final void openAddDialog() {
    }
    
    public final void openEditDialog(@org.jetbrains.annotations.NotNull()
    com.monstock.app.domain.model.Shelf shelf) {
    }
    
    public final void dismissDialog() {
    }
    
    public final void saveShelf(@org.jetbrains.annotations.NotNull()
    java.lang.String name, @org.jetbrains.annotations.Nullable()
    java.lang.Integer capacity) {
    }
    
    public final void requestDelete(@org.jetbrains.annotations.NotNull()
    com.monstock.app.domain.model.Shelf shelf) {
    }
    
    public final void confirmDelete() {
    }
    
    public final void cancelDelete() {
    }
}
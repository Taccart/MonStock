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

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0011\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0087\b\u0018\u00002\u00020\u0001B3\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u0007\u00a2\u0006\u0002\u0010\tJ\u000b\u0010\u0011\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\t\u0010\u0012\u001a\u00020\u0005H\u00c6\u0003J\u000b\u0010\u0013\u001a\u0004\u0018\u00010\u0007H\u00c6\u0003J\u000b\u0010\u0014\u001a\u0004\u0018\u00010\u0007H\u00c6\u0003J7\u0010\u0015\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00072\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u0007H\u00c6\u0001J\u0013\u0010\u0016\u001a\u00020\u00052\b\u0010\u0017\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u0018\u001a\u00020\u0019H\u00d6\u0001J\t\u0010\u001a\u001a\u00020\u001bH\u00d6\u0001R\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0013\u0010\b\u001a\u0004\u0018\u00010\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000bR\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010\u00a8\u0006\u001c"}, d2 = {"Lcom/monstock/app/ui/screens/shelf/ShelfUiState;", "", "pantry", "Lcom/monstock/app/domain/model/Pantry;", "showAddEditDialog", "", "editingShelf", "Lcom/monstock/app/domain/model/Shelf;", "pendingDeleteShelf", "(Lcom/monstock/app/domain/model/Pantry;ZLcom/monstock/app/domain/model/Shelf;Lcom/monstock/app/domain/model/Shelf;)V", "getEditingShelf", "()Lcom/monstock/app/domain/model/Shelf;", "getPantry", "()Lcom/monstock/app/domain/model/Pantry;", "getPendingDeleteShelf", "getShowAddEditDialog", "()Z", "component1", "component2", "component3", "component4", "copy", "equals", "other", "hashCode", "", "toString", "", "app_debug"})
public final class ShelfUiState {
    @org.jetbrains.annotations.Nullable()
    private final com.monstock.app.domain.model.Pantry pantry = null;
    private final boolean showAddEditDialog = false;
    @org.jetbrains.annotations.Nullable()
    private final com.monstock.app.domain.model.Shelf editingShelf = null;
    @org.jetbrains.annotations.Nullable()
    private final com.monstock.app.domain.model.Shelf pendingDeleteShelf = null;
    
    @org.jetbrains.annotations.Nullable()
    public final com.monstock.app.domain.model.Pantry component1() {
        return null;
    }
    
    public final boolean component2() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.monstock.app.domain.model.Shelf component3() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.monstock.app.domain.model.Shelf component4() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.monstock.app.ui.screens.shelf.ShelfUiState copy(@org.jetbrains.annotations.Nullable()
    com.monstock.app.domain.model.Pantry pantry, boolean showAddEditDialog, @org.jetbrains.annotations.Nullable()
    com.monstock.app.domain.model.Shelf editingShelf, @org.jetbrains.annotations.Nullable()
    com.monstock.app.domain.model.Shelf pendingDeleteShelf) {
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
    
    public ShelfUiState(@org.jetbrains.annotations.Nullable()
    com.monstock.app.domain.model.Pantry pantry, boolean showAddEditDialog, @org.jetbrains.annotations.Nullable()
    com.monstock.app.domain.model.Shelf editingShelf, @org.jetbrains.annotations.Nullable()
    com.monstock.app.domain.model.Shelf pendingDeleteShelf) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.monstock.app.domain.model.Pantry getPantry() {
        return null;
    }
    
    public final boolean getShowAddEditDialog() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.monstock.app.domain.model.Shelf getEditingShelf() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.monstock.app.domain.model.Shelf getPendingDeleteShelf() {
        return null;
    }
    
    public ShelfUiState() {
        super();
    }
}
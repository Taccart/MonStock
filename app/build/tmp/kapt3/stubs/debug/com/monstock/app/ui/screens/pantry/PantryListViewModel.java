package com.monstock.app.ui.screens.pantry;

import androidx.lifecycle.ViewModel;
import com.monstock.app.data.repository.PantryRepository;
import com.monstock.app.domain.model.Pantry;
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StateFlow;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\b\n\u0002\u0010\u000e\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0006\u0010\u0010\u001a\u00020\u0011J\u0006\u0010\u0012\u001a\u00020\u0011J\u0006\u0010\u0013\u001a\u00020\u0011J\u0006\u0010\u0014\u001a\u00020\u0011J\u000e\u0010\u0015\u001a\u00020\u00112\u0006\u0010\u0016\u001a\u00020\u000bJ\u000e\u0010\u0017\u001a\u00020\u00112\u0006\u0010\u0016\u001a\u00020\u000bJ\u000e\u0010\u0018\u001a\u00020\u00112\u0006\u0010\u0019\u001a\u00020\u001aR\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\n0\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00070\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\r\u00a8\u0006\u001b"}, d2 = {"Lcom/monstock/app/ui/screens/pantry/PantryListViewModel;", "Landroidx/lifecycle/ViewModel;", "pantryRepository", "Lcom/monstock/app/data/repository/PantryRepository;", "(Lcom/monstock/app/data/repository/PantryRepository;)V", "_uiState", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/monstock/app/ui/screens/pantry/PantryListUiState;", "pantries", "Lkotlinx/coroutines/flow/StateFlow;", "", "Lcom/monstock/app/domain/model/Pantry;", "getPantries", "()Lkotlinx/coroutines/flow/StateFlow;", "uiState", "getUiState", "cancelDelete", "", "confirmDelete", "dismissDialog", "openAddDialog", "openEditDialog", "pantry", "requestDelete", "savePantry", "name", "", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class PantryListViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.monstock.app.data.repository.PantryRepository pantryRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.monstock.app.domain.model.Pantry>> pantries = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.monstock.app.ui.screens.pantry.PantryListUiState> _uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.monstock.app.ui.screens.pantry.PantryListUiState> uiState = null;
    
    @javax.inject.Inject()
    public PantryListViewModel(@org.jetbrains.annotations.NotNull()
    com.monstock.app.data.repository.PantryRepository pantryRepository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.monstock.app.domain.model.Pantry>> getPantries() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.monstock.app.ui.screens.pantry.PantryListUiState> getUiState() {
        return null;
    }
    
    public final void openAddDialog() {
    }
    
    public final void openEditDialog(@org.jetbrains.annotations.NotNull()
    com.monstock.app.domain.model.Pantry pantry) {
    }
    
    public final void dismissDialog() {
    }
    
    public final void savePantry(@org.jetbrains.annotations.NotNull()
    java.lang.String name) {
    }
    
    public final void requestDelete(@org.jetbrains.annotations.NotNull()
    com.monstock.app.domain.model.Pantry pantry) {
    }
    
    public final void confirmDelete() {
    }
    
    public final void cancelDelete() {
    }
}
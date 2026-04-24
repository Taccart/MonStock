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

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b4\n\u0002\u0010\b\n\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u0001B\u00cd\u0001\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0006\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0003\u0012\b\b\u0002\u0010\b\u001a\u00020\t\u0012\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u000b\u0012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u000b\u0012\b\b\u0002\u0010\r\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u000e\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u000f\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u0011\u0012\n\b\u0002\u0010\u0012\u001a\u0004\u0018\u00010\u0011\u0012\u000e\b\u0002\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00150\u0014\u0012\u000e\b\u0002\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00170\u0014\u0012\b\b\u0002\u0010\u0018\u001a\u00020\u0019\u0012\b\b\u0002\u0010\u001a\u001a\u00020\u0019\u0012\b\b\u0002\u0010\u001b\u001a\u00020\u0019\u0012\b\b\u0002\u0010\u001c\u001a\u00020\u0019\u00a2\u0006\u0002\u0010\u001dJ\t\u00107\u001a\u00020\u0003H\u00c6\u0003J\t\u00108\u001a\u00020\u0003H\u00c6\u0003J\u0010\u00109\u001a\u0004\u0018\u00010\u0011H\u00c6\u0003\u00a2\u0006\u0002\u00100J\u0010\u0010:\u001a\u0004\u0018\u00010\u0011H\u00c6\u0003\u00a2\u0006\u0002\u00100J\u000f\u0010;\u001a\b\u0012\u0004\u0012\u00020\u00150\u0014H\u00c6\u0003J\u000f\u0010<\u001a\b\u0012\u0004\u0012\u00020\u00170\u0014H\u00c6\u0003J\t\u0010=\u001a\u00020\u0019H\u00c6\u0003J\t\u0010>\u001a\u00020\u0019H\u00c6\u0003J\t\u0010?\u001a\u00020\u0019H\u00c6\u0003J\t\u0010@\u001a\u00020\u0019H\u00c6\u0003J\t\u0010A\u001a\u00020\u0003H\u00c6\u0003J\t\u0010B\u001a\u00020\u0006H\u00c6\u0003J\t\u0010C\u001a\u00020\u0003H\u00c6\u0003J\t\u0010D\u001a\u00020\tH\u00c6\u0003J\u000b\u0010E\u001a\u0004\u0018\u00010\u000bH\u00c6\u0003J\u000b\u0010F\u001a\u0004\u0018\u00010\u000bH\u00c6\u0003J\t\u0010G\u001a\u00020\u0003H\u00c6\u0003J\t\u0010H\u001a\u00020\u0003H\u00c6\u0003J\u00d6\u0001\u0010I\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\u00032\b\b\u0002\u0010\b\u001a\u00020\t2\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u000b2\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u000b2\b\b\u0002\u0010\r\u001a\u00020\u00032\b\b\u0002\u0010\u000e\u001a\u00020\u00032\b\b\u0002\u0010\u000f\u001a\u00020\u00032\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u00112\n\b\u0002\u0010\u0012\u001a\u0004\u0018\u00010\u00112\u000e\b\u0002\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00150\u00142\u000e\b\u0002\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00170\u00142\b\b\u0002\u0010\u0018\u001a\u00020\u00192\b\b\u0002\u0010\u001a\u001a\u00020\u00192\b\b\u0002\u0010\u001b\u001a\u00020\u00192\b\b\u0002\u0010\u001c\u001a\u00020\u0019H\u00c6\u0001\u00a2\u0006\u0002\u0010JJ\u0013\u0010K\u001a\u00020\u00192\b\u0010L\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010M\u001a\u00020NH\u00d6\u0001J\t\u0010O\u001a\u00020\u0003H\u00d6\u0001R\u0011\u0010\u000e\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001fR\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b \u0010\u001fR\u0011\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b!\u0010\"R\u0013\u0010\f\u001a\u0004\u0018\u00010\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b#\u0010$R\u0011\u0010\u0018\u001a\u00020\u0019\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010%R\u0011\u0010\r\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b&\u0010\u001fR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\'\u0010\u001fR\u0011\u0010\u001b\u001a\u00020\u0019\u00a2\u0006\b\n\u0000\u001a\u0004\b(\u0010%R\u0011\u0010\u000f\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b)\u0010\u001fR\u0017\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00150\u0014\u00a2\u0006\b\n\u0000\u001a\u0004\b*\u0010+R\u0013\u0010\n\u001a\u0004\u0018\u00010\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b,\u0010$R\u0011\u0010\u0007\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b-\u0010\u001fR\u0011\u0010\u001a\u001a\u00020\u0019\u00a2\u0006\b\n\u0000\u001a\u0004\b.\u0010%R\u0015\u0010\u0012\u001a\u0004\u0018\u00010\u0011\u00a2\u0006\n\n\u0002\u00101\u001a\u0004\b/\u00100R\u0015\u0010\u0010\u001a\u0004\u0018\u00010\u0011\u00a2\u0006\n\n\u0002\u00101\u001a\u0004\b2\u00100R\u0011\u0010\u001c\u001a\u00020\u0019\u00a2\u0006\b\n\u0000\u001a\u0004\b3\u0010%R\u0017\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00170\u0014\u00a2\u0006\b\n\u0000\u001a\u0004\b4\u0010+R\u0011\u0010\b\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b5\u00106\u00a8\u0006P"}, d2 = {"Lcom/monstock/app/ui/screens/addedit/AddEditItemUiState;", "", "name", "", "brand", "category", "Lcom/monstock/app/data/model/ItemCategory;", "quantity", "unit", "Lcom/monstock/app/data/model/ItemUnit;", "purchaseDate", "Ljava/time/LocalDate;", "expiryDate", "minimumStockThreshold", "barcode", "notes", "selectedShelfId", "", "selectedPantryId", "pantries", "", "Lcom/monstock/app/domain/model/Pantry;", "shelves", "Lcom/monstock/app/domain/model/Shelf;", "isEditMode", "", "saved", "nameError", "shelfError", "(Ljava/lang/String;Ljava/lang/String;Lcom/monstock/app/data/model/ItemCategory;Ljava/lang/String;Lcom/monstock/app/data/model/ItemUnit;Ljava/time/LocalDate;Ljava/time/LocalDate;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Long;Ljava/lang/Long;Ljava/util/List;Ljava/util/List;ZZZZ)V", "getBarcode", "()Ljava/lang/String;", "getBrand", "getCategory", "()Lcom/monstock/app/data/model/ItemCategory;", "getExpiryDate", "()Ljava/time/LocalDate;", "()Z", "getMinimumStockThreshold", "getName", "getNameError", "getNotes", "getPantries", "()Ljava/util/List;", "getPurchaseDate", "getQuantity", "getSaved", "getSelectedPantryId", "()Ljava/lang/Long;", "Ljava/lang/Long;", "getSelectedShelfId", "getShelfError", "getShelves", "getUnit", "()Lcom/monstock/app/data/model/ItemUnit;", "component1", "component10", "component11", "component12", "component13", "component14", "component15", "component16", "component17", "component18", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "(Ljava/lang/String;Ljava/lang/String;Lcom/monstock/app/data/model/ItemCategory;Ljava/lang/String;Lcom/monstock/app/data/model/ItemUnit;Ljava/time/LocalDate;Ljava/time/LocalDate;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Long;Ljava/lang/Long;Ljava/util/List;Ljava/util/List;ZZZZ)Lcom/monstock/app/ui/screens/addedit/AddEditItemUiState;", "equals", "other", "hashCode", "", "toString", "app_debug"})
public final class AddEditItemUiState {
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String name = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String brand = null;
    @org.jetbrains.annotations.NotNull()
    private final com.monstock.app.data.model.ItemCategory category = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String quantity = null;
    @org.jetbrains.annotations.NotNull()
    private final com.monstock.app.data.model.ItemUnit unit = null;
    @org.jetbrains.annotations.Nullable()
    private final java.time.LocalDate purchaseDate = null;
    @org.jetbrains.annotations.Nullable()
    private final java.time.LocalDate expiryDate = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String minimumStockThreshold = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String barcode = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String notes = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Long selectedShelfId = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Long selectedPantryId = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.monstock.app.domain.model.Pantry> pantries = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.monstock.app.domain.model.Shelf> shelves = null;
    private final boolean isEditMode = false;
    private final boolean saved = false;
    private final boolean nameError = false;
    private final boolean shelfError = false;
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component1() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component10() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Long component11() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Long component12() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.monstock.app.domain.model.Pantry> component13() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.monstock.app.domain.model.Shelf> component14() {
        return null;
    }
    
    public final boolean component15() {
        return false;
    }
    
    public final boolean component16() {
        return false;
    }
    
    public final boolean component17() {
        return false;
    }
    
    public final boolean component18() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component2() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.monstock.app.data.model.ItemCategory component3() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component4() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.monstock.app.data.model.ItemUnit component5() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.time.LocalDate component6() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.time.LocalDate component7() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component8() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component9() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.monstock.app.ui.screens.addedit.AddEditItemUiState copy(@org.jetbrains.annotations.NotNull()
    java.lang.String name, @org.jetbrains.annotations.NotNull()
    java.lang.String brand, @org.jetbrains.annotations.NotNull()
    com.monstock.app.data.model.ItemCategory category, @org.jetbrains.annotations.NotNull()
    java.lang.String quantity, @org.jetbrains.annotations.NotNull()
    com.monstock.app.data.model.ItemUnit unit, @org.jetbrains.annotations.Nullable()
    java.time.LocalDate purchaseDate, @org.jetbrains.annotations.Nullable()
    java.time.LocalDate expiryDate, @org.jetbrains.annotations.NotNull()
    java.lang.String minimumStockThreshold, @org.jetbrains.annotations.NotNull()
    java.lang.String barcode, @org.jetbrains.annotations.NotNull()
    java.lang.String notes, @org.jetbrains.annotations.Nullable()
    java.lang.Long selectedShelfId, @org.jetbrains.annotations.Nullable()
    java.lang.Long selectedPantryId, @org.jetbrains.annotations.NotNull()
    java.util.List<com.monstock.app.domain.model.Pantry> pantries, @org.jetbrains.annotations.NotNull()
    java.util.List<com.monstock.app.domain.model.Shelf> shelves, boolean isEditMode, boolean saved, boolean nameError, boolean shelfError) {
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
    
    public AddEditItemUiState(@org.jetbrains.annotations.NotNull()
    java.lang.String name, @org.jetbrains.annotations.NotNull()
    java.lang.String brand, @org.jetbrains.annotations.NotNull()
    com.monstock.app.data.model.ItemCategory category, @org.jetbrains.annotations.NotNull()
    java.lang.String quantity, @org.jetbrains.annotations.NotNull()
    com.monstock.app.data.model.ItemUnit unit, @org.jetbrains.annotations.Nullable()
    java.time.LocalDate purchaseDate, @org.jetbrains.annotations.Nullable()
    java.time.LocalDate expiryDate, @org.jetbrains.annotations.NotNull()
    java.lang.String minimumStockThreshold, @org.jetbrains.annotations.NotNull()
    java.lang.String barcode, @org.jetbrains.annotations.NotNull()
    java.lang.String notes, @org.jetbrains.annotations.Nullable()
    java.lang.Long selectedShelfId, @org.jetbrains.annotations.Nullable()
    java.lang.Long selectedPantryId, @org.jetbrains.annotations.NotNull()
    java.util.List<com.monstock.app.domain.model.Pantry> pantries, @org.jetbrains.annotations.NotNull()
    java.util.List<com.monstock.app.domain.model.Shelf> shelves, boolean isEditMode, boolean saved, boolean nameError, boolean shelfError) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getName() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getBrand() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.monstock.app.data.model.ItemCategory getCategory() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getQuantity() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.monstock.app.data.model.ItemUnit getUnit() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.time.LocalDate getPurchaseDate() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.time.LocalDate getExpiryDate() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getMinimumStockThreshold() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getBarcode() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getNotes() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Long getSelectedShelfId() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Long getSelectedPantryId() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.monstock.app.domain.model.Pantry> getPantries() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.monstock.app.domain.model.Shelf> getShelves() {
        return null;
    }
    
    public final boolean isEditMode() {
        return false;
    }
    
    public final boolean getSaved() {
        return false;
    }
    
    public final boolean getNameError() {
        return false;
    }
    
    public final boolean getShelfError() {
        return false;
    }
    
    public AddEditItemUiState() {
        super();
    }
}
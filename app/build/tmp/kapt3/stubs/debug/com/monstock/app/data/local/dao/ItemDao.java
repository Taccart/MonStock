package com.monstock.app.data.local.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import com.monstock.app.data.local.entity.ItemEntity;
import com.monstock.app.data.model.ItemCategory;
import kotlinx.coroutines.flow.Flow;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\b\n\u0002\b\u0005\bg\u0018\u00002\u00020\u0001J\u0016\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u0016\u0010\u0007\u001a\u00020\u00032\u0006\u0010\b\u001a\u00020\tH\u00a7@\u00a2\u0006\u0002\u0010\nJ\u0014\u0010\u000b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\r0\fH\'J\u001c\u0010\u000e\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\r0\f2\u0006\u0010\u000f\u001a\u00020\tH\'J\u0018\u0010\u0010\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0011\u001a\u00020\u0012H\u00a7@\u00a2\u0006\u0002\u0010\u0013J\u0018\u0010\u0014\u001a\u0004\u0018\u00010\u00052\u0006\u0010\b\u001a\u00020\tH\u00a7@\u00a2\u0006\u0002\u0010\nJ\u001c\u0010\u0015\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\r0\f2\u0006\u0010\u0016\u001a\u00020\u0017H\'J\u001c\u0010\u0018\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\r0\f2\u0006\u0010\u0019\u001a\u00020\tH\'J$\u0010\u001a\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\r0\f2\u0006\u0010\u000f\u001a\u00020\t2\u0006\u0010\u001b\u001a\u00020\tH\'J\u0014\u0010\u001c\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\r0\fH\'J\u000e\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u001e0\fH\'J\u0016\u0010\u001f\u001a\u00020\t2\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u001c\u0010 \u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\r0\f2\u0006\u0010!\u001a\u00020\u0012H\'J\u0016\u0010\"\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006\u00a8\u0006#"}, d2 = {"Lcom/monstock/app/data/local/dao/ItemDao;", "", "deleteItem", "", "item", "Lcom/monstock/app/data/local/entity/ItemEntity;", "(Lcom/monstock/app/data/local/entity/ItemEntity;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteItemById", "id", "", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAllItems", "Lkotlinx/coroutines/flow/Flow;", "", "getExpiredItems", "todayEpochDay", "getItemByBarcode", "barcode", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getItemById", "getItemsByCategory", "category", "Lcom/monstock/app/data/model/ItemCategory;", "getItemsByShelf", "shelfId", "getItemsExpiringSoon", "epochDayLimit", "getLowStockItems", "getTotalItemCount", "", "insertItem", "searchItems", "query", "updateItem", "app_debug"})
@androidx.room.Dao()
public abstract interface ItemDao {
    
    /**
     * All items on a specific shelf, sorted soonest-to-expire first.
     */
    @androidx.room.Query(value = "SELECT * FROM items WHERE shelfId = :shelfId ORDER BY expiryDate ASC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.monstock.app.data.local.entity.ItemEntity>> getItemsByShelf(long shelfId);
    
    /**
     * All items across all shelves, sorted soonest-to-expire first.
     */
    @androidx.room.Query(value = "SELECT * FROM items ORDER BY expiryDate ASC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.monstock.app.data.local.entity.ItemEntity>> getAllItems();
    
    /**
     * Items expiring within the next [days] days (from today).
     */
    @androidx.room.Query(value = "\n        SELECT * FROM items\n        WHERE expiryDate IS NOT NULL\n          AND expiryDate <= :epochDayLimit\n          AND expiryDate >= :todayEpochDay\n        ORDER BY expiryDate ASC\n    ")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.monstock.app.data.local.entity.ItemEntity>> getItemsExpiringSoon(long todayEpochDay, long epochDayLimit);
    
    /**
     * Items that are already expired.
     */
    @androidx.room.Query(value = "\n        SELECT * FROM items\n        WHERE expiryDate IS NOT NULL\n          AND expiryDate < :todayEpochDay\n        ORDER BY expiryDate ASC\n    ")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.monstock.app.data.local.entity.ItemEntity>> getExpiredItems(long todayEpochDay);
    
    /**
     * Items whose quantity is at or below their minimum stock threshold.
     */
    @androidx.room.Query(value = "\n        SELECT * FROM items\n        WHERE minimumStockThreshold IS NOT NULL\n          AND quantity <= minimumStockThreshold\n        ORDER BY name ASC\n    ")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.monstock.app.data.local.entity.ItemEntity>> getLowStockItems();
    
    /**
     * Full-text search on name and brand.
     */
    @androidx.room.Query(value = "\n        SELECT * FROM items\n        WHERE name LIKE \'%\' || :query || \'%\'\n           OR brand LIKE \'%\' || :query || \'%\'\n        ORDER BY expiryDate ASC\n    ")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.monstock.app.data.local.entity.ItemEntity>> searchItems(@org.jetbrains.annotations.NotNull()
    java.lang.String query);
    
    /**
     * Filter by category.
     */
    @androidx.room.Query(value = "SELECT * FROM items WHERE category = :category ORDER BY expiryDate ASC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.monstock.app.data.local.entity.ItemEntity>> getItemsByCategory(@org.jetbrains.annotations.NotNull()
    com.monstock.app.data.model.ItemCategory category);
    
    @androidx.room.Query(value = "SELECT * FROM items WHERE barcode = :barcode LIMIT 1")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getItemByBarcode(@org.jetbrains.annotations.NotNull()
    java.lang.String barcode, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.monstock.app.data.local.entity.ItemEntity> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM items WHERE id = :id")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getItemById(long id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.monstock.app.data.local.entity.ItemEntity> $completion);
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertItem(@org.jetbrains.annotations.NotNull()
    com.monstock.app.data.local.entity.ItemEntity item, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion);
    
    @androidx.room.Update()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updateItem(@org.jetbrains.annotations.NotNull()
    com.monstock.app.data.local.entity.ItemEntity item, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Delete()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteItem(@org.jetbrains.annotations.NotNull()
    com.monstock.app.data.local.entity.ItemEntity item, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "DELETE FROM items WHERE id = :id")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteItemById(long id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    /**
     * Count of all items (for dashboard).
     */
    @androidx.room.Query(value = "SELECT COUNT(*) FROM items")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.lang.Integer> getTotalItemCount();
}
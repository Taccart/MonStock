package com.monstock.app.data.repository;

import com.monstock.app.data.model.ItemCategory;
import com.monstock.app.domain.model.Item;
import kotlinx.coroutines.flow.Flow;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\b\n\u0002\b\u0007\bf\u0018\u00002\u00020\u0001J\u0016\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a6@\u00a2\u0006\u0002\u0010\u0006J\u0014\u0010\u0007\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t0\bH&J\u0014\u0010\u000b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t0\bH&J\u0018\u0010\f\u001a\u0004\u0018\u00010\n2\u0006\u0010\r\u001a\u00020\u000eH\u00a6@\u00a2\u0006\u0002\u0010\u000fJ\u0018\u0010\u0010\u001a\u0004\u0018\u00010\n2\u0006\u0010\u0004\u001a\u00020\u0005H\u00a6@\u00a2\u0006\u0002\u0010\u0006J\u001c\u0010\u0011\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t0\b2\u0006\u0010\u0012\u001a\u00020\u0013H&J\u001c\u0010\u0014\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t0\b2\u0006\u0010\u0015\u001a\u00020\u0005H&J\u001e\u0010\u0016\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t0\b2\b\b\u0002\u0010\u0017\u001a\u00020\u0005H&J\u0014\u0010\u0018\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t0\bH&J\u000e\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u001a0\bH&J\u0016\u0010\u001b\u001a\u00020\u00052\u0006\u0010\u001c\u001a\u00020\nH\u00a6@\u00a2\u0006\u0002\u0010\u001dJ\u001c\u0010\u001e\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t0\b2\u0006\u0010\u001f\u001a\u00020\u000eH&J\u0016\u0010 \u001a\u00020\u00032\u0006\u0010\u001c\u001a\u00020\nH\u00a6@\u00a2\u0006\u0002\u0010\u001d\u00a8\u0006!"}, d2 = {"Lcom/monstock/app/data/repository/ItemRepository;", "", "deleteItem", "", "id", "", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAllItems", "Lkotlinx/coroutines/flow/Flow;", "", "Lcom/monstock/app/domain/model/Item;", "getExpiredItems", "getItemByBarcode", "barcode", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getItemById", "getItemsByCategory", "category", "Lcom/monstock/app/data/model/ItemCategory;", "getItemsByShelf", "shelfId", "getItemsExpiringSoon", "withinDays", "getLowStockItems", "getTotalItemCount", "", "insertItem", "item", "(Lcom/monstock/app/domain/model/Item;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "searchItems", "query", "updateItem", "app_debug"})
public abstract interface ItemRepository {
    
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.monstock.app.domain.model.Item>> getAllItems();
    
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.monstock.app.domain.model.Item>> getItemsByShelf(long shelfId);
    
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.monstock.app.domain.model.Item>> getItemsExpiringSoon(long withinDays);
    
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.monstock.app.domain.model.Item>> getExpiredItems();
    
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.monstock.app.domain.model.Item>> getLowStockItems();
    
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.monstock.app.domain.model.Item>> searchItems(@org.jetbrains.annotations.NotNull()
    java.lang.String query);
    
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.monstock.app.domain.model.Item>> getItemsByCategory(@org.jetbrains.annotations.NotNull()
    com.monstock.app.data.model.ItemCategory category);
    
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.lang.Integer> getTotalItemCount();
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getItemById(long id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.monstock.app.domain.model.Item> $completion);
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getItemByBarcode(@org.jetbrains.annotations.NotNull()
    java.lang.String barcode, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.monstock.app.domain.model.Item> $completion);
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertItem(@org.jetbrains.annotations.NotNull()
    com.monstock.app.domain.model.Item item, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion);
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updateItem(@org.jetbrains.annotations.NotNull()
    com.monstock.app.domain.model.Item item, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteItem(long id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 3, xi = 48)
    public static final class DefaultImpls {
    }
}
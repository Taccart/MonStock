package com.monstock.app.data.repository.impl;

import com.monstock.app.data.local.dao.ItemDao;
import com.monstock.app.data.local.entity.ItemEntity;
import com.monstock.app.data.model.ItemCategory;
import com.monstock.app.data.repository.ItemRepository;
import com.monstock.app.domain.model.Item;
import kotlinx.coroutines.flow.Flow;
import java.time.LocalDate;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0016\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0096@\u00a2\u0006\u0002\u0010\tJ\u0014\u0010\n\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\r0\f0\u000bH\u0016J\u0014\u0010\u000e\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\r0\f0\u000bH\u0016J\u0018\u0010\u000f\u001a\u0004\u0018\u00010\r2\u0006\u0010\u0010\u001a\u00020\u0011H\u0096@\u00a2\u0006\u0002\u0010\u0012J\u0018\u0010\u0013\u001a\u0004\u0018\u00010\r2\u0006\u0010\u0007\u001a\u00020\bH\u0096@\u00a2\u0006\u0002\u0010\tJ\u001c\u0010\u0014\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\r0\f0\u000b2\u0006\u0010\u0015\u001a\u00020\u0016H\u0016J\u001c\u0010\u0017\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\r0\f0\u000b2\u0006\u0010\u0018\u001a\u00020\bH\u0016J\u001c\u0010\u0019\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\r0\f0\u000b2\u0006\u0010\u001a\u001a\u00020\bH\u0016J\u0014\u0010\u001b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\r0\f0\u000bH\u0016J\u000e\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u001d0\u000bH\u0016J\u0016\u0010\u001e\u001a\u00020\b2\u0006\u0010\u001f\u001a\u00020\rH\u0096@\u00a2\u0006\u0002\u0010 J\u001c\u0010!\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\r0\f0\u000b2\u0006\u0010\"\u001a\u00020\u0011H\u0016J\u0016\u0010#\u001a\u00020\u00062\u0006\u0010\u001f\u001a\u00020\rH\u0096@\u00a2\u0006\u0002\u0010 J\f\u0010$\u001a\u00020\r*\u00020%H\u0002J\f\u0010&\u001a\u00020%*\u00020\rH\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\'"}, d2 = {"Lcom/monstock/app/data/repository/impl/ItemRepositoryImpl;", "Lcom/monstock/app/data/repository/ItemRepository;", "itemDao", "Lcom/monstock/app/data/local/dao/ItemDao;", "(Lcom/monstock/app/data/local/dao/ItemDao;)V", "deleteItem", "", "id", "", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAllItems", "Lkotlinx/coroutines/flow/Flow;", "", "Lcom/monstock/app/domain/model/Item;", "getExpiredItems", "getItemByBarcode", "barcode", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getItemById", "getItemsByCategory", "category", "Lcom/monstock/app/data/model/ItemCategory;", "getItemsByShelf", "shelfId", "getItemsExpiringSoon", "withinDays", "getLowStockItems", "getTotalItemCount", "", "insertItem", "item", "(Lcom/monstock/app/domain/model/Item;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "searchItems", "query", "updateItem", "toDomain", "Lcom/monstock/app/data/local/entity/ItemEntity;", "toEntity", "app_debug"})
public final class ItemRepositoryImpl implements com.monstock.app.data.repository.ItemRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.monstock.app.data.local.dao.ItemDao itemDao = null;
    
    @javax.inject.Inject()
    public ItemRepositoryImpl(@org.jetbrains.annotations.NotNull()
    com.monstock.app.data.local.dao.ItemDao itemDao) {
        super();
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<java.util.List<com.monstock.app.domain.model.Item>> getAllItems() {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<java.util.List<com.monstock.app.domain.model.Item>> getItemsByShelf(long shelfId) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<java.util.List<com.monstock.app.domain.model.Item>> getItemsExpiringSoon(long withinDays) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<java.util.List<com.monstock.app.domain.model.Item>> getExpiredItems() {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<java.util.List<com.monstock.app.domain.model.Item>> getLowStockItems() {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<java.util.List<com.monstock.app.domain.model.Item>> searchItems(@org.jetbrains.annotations.NotNull()
    java.lang.String query) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<java.util.List<com.monstock.app.domain.model.Item>> getItemsByCategory(@org.jetbrains.annotations.NotNull()
    com.monstock.app.data.model.ItemCategory category) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<java.lang.Integer> getTotalItemCount() {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object getItemById(long id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.monstock.app.domain.model.Item> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object getItemByBarcode(@org.jetbrains.annotations.NotNull()
    java.lang.String barcode, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.monstock.app.domain.model.Item> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object insertItem(@org.jetbrains.annotations.NotNull()
    com.monstock.app.domain.model.Item item, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object updateItem(@org.jetbrains.annotations.NotNull()
    com.monstock.app.domain.model.Item item, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object deleteItem(long id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    private final com.monstock.app.domain.model.Item toDomain(com.monstock.app.data.local.entity.ItemEntity $this$toDomain) {
        return null;
    }
    
    private final com.monstock.app.data.local.entity.ItemEntity toEntity(com.monstock.app.domain.model.Item $this$toEntity) {
        return null;
    }
}
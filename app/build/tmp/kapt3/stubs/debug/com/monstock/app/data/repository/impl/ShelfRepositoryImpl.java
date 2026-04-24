package com.monstock.app.data.repository.impl;

import com.monstock.app.data.local.dao.ShelfDao;
import com.monstock.app.data.local.entity.ShelfEntity;
import com.monstock.app.data.repository.ShelfRepository;
import com.monstock.app.domain.model.Shelf;
import kotlinx.coroutines.flow.Flow;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0016\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0096@\u00a2\u0006\u0002\u0010\tJ\u0018\u0010\n\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\u0007\u001a\u00020\bH\u0096@\u00a2\u0006\u0002\u0010\tJ\u001c\u0010\f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\u000e0\r2\u0006\u0010\u000f\u001a\u00020\bH\u0016J\u0016\u0010\u0010\u001a\u00020\b2\u0006\u0010\u0011\u001a\u00020\u000bH\u0096@\u00a2\u0006\u0002\u0010\u0012J\u0016\u0010\u0013\u001a\u00020\u00062\u0006\u0010\u0011\u001a\u00020\u000bH\u0096@\u00a2\u0006\u0002\u0010\u0012J\f\u0010\u0014\u001a\u00020\u000b*\u00020\u0015H\u0002J\f\u0010\u0016\u001a\u00020\u0015*\u00020\u000bH\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0017"}, d2 = {"Lcom/monstock/app/data/repository/impl/ShelfRepositoryImpl;", "Lcom/monstock/app/data/repository/ShelfRepository;", "shelfDao", "Lcom/monstock/app/data/local/dao/ShelfDao;", "(Lcom/monstock/app/data/local/dao/ShelfDao;)V", "deleteShelf", "", "id", "", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getShelfById", "Lcom/monstock/app/domain/model/Shelf;", "getShelvesByPantry", "Lkotlinx/coroutines/flow/Flow;", "", "pantryId", "insertShelf", "shelf", "(Lcom/monstock/app/domain/model/Shelf;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateShelf", "toDomain", "Lcom/monstock/app/data/local/entity/ShelfEntity;", "toEntity", "app_debug"})
public final class ShelfRepositoryImpl implements com.monstock.app.data.repository.ShelfRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.monstock.app.data.local.dao.ShelfDao shelfDao = null;
    
    @javax.inject.Inject()
    public ShelfRepositoryImpl(@org.jetbrains.annotations.NotNull()
    com.monstock.app.data.local.dao.ShelfDao shelfDao) {
        super();
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<java.util.List<com.monstock.app.domain.model.Shelf>> getShelvesByPantry(long pantryId) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object getShelfById(long id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.monstock.app.domain.model.Shelf> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object insertShelf(@org.jetbrains.annotations.NotNull()
    com.monstock.app.domain.model.Shelf shelf, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object updateShelf(@org.jetbrains.annotations.NotNull()
    com.monstock.app.domain.model.Shelf shelf, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object deleteShelf(long id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    private final com.monstock.app.domain.model.Shelf toDomain(com.monstock.app.data.local.entity.ShelfEntity $this$toDomain) {
        return null;
    }
    
    private final com.monstock.app.data.local.entity.ShelfEntity toEntity(com.monstock.app.domain.model.Shelf $this$toEntity) {
        return null;
    }
}
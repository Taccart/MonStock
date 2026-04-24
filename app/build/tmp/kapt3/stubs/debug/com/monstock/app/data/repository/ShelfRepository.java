package com.monstock.app.data.repository;

import com.monstock.app.domain.model.Shelf;
import kotlinx.coroutines.flow.Flow;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\u0006\bf\u0018\u00002\u00020\u0001J\u0016\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a6@\u00a2\u0006\u0002\u0010\u0006J\u0018\u0010\u0007\u001a\u0004\u0018\u00010\b2\u0006\u0010\u0004\u001a\u00020\u0005H\u00a6@\u00a2\u0006\u0002\u0010\u0006J\u001c\u0010\t\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u000b0\n2\u0006\u0010\f\u001a\u00020\u0005H&J\u0016\u0010\r\u001a\u00020\u00052\u0006\u0010\u000e\u001a\u00020\bH\u00a6@\u00a2\u0006\u0002\u0010\u000fJ\u0016\u0010\u0010\u001a\u00020\u00032\u0006\u0010\u000e\u001a\u00020\bH\u00a6@\u00a2\u0006\u0002\u0010\u000f\u00a8\u0006\u0011"}, d2 = {"Lcom/monstock/app/data/repository/ShelfRepository;", "", "deleteShelf", "", "id", "", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getShelfById", "Lcom/monstock/app/domain/model/Shelf;", "getShelvesByPantry", "Lkotlinx/coroutines/flow/Flow;", "", "pantryId", "insertShelf", "shelf", "(Lcom/monstock/app/domain/model/Shelf;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateShelf", "app_debug"})
public abstract interface ShelfRepository {
    
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.monstock.app.domain.model.Shelf>> getShelvesByPantry(long pantryId);
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getShelfById(long id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.monstock.app.domain.model.Shelf> $completion);
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertShelf(@org.jetbrains.annotations.NotNull()
    com.monstock.app.domain.model.Shelf shelf, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion);
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updateShelf(@org.jetbrains.annotations.NotNull()
    com.monstock.app.domain.model.Shelf shelf, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteShelf(long id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
}
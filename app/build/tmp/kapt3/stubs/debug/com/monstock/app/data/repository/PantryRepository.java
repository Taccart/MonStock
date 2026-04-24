package com.monstock.app.data.repository;

import com.monstock.app.domain.model.Pantry;
import kotlinx.coroutines.flow.Flow;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0006\bf\u0018\u00002\u00020\u0001J\u0016\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a6@\u00a2\u0006\u0002\u0010\u0006J\u0014\u0010\u0007\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t0\bH&J\u0018\u0010\u000b\u001a\u0004\u0018\u00010\n2\u0006\u0010\u0004\u001a\u00020\u0005H\u00a6@\u00a2\u0006\u0002\u0010\u0006J\u0016\u0010\f\u001a\u00020\u00052\u0006\u0010\r\u001a\u00020\nH\u00a6@\u00a2\u0006\u0002\u0010\u000eJ\u0016\u0010\u000f\u001a\u00020\u00032\u0006\u0010\r\u001a\u00020\nH\u00a6@\u00a2\u0006\u0002\u0010\u000e\u00a8\u0006\u0010"}, d2 = {"Lcom/monstock/app/data/repository/PantryRepository;", "", "deletePantry", "", "id", "", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAllPantries", "Lkotlinx/coroutines/flow/Flow;", "", "Lcom/monstock/app/domain/model/Pantry;", "getPantryById", "insertPantry", "pantry", "(Lcom/monstock/app/domain/model/Pantry;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updatePantry", "app_debug"})
public abstract interface PantryRepository {
    
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.monstock.app.domain.model.Pantry>> getAllPantries();
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getPantryById(long id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.monstock.app.domain.model.Pantry> $completion);
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertPantry(@org.jetbrains.annotations.NotNull()
    com.monstock.app.domain.model.Pantry pantry, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion);
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updatePantry(@org.jetbrains.annotations.NotNull()
    com.monstock.app.domain.model.Pantry pantry, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deletePantry(long id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
}
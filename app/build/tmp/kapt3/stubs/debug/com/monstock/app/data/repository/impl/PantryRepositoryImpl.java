package com.monstock.app.data.repository.impl;

import com.monstock.app.data.local.dao.PantryDao;
import com.monstock.app.data.local.entity.PantryEntity;
import com.monstock.app.data.repository.PantryRepository;
import com.monstock.app.domain.model.Pantry;
import kotlinx.coroutines.flow.Flow;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0016\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0096@\u00a2\u0006\u0002\u0010\tJ\u0014\u0010\n\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\r0\f0\u000bH\u0016J\u0018\u0010\u000e\u001a\u0004\u0018\u00010\r2\u0006\u0010\u0007\u001a\u00020\bH\u0096@\u00a2\u0006\u0002\u0010\tJ\u0016\u0010\u000f\u001a\u00020\b2\u0006\u0010\u0010\u001a\u00020\rH\u0096@\u00a2\u0006\u0002\u0010\u0011J\u0016\u0010\u0012\u001a\u00020\u00062\u0006\u0010\u0010\u001a\u00020\rH\u0096@\u00a2\u0006\u0002\u0010\u0011J\f\u0010\u0013\u001a\u00020\r*\u00020\u0014H\u0002J\f\u0010\u0015\u001a\u00020\u0014*\u00020\rH\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0016"}, d2 = {"Lcom/monstock/app/data/repository/impl/PantryRepositoryImpl;", "Lcom/monstock/app/data/repository/PantryRepository;", "pantryDao", "Lcom/monstock/app/data/local/dao/PantryDao;", "(Lcom/monstock/app/data/local/dao/PantryDao;)V", "deletePantry", "", "id", "", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAllPantries", "Lkotlinx/coroutines/flow/Flow;", "", "Lcom/monstock/app/domain/model/Pantry;", "getPantryById", "insertPantry", "pantry", "(Lcom/monstock/app/domain/model/Pantry;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updatePantry", "toDomain", "Lcom/monstock/app/data/local/entity/PantryEntity;", "toEntity", "app_debug"})
public final class PantryRepositoryImpl implements com.monstock.app.data.repository.PantryRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.monstock.app.data.local.dao.PantryDao pantryDao = null;
    
    @javax.inject.Inject()
    public PantryRepositoryImpl(@org.jetbrains.annotations.NotNull()
    com.monstock.app.data.local.dao.PantryDao pantryDao) {
        super();
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<java.util.List<com.monstock.app.domain.model.Pantry>> getAllPantries() {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object getPantryById(long id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.monstock.app.domain.model.Pantry> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object insertPantry(@org.jetbrains.annotations.NotNull()
    com.monstock.app.domain.model.Pantry pantry, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object updatePantry(@org.jetbrains.annotations.NotNull()
    com.monstock.app.domain.model.Pantry pantry, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object deletePantry(long id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    private final com.monstock.app.domain.model.Pantry toDomain(com.monstock.app.data.local.entity.PantryEntity $this$toDomain) {
        return null;
    }
    
    private final com.monstock.app.data.local.entity.PantryEntity toEntity(com.monstock.app.domain.model.Pantry $this$toEntity) {
        return null;
    }
}
package com.monstock.app.data.repository.impl;

import com.monstock.app.data.local.dao.PantryDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast",
    "deprecation",
    "nullness:initialization.field.uninitialized"
})
public final class PantryRepositoryImpl_Factory implements Factory<PantryRepositoryImpl> {
  private final Provider<PantryDao> pantryDaoProvider;

  public PantryRepositoryImpl_Factory(Provider<PantryDao> pantryDaoProvider) {
    this.pantryDaoProvider = pantryDaoProvider;
  }

  @Override
  public PantryRepositoryImpl get() {
    return newInstance(pantryDaoProvider.get());
  }

  public static PantryRepositoryImpl_Factory create(Provider<PantryDao> pantryDaoProvider) {
    return new PantryRepositoryImpl_Factory(pantryDaoProvider);
  }

  public static PantryRepositoryImpl newInstance(PantryDao pantryDao) {
    return new PantryRepositoryImpl(pantryDao);
  }
}

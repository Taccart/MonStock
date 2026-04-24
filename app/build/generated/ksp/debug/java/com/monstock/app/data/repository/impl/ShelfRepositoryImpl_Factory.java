package com.monstock.app.data.repository.impl;

import com.monstock.app.data.local.dao.ShelfDao;
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
public final class ShelfRepositoryImpl_Factory implements Factory<ShelfRepositoryImpl> {
  private final Provider<ShelfDao> shelfDaoProvider;

  public ShelfRepositoryImpl_Factory(Provider<ShelfDao> shelfDaoProvider) {
    this.shelfDaoProvider = shelfDaoProvider;
  }

  @Override
  public ShelfRepositoryImpl get() {
    return newInstance(shelfDaoProvider.get());
  }

  public static ShelfRepositoryImpl_Factory create(Provider<ShelfDao> shelfDaoProvider) {
    return new ShelfRepositoryImpl_Factory(shelfDaoProvider);
  }

  public static ShelfRepositoryImpl newInstance(ShelfDao shelfDao) {
    return new ShelfRepositoryImpl(shelfDao);
  }
}

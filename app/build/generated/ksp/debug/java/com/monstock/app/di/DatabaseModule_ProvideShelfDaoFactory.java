package com.monstock.app.di;

import com.monstock.app.data.local.dao.ShelfDao;
import com.monstock.app.data.local.database.MonStockDatabase;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
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
public final class DatabaseModule_ProvideShelfDaoFactory implements Factory<ShelfDao> {
  private final Provider<MonStockDatabase> dbProvider;

  public DatabaseModule_ProvideShelfDaoFactory(Provider<MonStockDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public ShelfDao get() {
    return provideShelfDao(dbProvider.get());
  }

  public static DatabaseModule_ProvideShelfDaoFactory create(
      Provider<MonStockDatabase> dbProvider) {
    return new DatabaseModule_ProvideShelfDaoFactory(dbProvider);
  }

  public static ShelfDao provideShelfDao(MonStockDatabase db) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideShelfDao(db));
  }
}

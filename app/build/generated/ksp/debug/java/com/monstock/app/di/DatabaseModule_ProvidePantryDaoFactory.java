package com.monstock.app.di;

import com.monstock.app.data.local.dao.PantryDao;
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
public final class DatabaseModule_ProvidePantryDaoFactory implements Factory<PantryDao> {
  private final Provider<MonStockDatabase> dbProvider;

  public DatabaseModule_ProvidePantryDaoFactory(Provider<MonStockDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public PantryDao get() {
    return providePantryDao(dbProvider.get());
  }

  public static DatabaseModule_ProvidePantryDaoFactory create(
      Provider<MonStockDatabase> dbProvider) {
    return new DatabaseModule_ProvidePantryDaoFactory(dbProvider);
  }

  public static PantryDao providePantryDao(MonStockDatabase db) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.providePantryDao(db));
  }
}

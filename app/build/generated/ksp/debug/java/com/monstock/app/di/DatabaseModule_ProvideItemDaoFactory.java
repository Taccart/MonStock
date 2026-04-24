package com.monstock.app.di;

import com.monstock.app.data.local.dao.ItemDao;
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
public final class DatabaseModule_ProvideItemDaoFactory implements Factory<ItemDao> {
  private final Provider<MonStockDatabase> dbProvider;

  public DatabaseModule_ProvideItemDaoFactory(Provider<MonStockDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public ItemDao get() {
    return provideItemDao(dbProvider.get());
  }

  public static DatabaseModule_ProvideItemDaoFactory create(Provider<MonStockDatabase> dbProvider) {
    return new DatabaseModule_ProvideItemDaoFactory(dbProvider);
  }

  public static ItemDao provideItemDao(MonStockDatabase db) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideItemDao(db));
  }
}

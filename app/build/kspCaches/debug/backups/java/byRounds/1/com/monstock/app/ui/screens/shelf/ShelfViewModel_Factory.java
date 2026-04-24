package com.monstock.app.ui.screens.shelf;

import com.monstock.app.data.repository.PantryRepository;
import com.monstock.app.data.repository.ShelfRepository;
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
public final class ShelfViewModel_Factory implements Factory<ShelfViewModel> {
  private final Provider<ShelfRepository> shelfRepositoryProvider;

  private final Provider<PantryRepository> pantryRepositoryProvider;

  public ShelfViewModel_Factory(Provider<ShelfRepository> shelfRepositoryProvider,
      Provider<PantryRepository> pantryRepositoryProvider) {
    this.shelfRepositoryProvider = shelfRepositoryProvider;
    this.pantryRepositoryProvider = pantryRepositoryProvider;
  }

  @Override
  public ShelfViewModel get() {
    return newInstance(shelfRepositoryProvider.get(), pantryRepositoryProvider.get());
  }

  public static ShelfViewModel_Factory create(Provider<ShelfRepository> shelfRepositoryProvider,
      Provider<PantryRepository> pantryRepositoryProvider) {
    return new ShelfViewModel_Factory(shelfRepositoryProvider, pantryRepositoryProvider);
  }

  public static ShelfViewModel newInstance(ShelfRepository shelfRepository,
      PantryRepository pantryRepository) {
    return new ShelfViewModel(shelfRepository, pantryRepository);
  }
}

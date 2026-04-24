package com.monstock.app.ui.screens.home;

import com.monstock.app.data.repository.ItemRepository;
import com.monstock.app.data.repository.PantryRepository;
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
public final class HomeViewModel_Factory implements Factory<HomeViewModel> {
  private final Provider<ItemRepository> itemRepositoryProvider;

  private final Provider<PantryRepository> pantryRepositoryProvider;

  public HomeViewModel_Factory(Provider<ItemRepository> itemRepositoryProvider,
      Provider<PantryRepository> pantryRepositoryProvider) {
    this.itemRepositoryProvider = itemRepositoryProvider;
    this.pantryRepositoryProvider = pantryRepositoryProvider;
  }

  @Override
  public HomeViewModel get() {
    return newInstance(itemRepositoryProvider.get(), pantryRepositoryProvider.get());
  }

  public static HomeViewModel_Factory create(Provider<ItemRepository> itemRepositoryProvider,
      Provider<PantryRepository> pantryRepositoryProvider) {
    return new HomeViewModel_Factory(itemRepositoryProvider, pantryRepositoryProvider);
  }

  public static HomeViewModel newInstance(ItemRepository itemRepository,
      PantryRepository pantryRepository) {
    return new HomeViewModel(itemRepository, pantryRepository);
  }
}

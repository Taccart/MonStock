package com.monstock.app.ui.screens.addedit;

import com.monstock.app.data.repository.ItemRepository;
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
public final class AddEditItemViewModel_Factory implements Factory<AddEditItemViewModel> {
  private final Provider<ItemRepository> itemRepositoryProvider;

  private final Provider<ShelfRepository> shelfRepositoryProvider;

  private final Provider<PantryRepository> pantryRepositoryProvider;

  public AddEditItemViewModel_Factory(Provider<ItemRepository> itemRepositoryProvider,
      Provider<ShelfRepository> shelfRepositoryProvider,
      Provider<PantryRepository> pantryRepositoryProvider) {
    this.itemRepositoryProvider = itemRepositoryProvider;
    this.shelfRepositoryProvider = shelfRepositoryProvider;
    this.pantryRepositoryProvider = pantryRepositoryProvider;
  }

  @Override
  public AddEditItemViewModel get() {
    return newInstance(itemRepositoryProvider.get(), shelfRepositoryProvider.get(), pantryRepositoryProvider.get());
  }

  public static AddEditItemViewModel_Factory create(Provider<ItemRepository> itemRepositoryProvider,
      Provider<ShelfRepository> shelfRepositoryProvider,
      Provider<PantryRepository> pantryRepositoryProvider) {
    return new AddEditItemViewModel_Factory(itemRepositoryProvider, shelfRepositoryProvider, pantryRepositoryProvider);
  }

  public static AddEditItemViewModel newInstance(ItemRepository itemRepository,
      ShelfRepository shelfRepository, PantryRepository pantryRepository) {
    return new AddEditItemViewModel(itemRepository, shelfRepository, pantryRepository);
  }
}

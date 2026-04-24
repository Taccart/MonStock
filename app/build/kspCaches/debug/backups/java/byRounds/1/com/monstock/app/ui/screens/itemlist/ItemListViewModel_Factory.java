package com.monstock.app.ui.screens.itemlist;

import com.monstock.app.data.repository.ItemRepository;
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
public final class ItemListViewModel_Factory implements Factory<ItemListViewModel> {
  private final Provider<ItemRepository> itemRepositoryProvider;

  private final Provider<ShelfRepository> shelfRepositoryProvider;

  public ItemListViewModel_Factory(Provider<ItemRepository> itemRepositoryProvider,
      Provider<ShelfRepository> shelfRepositoryProvider) {
    this.itemRepositoryProvider = itemRepositoryProvider;
    this.shelfRepositoryProvider = shelfRepositoryProvider;
  }

  @Override
  public ItemListViewModel get() {
    return newInstance(itemRepositoryProvider.get(), shelfRepositoryProvider.get());
  }

  public static ItemListViewModel_Factory create(Provider<ItemRepository> itemRepositoryProvider,
      Provider<ShelfRepository> shelfRepositoryProvider) {
    return new ItemListViewModel_Factory(itemRepositoryProvider, shelfRepositoryProvider);
  }

  public static ItemListViewModel newInstance(ItemRepository itemRepository,
      ShelfRepository shelfRepository) {
    return new ItemListViewModel(itemRepository, shelfRepository);
  }
}

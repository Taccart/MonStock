package com.monstock.app.ui.screens.itemdetail;

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
public final class ItemDetailViewModel_Factory implements Factory<ItemDetailViewModel> {
  private final Provider<ItemRepository> itemRepositoryProvider;

  private final Provider<ShelfRepository> shelfRepositoryProvider;

  public ItemDetailViewModel_Factory(Provider<ItemRepository> itemRepositoryProvider,
      Provider<ShelfRepository> shelfRepositoryProvider) {
    this.itemRepositoryProvider = itemRepositoryProvider;
    this.shelfRepositoryProvider = shelfRepositoryProvider;
  }

  @Override
  public ItemDetailViewModel get() {
    return newInstance(itemRepositoryProvider.get(), shelfRepositoryProvider.get());
  }

  public static ItemDetailViewModel_Factory create(Provider<ItemRepository> itemRepositoryProvider,
      Provider<ShelfRepository> shelfRepositoryProvider) {
    return new ItemDetailViewModel_Factory(itemRepositoryProvider, shelfRepositoryProvider);
  }

  public static ItemDetailViewModel newInstance(ItemRepository itemRepository,
      ShelfRepository shelfRepository) {
    return new ItemDetailViewModel(itemRepository, shelfRepository);
  }
}

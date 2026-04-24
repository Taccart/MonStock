package com.monstock.app.ui.screens.scanner;

import com.monstock.app.data.repository.BarcodeRepository;
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
public final class RestockScanViewModel_Factory implements Factory<RestockScanViewModel> {
  private final Provider<ItemRepository> itemRepositoryProvider;

  private final Provider<ShelfRepository> shelfRepositoryProvider;

  private final Provider<BarcodeRepository> barcodeRepositoryProvider;

  public RestockScanViewModel_Factory(Provider<ItemRepository> itemRepositoryProvider,
      Provider<ShelfRepository> shelfRepositoryProvider,
      Provider<BarcodeRepository> barcodeRepositoryProvider) {
    this.itemRepositoryProvider = itemRepositoryProvider;
    this.shelfRepositoryProvider = shelfRepositoryProvider;
    this.barcodeRepositoryProvider = barcodeRepositoryProvider;
  }

  @Override
  public RestockScanViewModel get() {
    return newInstance(itemRepositoryProvider.get(), shelfRepositoryProvider.get(), barcodeRepositoryProvider.get());
  }

  public static RestockScanViewModel_Factory create(Provider<ItemRepository> itemRepositoryProvider,
      Provider<ShelfRepository> shelfRepositoryProvider,
      Provider<BarcodeRepository> barcodeRepositoryProvider) {
    return new RestockScanViewModel_Factory(itemRepositoryProvider, shelfRepositoryProvider, barcodeRepositoryProvider);
  }

  public static RestockScanViewModel newInstance(ItemRepository itemRepository,
      ShelfRepository shelfRepository, BarcodeRepository barcodeRepository) {
    return new RestockScanViewModel(itemRepository, shelfRepository, barcodeRepository);
  }
}

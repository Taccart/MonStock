package com.monstock.app.ui.screens.scanner;

import com.monstock.app.data.repository.ItemRepository;
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
public final class QrLabelViewModel_Factory implements Factory<QrLabelViewModel> {
  private final Provider<ItemRepository> itemRepositoryProvider;

  public QrLabelViewModel_Factory(Provider<ItemRepository> itemRepositoryProvider) {
    this.itemRepositoryProvider = itemRepositoryProvider;
  }

  @Override
  public QrLabelViewModel get() {
    return newInstance(itemRepositoryProvider.get());
  }

  public static QrLabelViewModel_Factory create(Provider<ItemRepository> itemRepositoryProvider) {
    return new QrLabelViewModel_Factory(itemRepositoryProvider);
  }

  public static QrLabelViewModel newInstance(ItemRepository itemRepository) {
    return new QrLabelViewModel(itemRepository);
  }
}

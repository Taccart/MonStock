package com.monstock.app.ui.screens.scanner;

import com.monstock.app.data.repository.BarcodeRepository;
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
public final class BarcodeConfirmViewModel_Factory implements Factory<BarcodeConfirmViewModel> {
  private final Provider<BarcodeRepository> barcodeRepositoryProvider;

  public BarcodeConfirmViewModel_Factory(Provider<BarcodeRepository> barcodeRepositoryProvider) {
    this.barcodeRepositoryProvider = barcodeRepositoryProvider;
  }

  @Override
  public BarcodeConfirmViewModel get() {
    return newInstance(barcodeRepositoryProvider.get());
  }

  public static BarcodeConfirmViewModel_Factory create(
      Provider<BarcodeRepository> barcodeRepositoryProvider) {
    return new BarcodeConfirmViewModel_Factory(barcodeRepositoryProvider);
  }

  public static BarcodeConfirmViewModel newInstance(BarcodeRepository barcodeRepository) {
    return new BarcodeConfirmViewModel(barcodeRepository);
  }
}

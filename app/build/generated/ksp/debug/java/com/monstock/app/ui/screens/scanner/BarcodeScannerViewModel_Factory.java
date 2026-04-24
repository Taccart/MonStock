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
public final class BarcodeScannerViewModel_Factory implements Factory<BarcodeScannerViewModel> {
  private final Provider<BarcodeRepository> barcodeRepositoryProvider;

  public BarcodeScannerViewModel_Factory(Provider<BarcodeRepository> barcodeRepositoryProvider) {
    this.barcodeRepositoryProvider = barcodeRepositoryProvider;
  }

  @Override
  public BarcodeScannerViewModel get() {
    return newInstance(barcodeRepositoryProvider.get());
  }

  public static BarcodeScannerViewModel_Factory create(
      Provider<BarcodeRepository> barcodeRepositoryProvider) {
    return new BarcodeScannerViewModel_Factory(barcodeRepositoryProvider);
  }

  public static BarcodeScannerViewModel newInstance(BarcodeRepository barcodeRepository) {
    return new BarcodeScannerViewModel(barcodeRepository);
  }
}

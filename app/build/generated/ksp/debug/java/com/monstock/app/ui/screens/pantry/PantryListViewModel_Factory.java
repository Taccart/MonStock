package com.monstock.app.ui.screens.pantry;

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
public final class PantryListViewModel_Factory implements Factory<PantryListViewModel> {
  private final Provider<PantryRepository> pantryRepositoryProvider;

  public PantryListViewModel_Factory(Provider<PantryRepository> pantryRepositoryProvider) {
    this.pantryRepositoryProvider = pantryRepositoryProvider;
  }

  @Override
  public PantryListViewModel get() {
    return newInstance(pantryRepositoryProvider.get());
  }

  public static PantryListViewModel_Factory create(
      Provider<PantryRepository> pantryRepositoryProvider) {
    return new PantryListViewModel_Factory(pantryRepositoryProvider);
  }

  public static PantryListViewModel newInstance(PantryRepository pantryRepository) {
    return new PantryListViewModel(pantryRepository);
  }
}

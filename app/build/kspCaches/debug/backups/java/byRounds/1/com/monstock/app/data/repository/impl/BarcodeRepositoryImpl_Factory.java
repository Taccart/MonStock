package com.monstock.app.data.repository.impl;

import com.monstock.app.data.local.dao.BarcodeCacheDao;
import com.monstock.app.data.remote.api.OpenFoodFactsApiService;
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
public final class BarcodeRepositoryImpl_Factory implements Factory<BarcodeRepositoryImpl> {
  private final Provider<BarcodeCacheDao> cacheDaoProvider;

  private final Provider<OpenFoodFactsApiService> apiServiceProvider;

  public BarcodeRepositoryImpl_Factory(Provider<BarcodeCacheDao> cacheDaoProvider,
      Provider<OpenFoodFactsApiService> apiServiceProvider) {
    this.cacheDaoProvider = cacheDaoProvider;
    this.apiServiceProvider = apiServiceProvider;
  }

  @Override
  public BarcodeRepositoryImpl get() {
    return newInstance(cacheDaoProvider.get(), apiServiceProvider.get());
  }

  public static BarcodeRepositoryImpl_Factory create(Provider<BarcodeCacheDao> cacheDaoProvider,
      Provider<OpenFoodFactsApiService> apiServiceProvider) {
    return new BarcodeRepositoryImpl_Factory(cacheDaoProvider, apiServiceProvider);
  }

  public static BarcodeRepositoryImpl newInstance(BarcodeCacheDao cacheDao,
      OpenFoodFactsApiService apiService) {
    return new BarcodeRepositoryImpl(cacheDao, apiService);
  }
}

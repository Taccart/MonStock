package com.monstock.app.di;

import com.monstock.app.data.remote.api.OpenFoodFactsApiService;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import retrofit2.Retrofit;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata("javax.inject.Named")
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
public final class NetworkModule_ProvideOpenFoodFactsApiServiceFactory implements Factory<OpenFoodFactsApiService> {
  private final Provider<Retrofit> retrofitProvider;

  public NetworkModule_ProvideOpenFoodFactsApiServiceFactory(Provider<Retrofit> retrofitProvider) {
    this.retrofitProvider = retrofitProvider;
  }

  @Override
  public OpenFoodFactsApiService get() {
    return provideOpenFoodFactsApiService(retrofitProvider.get());
  }

  public static NetworkModule_ProvideOpenFoodFactsApiServiceFactory create(
      Provider<Retrofit> retrofitProvider) {
    return new NetworkModule_ProvideOpenFoodFactsApiServiceFactory(retrofitProvider);
  }

  public static OpenFoodFactsApiService provideOpenFoodFactsApiService(Retrofit retrofit) {
    return Preconditions.checkNotNullFromProvides(NetworkModule.INSTANCE.provideOpenFoodFactsApiService(retrofit));
  }
}

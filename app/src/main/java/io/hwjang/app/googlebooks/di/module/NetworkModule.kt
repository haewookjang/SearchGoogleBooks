package io.hwjang.app.googlebooks.di.module

import androidx.paging.PagedList
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import io.hwjang.app.googlebooks.data.paging.PageConfig
import io.hwjang.app.googlebooks.data.api.GoogleBooksService
import io.hwjang.app.googlebooks.data.repository.remote.GoogleBooksRepositoryImpl
import io.hwjang.app.googlebooks.domain.repository.GoogleBooksRepository

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(GoogleBooksService.BASE_URL)
            .client(okHttpClient)
            .build()


    @Provides
    @Singleton
    fun providePageListConfig() = PagedList.Config.Builder()
        .setPageSize(PageConfig.DEFAULT_PAGE_SIZE)
        .setEnablePlaceholders(true)
        .setPrefetchDistance(PageConfig.PREFETCH_DISTANCE)
        .build()


    @Provides
    @Singleton
    fun provideGoogleBooksService(retrofit: Retrofit): GoogleBooksService =
        retrofit.create(GoogleBooksService::class.java)

    @Provides
    @Singleton
    fun provideGoogleBooksRepository(
        service: GoogleBooksService,
        config: PagedList.Config
    ): GoogleBooksRepository {
        return GoogleBooksRepositoryImpl(
            service,
            config
        )
    }

}
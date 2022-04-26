package com.mapsearch.network.di

import com.mapsearch.network.BuildConfig.DEBUG
import com.mapsearch.network.api.hubs.HubsSearchApi
import com.mapsearch.network.api.nearby.NearbyApi
import com.mapsearch.network.authprovider.AuthProvider
import com.mapsearch.network.authprovider.IAuthProvider
import com.mapsearch.network.inerceptors.HeadersInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    companion object {
        const val baseUrl = "https://staging.donkey.bike/"
        const val HTTP_TIMEOUT = 30000
    }

    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            setLevel(if (DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE)
        }
    }

    @Provides
    fun provideHeaderInterceptor(provider: IAuthProvider): HeadersInterceptor {
        return HeadersInterceptor(provider)
    }

    @Provides
    fun privideAuthProvider(): IAuthProvider {
        return AuthProvider()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        headersInterceptor: HeadersInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(HTTP_TIMEOUT.toLong(), TimeUnit.MILLISECONDS)
            .writeTimeout(HTTP_TIMEOUT.toLong(), TimeUnit.MILLISECONDS)
            .readTimeout(HTTP_TIMEOUT.toLong(), TimeUnit.MILLISECONDS)
            .addInterceptor(loggingInterceptor)
            .addInterceptor(headersInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient) =
        Retrofit.Builder().client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseUrl)
            .build()

    @Provides
    @Singleton
    fun provideNearbyApi(retrofit: Retrofit): NearbyApi {
        return retrofit.create(NearbyApi::class.java)
    }

    @Provides
    @Singleton
    fun provideHubsSearchApi(retrofit: Retrofit): HubsSearchApi {
        return retrofit.create(HubsSearchApi::class.java)
    }
}
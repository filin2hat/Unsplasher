package com.biryulindevelop.unsplash.di

import android.content.Context
import com.biryulindevelop.data.api.ApiDigest
import com.biryulindevelop.data.api.ApiPhotos
import com.biryulindevelop.data.api.ApiProfile
import com.biryulindevelop.data.api.authentication.ApiToken
import com.biryulindevelop.data.api.authentication.AuthTokenProvider
import com.biryulindevelop.data.api.authentication.interceptor.AuthTokenInterceptor
import com.biryulindevelop.data.api.authentication.interceptor.AuthTokenInterceptorQualifier
import com.biryulindevelop.data.api.authentication.interceptor.LoggingInterceptorQualifier
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class ApiModule {

    @Provides
    @Singleton
    fun provideAuthTokenProvider(@ApplicationContext context: Context): AuthTokenProvider =
        AuthTokenProvider(context)

    @Provides
    @AuthTokenInterceptorQualifier
    fun provideAuthTokenInterceptor(tokenProvider: AuthTokenProvider): Interceptor =
        AuthTokenInterceptor(tokenProvider)

    @Provides
    @LoggingInterceptorQualifier
    fun provideLoginInterceptor(): Interceptor =
        HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BASIC)

    @Provides
    @Singleton
    fun provideUnsplashClient(
        @LoggingInterceptorQualifier loggingInterceptor: Interceptor,
        @AuthTokenInterceptorQualifier authTokenInterceptor: Interceptor
    ): OkHttpClient = OkHttpClient.Builder()
        .addNetworkInterceptor(loggingInterceptor)
        .addInterceptor(authTokenInterceptor)
        .followRedirects(true)
        .build()

    @Provides
    @Singleton
    fun provideRetrofit(okhttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl("https://api.unsplash.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(okhttpClient)
        .build()

    @Provides
    @Singleton
    fun provideApiToken(retrofit: Retrofit): ApiToken = retrofit.create(ApiToken::class.java)

    @Provides
    @Singleton
    fun provideApiPhotos(retrofit: Retrofit): ApiPhotos = retrofit.create(ApiPhotos::class.java)

    @Provides
    @Singleton
    fun provideApiDigest(retrofit: Retrofit): ApiDigest = retrofit.create(ApiDigest::class.java)

    @Provides
    @Singleton
    fun provideApiProfile(retrofit: Retrofit): ApiProfile = retrofit.create(ApiProfile::class.java)

}
package com.example.teacherapp.di

import com.example.teacherapp.cookiesmanage.AppCookieJar
import com.example.teacherapp.network.loginApi
import com.example.teacherapp.utils.Constants
import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideCookieJar(@ApplicationContext context: Context): AppCookieJar {
        return AppCookieJar(context)
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(cookieJar: AppCookieJar): OkHttpClient {
        return OkHttpClient.Builder()
            .cookieJar(cookieJar)
            .build()
    }

    @Singleton
    @Provides
    fun provideBooksApi(client: OkHttpClient): loginApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(loginApi::class.java)
    }
}

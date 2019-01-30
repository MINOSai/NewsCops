package com.example.newscops.di.module

import android.app.Application
import android.content.SharedPreferences
import androidx.room.Room
import com.example.newscops.BuildConfig
import com.example.newscops.db.NewsDao
import com.example.newscops.db.NewsDatabase
import com.example.newscops.network.WebService
import com.example.newscops.repo.NewsRepo
import com.example.newscops.util.PreferenceHelper
import com.google.gson.Gson
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun provideGson(): Gson = Gson()

    @Provides
    @Singleton
    fun providePreferences(application: Application): SharedPreferences = PreferenceHelper.defaultPrefs(application)

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val okHttpClient = OkHttpClient.Builder()
        okHttpClient.addInterceptor { chain ->
            val original = chain.request()
            val httpUrl = original.url()

            val newHttpUrl = httpUrl.newBuilder().addQueryParameter("apiKey", BuildConfig.NewsApiKey).build()

            val requestBuilder = original.newBuilder().url(newHttpUrl)

            val request = requestBuilder.build()
            return@addInterceptor chain.proceed(request)
        }
        return okHttpClient.build()
    }

    @Provides
    @Singleton
    fun provideWebService(okHttpClient: OkHttpClient): WebService = Retrofit.Builder()
        .baseUrl(BuildConfig.NewsApiBaseUrl)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()
        .create(WebService::class.java)

    @Provides
    @Singleton
    fun provideDatabase(application: Application): NewsDatabase = Room.databaseBuilder(
                application,
                NewsDatabase::class.java,
            "newsdatabase.db"
            ).build()

    @Provides
    @Singleton
    fun provideDao(database: NewsDatabase): NewsDao = database.newsDao()

    @Provides
    @Singleton
    fun provideRepo(dao: NewsDao, webService: WebService, prefs: SharedPreferences): NewsRepo = NewsRepo(webService, dao, prefs)

}
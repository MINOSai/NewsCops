package com.example.newscops.network

import com.example.newscops.model.api.NewsResponse
import com.example.newscops.model.api.SourceResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WebService {

    @GET("top-headlines")
    fun fetchHeadlines(@Query("country") country: String): Deferred<Response<NewsResponse>>

    @GET("sources")
    fun fetchSources(@Query("country") country: String): Deferred<Response<SourceResponse>>

    @GET("everything")
    fun populateResults(@Query("q") query: String): Deferred<Response<NewsResponse>>

    @GET("top-headlines")
    fun getSourceArticles(@Query("sources") source: String): Deferred<Response<NewsResponse>>

}
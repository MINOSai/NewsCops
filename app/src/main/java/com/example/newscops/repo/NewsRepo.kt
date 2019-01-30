package com.example.newscops.repo

import android.content.SharedPreferences
import android.os.AsyncTask
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.newscops.db.NewsDao
import com.example.newscops.model.ArticleModel
import com.example.newscops.model.SourceModel
import com.example.newscops.network.WebService
import com.example.newscops.util.PreferenceHelper
import javax.inject.Inject
import javax.inject.Singleton

import com.example.newscops.util.PreferenceHelper.get
import com.example.newscops.util.PreferenceHelper.set
import kotlinx.coroutines.*
import org.jetbrains.anko.doAsync
import java.lang.Exception

@Singleton
class NewsRepo @Inject constructor(val webService: WebService, val dao: NewsDao, val prefs: SharedPreferences) {

    var countryCode: String? = null

    init {
        countryCode = fetchCountryCode()
    }

    fun getArticles(): LiveData<PagedList<ArticleModel>> {
        refreshHeadlines()

        return LivePagedListBuilder(dao.getAllArticles(), 20).build()
    }

    fun getSources(): LiveData<List<SourceModel>> {

        updateSources()

        return dao.getAllSources()

    }

    fun refreshHeadlines() {
        GlobalScope.launch {
            try {
                val request = webService.fetchHeadlines(countryCode ?: "in")
                val response = request.await()
                if (response.isSuccessful) {
                    dao.insertArticles(response.body()?.articles!!)
                }
            } catch(e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun updateSources() {
        GlobalScope.launch {
            try {
                val request = webService.fetchSources(countryCode ?: "in")
                val response = request.await()
                if (response.isSuccessful) {
                    dao.insertSources(response.body()?.sources!!)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun fetchCountryCode() = prefs[PreferenceHelper.PREF_COUNTRY_KEY, "in"]

    suspend fun populateResults(query: String): List<ArticleModel>? {
        try {
            val request = webService.populateResults(query)
            val response = request.await()
            if (response.isSuccessful) {
                return response.body()?.articles
            }
            return listOf()
        } catch (e: Exception) {
            e.printStackTrace()
            return listOf()
        }
    }

    suspend fun getSourceNews(source: String): List<ArticleModel>? {
        try {
            val request = webService.getSourceArticles(source)
            val response = request.await()
            if (response.isSuccessful) {
                return response.body()?.articles
            }
            return listOf()
        } catch (e: Exception) {
            e.printStackTrace()
            return listOf()
        }
    }

}
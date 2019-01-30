package com.example.newscops.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newscops.model.ArticleModel
import com.example.newscops.repo.NewsRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SearchViewModel @Inject constructor(val repo: NewsRepo) : ViewModel() {

    var searchResults = MutableLiveData<List<ArticleModel>>()

    fun populateSearchResults(query: String) {
        GlobalScope.launch {
            var list = repo.populateResults(query)
            withContext(Dispatchers.Main) {
                searchResults.value = list
            }
        }
    }

    fun getAllSources() = repo.getSources()

}
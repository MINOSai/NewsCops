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

class SourceViewModel @Inject constructor(val repo: NewsRepo) : ViewModel() {

    var articlesList = MutableLiveData<List<ArticleModel>>()

    fun getSourceArticles(source: String) {
        GlobalScope.launch {
            var list = repo.getSourceNews(source)
            withContext(Dispatchers.Main) {
                articlesList.value = list
            }
        }
    }

}
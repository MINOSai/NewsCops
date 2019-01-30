package com.example.newscops.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.example.newscops.model.ArticleModel
import com.example.newscops.repo.NewsRepo
import javax.inject.Inject

class MainViewModel @Inject constructor(val repo: NewsRepo) : ViewModel() {

    fun getArticles() = repo.getArticles()

    fun refreshHeadlines() = repo.refreshHeadlines()

}
package com.example.newscops.model.api

import com.example.newscops.model.ArticleModel

data class NewsResponse (
    var status: String,
    var totalResults: String,
    var articles: List<ArticleModel>
)
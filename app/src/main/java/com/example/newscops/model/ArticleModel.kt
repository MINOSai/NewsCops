package com.example.newscops.model

import androidx.annotation.Nullable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "articlemodel")
data class ArticleModel (
    @Embedded var source: ArticleSourceModel? = ArticleSourceModel(),
    var author: String? = "",
    var title: String? = "",
    var description: String? = "",
    @PrimaryKey var url: String = "",
    var urlToImage: String? = "",
    var publishedAt: String? = "",
    var content: String? = ""
)

data class ArticleSourceModel (
    var id: String? = "",
    var name: String? = ""
)
package com.example.newscops.db

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.newscops.model.ArticleModel
import com.example.newscops.model.SourceModel
import androidx.room.Query

@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertArticles(articles: List<ArticleModel>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSources(sources: List<SourceModel>)

    @Query("SELECT COUNT(*) FROM sourcemodel")
    fun getSourcesCount():Int

    @Query("SELECT * FROM articlemodel ORDER BY publishedAt DESC")
    fun getAllArticles(): DataSource.Factory<Int, ArticleModel>

    @Query("SELECT * FROM sourcemodel ORDER BY name ASC")
    fun getAllSources(): LiveData<List<SourceModel>>

    @Query("SELECT * FROM articlemodel ORDER BY publishedAt DESC LIMIT 1")
    fun getTopArticle(): ArticleModel
}
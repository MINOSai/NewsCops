package com.example.newscops.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.newscops.model.ArticleModel
import com.example.newscops.model.SourceModel

@Database(entities = [(ArticleModel::class), (SourceModel::class)], version = 1, exportSchema = false)
abstract class NewsDatabase : RoomDatabase() {

    abstract fun newsDao(): NewsDao
    lateinit var INSTANCE: NewsDatabase

}
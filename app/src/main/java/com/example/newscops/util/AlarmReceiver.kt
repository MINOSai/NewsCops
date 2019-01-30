package com.example.newscops.util

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.newscops.db.NewsDao
import com.example.newscops.model.ArticleModel
import dagger.android.AndroidInjection
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject

class AlarmReceiver : BroadcastReceiver() {

    @Inject
    lateinit var dao: NewsDao

    override fun onReceive(context: Context?, intent: Intent?) {
        AndroidInjection.inject(this, context)

        context?.let {
            GlobalScope.launch {
                try {
                    val article: ArticleModel = dao.getTopArticle()
                    withContext(Dispatchers.Main) {
                        HeadlineNotification.notify(it, article.url, article.title ?: "")
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

}
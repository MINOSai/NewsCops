package com.example.newscops.util

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class HeadlineWorker(val context: Context, val params: WorkerParameters) : Worker(context, params) {

    override fun doWork(): Result {
        HeadlineNotification.notify(context, "https://github.com/MINOSai", "This is a sample notification")
        return Result.success()
    }
}
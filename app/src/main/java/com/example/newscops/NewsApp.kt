package com.example.newscops

import android.app.Activity
import android.app.Application
import android.content.BroadcastReceiver
import com.example.newscops.di.AppInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.HasBroadcastReceiverInjector
import javax.inject.Inject

class NewsApp : Application(), HasActivityInjector, HasBroadcastReceiverInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>
    @Inject
    lateinit var broadcastReceiverInjector: DispatchingAndroidInjector<BroadcastReceiver>

    override fun activityInjector() = dispatchingAndroidInjector

    override fun broadcastReceiverInjector() = broadcastReceiverInjector

    override fun onCreate() {
        super.onCreate()

        AppInjector.init(this)
    }
}
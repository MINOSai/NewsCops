package com.example.newscops.di.module

import com.example.newscops.util.AlarmReceiver
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ReceiverModule {

    @ContributesAndroidInjector
    abstract fun contributeAlarmReceiver(): AlarmReceiver

}
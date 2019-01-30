package com.example.newscops.di

import android.app.Application
import com.example.newscops.NewsApp
import com.example.newscops.di.module.ActivityModule
import com.example.newscops.di.module.AppModule
import com.example.newscops.di.module.ReceiverModule
import com.example.newscops.di.module.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidInjectionModule::class,
    AppModule::class,
    ActivityModule::class,
    ViewModelModule::class,
    ReceiverModule::class
])
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun build(): AppComponent
    }

    fun inject(newsApp: NewsApp)

}
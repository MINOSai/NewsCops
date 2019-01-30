package com.example.newscops.di.module

import com.example.newscops.ui.fragment.HomeFragment
import com.example.newscops.ui.fragment.SearchFragment
import com.example.newscops.ui.fragment.SourceFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract fun contributeHomeFragment(): HomeFragment

    @ContributesAndroidInjector
    abstract fun contribuesSearchFragment(): SearchFragment

    @ContributesAndroidInjector
    abstract fun contributeSourceFragment(): SourceFragment

}
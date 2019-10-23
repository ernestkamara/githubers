package io.kamara.githubers.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import io.kamara.githubers.MainActivity

@Suppress("unused")
@Module
abstract class ActivityModule {

    @ContributesAndroidInjector(modules = [FragmentBuildersModule::class])
    abstract fun contributeMainActivity(): MainActivity
}
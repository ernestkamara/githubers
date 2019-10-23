package io.kamara.githubers.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import io.kamara.githubers.ui.UserFragment

@Suppress("unused")
@Module
abstract class FragmentBuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeUserFragment(): UserFragment

}
package io.kamara.githubers.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import io.kamara.githubers.viewmodels.UserListViewModel
import io.kamara.githubers.viewmodels.UserViewModel

@Suppress("unused")
@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(UserViewModel::class)
    abstract fun bindUserViewModel(userViewModel: UserViewModel): ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(UserListViewModel::class)
    abstract fun bindUserListViewModel(userListViewModel: UserListViewModel): ViewModel
}
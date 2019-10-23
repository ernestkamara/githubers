package io.kamara.githubers.viewmodels

import androidx.lifecycle.ViewModel
import io.kamara.githubers.repository.GithuberRepository
import javax.inject.Inject

class UserListViewModel
@Inject constructor(private val repository: GithuberRepository) : ViewModel() {

    val users by lazy { repository.observeUsers() }

}
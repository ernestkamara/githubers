package io.kamara.githubers.viewmodels

import androidx.lifecycle.*
import io.kamara.githubers.repository.GithuberRepository
import javax.inject.Inject

class UserViewModel
@Inject constructor(private val repository: GithuberRepository) : ViewModel() {

    lateinit var id: String

    val user by lazy { repository.observeUser(id) }

}
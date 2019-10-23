package io.kamara.githubers.viewmodels

import androidx.lifecycle.*
import io.kamara.githubers.repository.GithuberRepository
import io.kamara.githubers.testing.OpenForTesting
import javax.inject.Inject

@OpenForTesting
class UserViewModel
@Inject constructor(private val repository: GithuberRepository) : ViewModel() {
    private val _login = MutableLiveData<String>()


    //val user by lazy { repository.observeUser(login) }

    val user = Transformations
        .switchMap(_login) {input ->
        if (input.isNullOrEmpty()) {
            AbsentLiveData.create()
        } else {
            repository.observeUser(input)
        }
    }

    fun setLogin(login: String?) {
        _login.value = login
    }
}

class AbsentLiveData<T : Any?> private constructor(): LiveData<T>() {
    init {
        // use post instead of set since this can be created on any thread
        postValue(null)
    }

    companion object {
        fun <T> create(): LiveData<T> {
            return AbsentLiveData()
        }
    }
}

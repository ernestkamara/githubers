package io.kamara.githubers.viewmodels

import androidx.lifecycle.*
import io.kamara.githubers.model.UserDao
import io.kamara.githubers.repository.GithuberRepository
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class UserViewModel
@Inject constructor(private val repository: GithuberRepository, private val userDao: UserDao) : ViewModel() {

    private val _login = MutableLiveData<String>()

    val user = _login.switchMap { id ->
        liveData(context = viewModelScope.coroutineContext + Dispatchers.IO) {
            emit(repository.getUser(id))
        }
    }

    fun setLogin(login: String?) {
        if (_login.value != login) {
            _login.value = login
        }
    }

}
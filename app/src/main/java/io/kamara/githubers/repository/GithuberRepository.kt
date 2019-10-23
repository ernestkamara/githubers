package io.kamara.githubers.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import io.kamara.githubers.db.UserDao
import io.kamara.githubers.model.Result
import io.kamara.githubers.testing.OpenForTesting
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@OpenForTesting
class GithuberRepository
@Inject constructor(private val remoteDataSource: GithuberServiceDataSource, private val userDao: UserDao) {

    fun observeUser(id: String) = resultLiveData(
        databaseQuery = { userDao.getUser(id)},
        networkCall = { remoteDataSource.fetchUser(id)},
        saveCallResult = { userDao.insert(it)})
        .distinctUntilChanged()

    fun observeUsers() = resultLiveData(
        databaseQuery = { userDao.getUsers()},
        networkCall = { remoteDataSource.fetchUsers()},
        saveCallResult = { userDao.insertAll(it)})
        .distinctUntilChanged()

    fun <T, A> resultLiveData(databaseQuery: () -> LiveData<T>,
                              networkCall: suspend () -> Result<A>,
                              saveCallResult: suspend (A) -> Unit): LiveData<Result<T>> =
        liveData(Dispatchers.IO) {
            emit(Result.loading<T>())
            val source = databaseQuery.invoke().map { Result.success(it) }
            emitSource(source)

            val responseStatus = networkCall.invoke()
            if (responseStatus.status == Result.Status.SUCCESS) {
                saveCallResult(responseStatus.data!!)
            } else if (responseStatus.status == Result.Status.ERROR) {
                emit(Result.error<T>(responseStatus.message!!))
                emitSource(source)
            }
        }
}
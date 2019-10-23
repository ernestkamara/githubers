package io.kamara.githubers.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import io.kamara.githubers.model.User
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit


object TestUtil {

    fun createUser(login: String) = User(
        login = login,
        avatarUrl = null,
        name = "$login name",
        company = null,
        reposUrl = null,
        blog = null
    )

    fun <T> getValue(liveData: LiveData<T>): T {
        val data = arrayOfNulls<Any>(1)
        val latch = CountDownLatch(1)
        val observer = object : Observer<T> {
            override fun onChanged(o: T?) {
                data[0] = o
                latch.countDown()
                liveData.removeObserver(this)
            }
        }
        liveData.observeForever(observer)
        latch.await(2, TimeUnit.SECONDS)

        @Suppress("UNCHECKED_CAST")
        return data[0] as T
    }
}

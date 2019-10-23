package io.kamara.githubers.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import io.kamara.githubers.util.TestUtil.createUser
import io.kamara.githubers.util.TestUtil.getValue
import junit.framework.Assert.assertNull
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UserDaoTest : GithuberDatabaseTest() {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var userDao: UserDao

    private val ernest = createUser("ernest")
    private val john = ernest.copy(login = "john", company = "dnb")

    @Before
    fun createDb() {
        userDao = database.userDao()

        runBlocking {
            userDao.insertAll(listOf(ernest, john))
        }
    }

    @Test
    fun updateUser() {
        val loaded = getValue(userDao.getUser(john.login))
        assertThat(loaded.login, `is`("john"))
        assertNull(loaded.avatarUrl)

        val replacement = john.copy(avatarUrl = "foo")
        runBlocking {
            userDao.insert(replacement)
        }

        val loadedReplacement = getValue(userDao.getUser(replacement.login))
        assertThat(loadedReplacement.avatarUrl, `is`("foo"))
    }

    @Test
    fun getUser() {
        val ernest = getValue(userDao.getUser("ernest"))
        assertThat(ernest.login, `is`("ernest"))
    }
}

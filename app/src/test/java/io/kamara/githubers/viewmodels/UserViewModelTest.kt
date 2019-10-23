package io.kamara.githubers.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import io.kamara.githubers.model.User
import io.kamara.githubers.model.Result
import io.kamara.githubers.repository.GithuberRepository
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.*

@RunWith(JUnit4::class)
class UserViewModelTest {
    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private val userRepository = mock(GithuberRepository::class.java)
    private val userViewModel = UserViewModel(userRepository)

    @Test
    fun testNull() {
        assertThat(userViewModel.user, notNullValue())
        verify(userRepository, never()).observeUser(anyString())

        userViewModel.setLogin("ernest")
        verify(userRepository, never()).observeUser("ernest")
    }

    @Test
    fun sendResultToUI() {
        val foo = MutableLiveData<Result<User>>()
        val bar = MutableLiveData<Result<User>>()

        `when`(userRepository.observeUser("foo")).thenReturn(foo)
        `when`(userRepository.observeUser("bar")).thenReturn(bar)

        val observer = mock<Observer<Result<User>>>()
        userViewModel.user.observeForever(observer)
        userViewModel.setLogin("foo")
        verify(observer, never()).onChanged(any())

        val fooUser = createUser("foo")
        val fooValue = Result.success(fooUser)

        foo.value = fooValue
        verify(observer).onChanged(fooValue)
        reset(observer)
        val barUser = createUser("bar")
        val barValue = Result.success(barUser)
        bar.value = barValue
        userViewModel.setLogin("bar")
        verify(observer).onChanged(barValue)
    }

    @Test
    fun nullUser() {
        val observer = mock<Observer<Result<User>>>()
        userViewModel.setLogin("foo")
        userViewModel.setLogin(null)
        userViewModel.user.observeForever(observer)
        verify(observer).onChanged(null)
    }

    private fun createUser(login: String) = User(
        login = login,
        avatarUrl = null,
        name = "$login name",
        company = null,
        reposUrl = null,
        blog = null
    )

    private inline fun <reified T> mock(): T = mock(T::class.java)


}
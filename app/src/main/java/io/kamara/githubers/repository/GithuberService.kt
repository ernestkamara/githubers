package io.kamara.githubers.repository

import androidx.lifecycle.LiveData
import io.kamara.githubers.model.User
import retrofit2.http.GET
import retrofit2.http.Path

interface GithuberService {

    @GET("/users")
    suspend fun getUsers(): LiveData<List<User>>


    @GET("users/{login}")
    suspend fun getUser(@Path("login") login: String): LiveData<User>
}
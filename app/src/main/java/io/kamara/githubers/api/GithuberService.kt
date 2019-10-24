package io.kamara.githubers.api

import io.kamara.githubers.model.User
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface GithuberService {

    companion object {
        const val BASE_URL = "https://api.github.com"
    }


    @GET("/users") //TODO: add {since} parameter (<https://api.github.com/users?since=135>; rel="next")
    suspend fun getUsers(): Response<List<User>>


    @GET("/users/{login}")
    suspend fun getUser(@Path("login") login: String): Response<User>
}
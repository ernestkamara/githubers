package io.kamara.githubers.repository

import javax.inject.Inject

class GithuberRepository
@Inject constructor(private val githubService: GithuberService) {

    suspend fun getUsers() = githubService.getUsers()

    suspend fun getUser(login: String) = githubService.getUser(login)

}
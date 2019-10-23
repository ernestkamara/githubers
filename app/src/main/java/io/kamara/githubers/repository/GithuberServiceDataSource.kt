package io.kamara.githubers.repository

import io.kamara.githubers.api.BaseDataSource
import io.kamara.githubers.api.GithuberService
import javax.inject.Inject

class GithuberServiceDataSource @Inject constructor(private val githubService: GithuberService): BaseDataSource() {

    suspend fun fetchUser(login: String) = getResult {  githubService.getUser(login) }

    suspend fun fetchUsers() = getResult {  githubService.getUsers() }
}
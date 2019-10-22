package io.kamara.githubers.model

import androidx.room.Entity
import com.squareup.moshi.Json

@Entity(primaryKeys = ["login"], tableName = "users")
data class User(
    @field:Json(name = "login")
    val login: String,
    @field:Json(name = "avatar_url")
    val avatarUrl: String?,
    @field:Json(name = "name")
    val name: String?,
    @field:Json(name = "company")
    val company: String?,
    @field:Json(name = "repos_url")
    val reposUrl: String?,
    @field:Json(name = "blog")
    val blog: String?
)

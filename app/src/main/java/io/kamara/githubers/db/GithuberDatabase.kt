package io.kamara.githubers.db

import androidx.room.Database
import androidx.room.RoomDatabase
import io.kamara.githubers.model.User


@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class GithuberDatabase : RoomDatabase() {
    companion object {
        const val DATABASE_NAME = "githubers"
    }

    abstract fun userDao(): UserDao
}
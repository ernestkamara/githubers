package io.kamara.githubers.model

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class GithuberDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}
package io.kamara.githubers.db

import androidx.arch.core.executor.testing.CountingTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import org.junit.After
import org.junit.Before
import org.junit.Rule
import java.util.concurrent.TimeUnit

abstract class GithuberDatabaseTest {
    @Rule
    @JvmField
    val countingTaskExecutorRule = CountingTaskExecutorRule()

    private lateinit var _db: GithuberDatabase

    val database: GithuberDatabase get() = _db

    @Before
    fun initDb() {
        _db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            GithuberDatabase::class.java
        ).build()
    }

    @After
    fun closeDb() {
        countingTaskExecutorRule.drainTasks(10, TimeUnit.SECONDS)
        _db.close()
    }
}

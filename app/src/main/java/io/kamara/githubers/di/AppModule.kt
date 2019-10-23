package io.kamara.githubers.di

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import androidx.room.Room
import dagger.Module
import dagger.Provides
import io.kamara.githubers.db.GithuberDatabase
import io.kamara.githubers.db.UserDao
import io.kamara.githubers.api.GithuberService
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class])
class AppModule {

    @Singleton
    @Provides
    fun provideGithuberDatabase(app: Application): GithuberDatabase {
        return Room
            .databaseBuilder(app, GithuberDatabase::class.java,GithuberDatabase.DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideUserDao(db: GithuberDatabase): UserDao {
        return db.userDao()
    }

    @Provides
    @Singleton
    fun provideConnectivityManager(app: Application): ConnectivityManager {
        return app.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    @Singleton
    @Provides
    fun provideGithuberService(): GithuberService {
        return Retrofit.Builder()
            .baseUrl(GithuberService.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(GithuberService::class.java)
    }

}
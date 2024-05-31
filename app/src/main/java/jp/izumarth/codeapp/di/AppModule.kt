package jp.izumarth.codeapp.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import jp.izumarth.codeapp.data.room.Database
import jp.izumarth.codeapp.data.room.ReviewDao
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Singleton
    @Provides
    fun provideReviewDao(db: Database): ReviewDao =
        db.reviewDao()

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): Database =
        Room.databaseBuilder(context, Database::class.java, "beer.db")
            .fallbackToDestructiveMigration()
            .build()
}
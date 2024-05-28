package jp.izumarth.codeapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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
}
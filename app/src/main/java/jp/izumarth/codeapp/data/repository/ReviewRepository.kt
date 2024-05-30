package jp.izumarth.codeapp.data.repository

import jp.izumarth.codeapp.data.room.ReviewDao
import jp.izumarth.codeapp.model.Review
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ReviewRepository @Inject constructor(
    private val source: ReviewDao
) {
    suspend fun getReview(name: String): Flow<Review> =
        source.getReview(name)

    suspend fun replace(review: Review) =
        source.replace(review)
}
package jp.izumarth.codeapp.data.repository

import jp.izumarth.codeapp.data.room.ReviewDao
import jp.izumarth.codeapp.model.Review
import javax.inject.Inject

class ReviewRepository @Inject constructor(
    private val source: ReviewDao
) {
    suspend fun getReview(name: String): Review =
        source.getReview(name)

    suspend fun replace(review: Review) =
        source.replace(review)
}
package jp.izumarth.codeapp.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import jp.izumarth.codeapp.model.Review
import kotlinx.coroutines.flow.Flow

@Dao
interface ReviewDao {

    @Query("SELECT * FROM review WHERE name = :name")
    fun getReview(name: String): Flow<Review>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun replace(review: Review)
}
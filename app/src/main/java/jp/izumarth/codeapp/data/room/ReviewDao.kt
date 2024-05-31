package jp.izumarth.codeapp.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import jp.izumarth.codeapp.model.Review

@Dao
interface ReviewDao {

    @Query("SELECT * FROM review WHERE name = :name")
    suspend fun getReview(name: String): Review

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun replace(review: Review)
}
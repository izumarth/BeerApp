package jp.izumarth.codeapp.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import jp.izumarth.codeapp.model.Review

@Database(
    entities = [
        Review::class,
    ],
    version = 2,
    exportSchema = false,
)
abstract class Database : RoomDatabase() {

    abstract fun reviewDao(): ReviewDao
}
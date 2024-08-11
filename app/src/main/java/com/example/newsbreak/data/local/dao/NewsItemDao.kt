package com.example.newsbreak.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.newsbreak.data.models.NewsItem
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsItemDao {
    @Transaction
    @Query("SELECT * FROM news")
    fun getAll(): Flow<List<NewsItem>>

    @Query(
        "SELECT * FROM news WHERE title LIKE '%' || :query || '%' " +
                "OR description LIKE '%' || :query || '%' " +
                "OR author LIKE '%' || :query || '%'"
    )
    fun findByQuery(query: String): Flow<List<NewsItem>>

    @Insert
    fun insertAll(vararg newsItems: NewsItem)

    @Delete
    fun delete(newsItem: NewsItem)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(newsItem: NewsItem)
}

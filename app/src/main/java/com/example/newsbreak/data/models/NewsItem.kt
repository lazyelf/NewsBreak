package com.example.newsbreak.data.models

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "news")
data class NewsItem(
    @ColumnInfo(name = "author") val author: String?,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "description") val description: String?,
    @ColumnInfo(name = "url") val url: String,
    @ColumnInfo(name = "url_to_image") val urlToImage: String?,
    @ColumnInfo(name = "published_at") val publishedAt: String,
    @ColumnInfo(name = "content") val content: String?,
    @Embedded(prefix = "source") val source: NewsSource,
){
    @PrimaryKey(autoGenerate = true) var id : Int = 0
    @ColumnInfo(name = "is_saved") var isSaved: Boolean = false
}

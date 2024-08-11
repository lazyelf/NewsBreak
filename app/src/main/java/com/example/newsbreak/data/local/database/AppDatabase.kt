package com.example.newsbreak.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.newsbreak.data.local.dao.NewsItemDao
import com.example.newsbreak.data.models.NewsItem


@Database(entities = [NewsItem::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun newsItemDao(): NewsItemDao
}

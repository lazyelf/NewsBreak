package com.example.newsbreak.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.newsbreak.data.local.dao.NewsItemDao
import com.example.newsbreak.data.models.NewsItem

@Database(entities = [NewsItem::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun newsItemDao(): NewsItemDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            createDatabase(context).also {
                it.also { instance = it }
            }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "news_db"
            ).addMigrations(MIGRATION_1_2).build()
    }
}

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("ALTER TABLE news ADD COLUMN is_saved INTEGER NOT NULL DEFAULT(0)")
    }
}


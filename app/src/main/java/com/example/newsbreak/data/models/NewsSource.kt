package com.example.newsbreak.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity
data class NewsSource (
    @ColumnInfo(name = "id") val id: String?,
    @ColumnInfo(name = "name") val name: String?
    )
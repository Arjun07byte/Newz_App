package com.example.newzapp.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(
    tableName = "articleTable"
)
data class Article(
    @PrimaryKey(autoGenerate = true)
    var articleId: Int,
    val author: String ?= "author",
    val content: String ?= "content",
    val description: String ?= "Click to See More Info",
    val publishedAt: String,
    val source: Source,
    val title: String,
    val url: String,
    val urlToImage: String ?= "No Image"
) : Serializable
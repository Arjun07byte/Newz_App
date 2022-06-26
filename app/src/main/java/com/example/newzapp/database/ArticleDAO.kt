package com.example.newzapp.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.newzapp.models.Article

@Dao
interface ArticleDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateAndInsert(article: Article): Long
    
    @Query("SELECT * FROM articleTable")
    fun getAllArticles(): LiveData<List<Article>>
    
    @Delete
    suspend fun deleteArticle(article: Article)
}
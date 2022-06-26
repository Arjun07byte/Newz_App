package com.example.newzapp.repository

import com.example.newzapp.api.RetrofitInstance
import com.example.newzapp.database.ArticleDatabase

class NewsRepository(
    val db: ArticleDatabase
) {
    suspend fun getBreakingNews(pageNum: Int) = RetrofitInstance.apiVar.getBreakingNews(pageNumber = pageNum)
}
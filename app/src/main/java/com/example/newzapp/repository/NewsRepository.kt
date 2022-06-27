package com.example.newzapp.repository

import com.example.newzapp.api.RetrofitInstance
import com.example.newzapp.database.ArticleDatabase
import com.example.newzapp.models.Article

class NewsRepository(
    val db: ArticleDatabase
) {
    suspend fun getBreakingNews(pageNum: Int) = RetrofitInstance.apiVar.getBreakingNews(pageNumber = pageNum)

    suspend fun updateAndInsert(article: Article) = db.getArticleDAO().updateAndInsert(article)

    fun getAllSaved() = db.getArticleDAO().getAllArticles()

    suspend fun deleteArticle(article: Article) = db.getArticleDAO().deleteArticle(article)
}
package com.example.newzapp.uiComponents

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.newzapp.repository.NewsRepository

class NewsVMProvider(
    private val newsRepository: NewsRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NewsViewModel(newsRepository) as T
    }
}
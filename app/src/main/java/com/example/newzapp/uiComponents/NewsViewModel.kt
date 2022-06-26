package com.example.newzapp.uiComponents

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newzapp.models.NewsResponse
import com.example.newzapp.repository.NewsRepository
import com.example.newzapp.utils.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class NewsViewModel(
    private val newsRepository: NewsRepository
): ViewModel() {
    val breakingNewsObject: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    private val breakingNewsPage = 1

    init {
        getBreakingNews()
    }

    private fun getBreakingNews() = viewModelScope.launch {
        breakingNewsObject.postValue(Resource.LoadingCL())
        val currResponse = newsRepository.getBreakingNews(breakingNewsPage)
        breakingNewsObject.postValue(handleResponses(currResponse))
    }

    private fun handleResponses(response: Response<NewsResponse>): Resource<NewsResponse>{
        if (response.isSuccessful){
            response.body()?.let { resultResponse ->
                return Resource.SuccessCL(resultResponse)
            }
        }

        return Resource.ErrorCL(response.message())
    }
}
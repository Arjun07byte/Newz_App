package com.example.newzapp.uiComponents

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.newzapp.utils.NewsApplication
import com.example.newzapp.models.Article
import com.example.newzapp.models.NewsResponse
import com.example.newzapp.repository.NewsRepository
import com.example.newzapp.utils.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class NewsViewModel(
    private val app: Application,
    private val newsRepository: NewsRepository
): AndroidViewModel(app) {
    val breakingNewsObject: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    private val breakingNewsPage = 1

    init {
        getBreakingNews()
    }

    private fun getBreakingNews() = viewModelScope.launch {
        safeBreakingNewsCall()
    }

    private fun handleResponses(response: Response<NewsResponse>): Resource<NewsResponse>{
        if (response.isSuccessful){
            response.body()?.let { resultResponse ->
                return Resource.SuccessCL(resultResponse)
            }
        }

        return Resource.ErrorCL(response.message())
    }

    fun insertArticle(article: Article) = viewModelScope.launch {
        newsRepository.updateAndInsert(article)
    }

    fun getAllNews() = newsRepository.getAllSaved()

    fun deleteArticle(article: Article) = viewModelScope.launch {
        newsRepository.deleteArticle(article)
    }

    private suspend fun safeBreakingNewsCall() {
        breakingNewsObject.postValue(Resource.LoadingCL())
        try{
            if(isInternetAvailable()){
                val currResponse = newsRepository.getBreakingNews(breakingNewsPage)
                breakingNewsObject.postValue(handleResponses(currResponse))
            } else{
                breakingNewsObject.postValue(Resource.ErrorCL("No Internet"))
            }
        } catch (thr: Throwable){
            breakingNewsObject.postValue(Resource.ErrorCL("Error"))
        }
    }

    private fun isInternetAvailable(): Boolean{
        val connectivityManager = getApplication<NewsApplication>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager

        return connectivityManager.run {
            getNetworkCapabilities(activeNetwork)?.run {
                hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                        || hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                        || hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
            }
        } ?: false
    }
}
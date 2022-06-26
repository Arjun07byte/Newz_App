package com.example.newzapp.uiComponents.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newzapp.R
import com.example.newzapp.adapters.NewsAdapter
import com.example.newzapp.uiComponents.NewsActivity
import com.example.newzapp.uiComponents.NewsViewModel
import com.example.newzapp.utils.Resource

class BreakingNewsFragment: Fragment(R.layout.fragment_breaking_news) {
    lateinit var viewModel: NewsViewModel
    lateinit var myNewsAdapter: NewsAdapter
    lateinit var myRecyclerView: RecyclerView
    lateinit var myProgressBar: ProgressBar

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as NewsActivity).myViewModel
        myRecyclerView = view.findViewById(R.id.rvBreakingNews)
        myProgressBar = view.findViewById(R.id.paginationProgressBar)

        setUpMyRecyclerView()
        viewModel.breakingNewsObject.observe(viewLifecycleOwner, Observer {
            when(it){
                is Resource.SuccessCL -> {
                    hideProgressBar()
                    it.myData?.let { newsResp ->
                        myNewsAdapter.differ.submitList(newsResp.articles)
                    }
                }
                is Resource.ErrorCL -> {
                    hideProgressBar()
                    it.currMessage?.let { errorMessage ->
                        Log.e("BreakingNewsFragment", errorMessage)
                    }
                }
                else -> {
                    showProgressBar()
                }
            }
        })
    }

    private fun hideProgressBar(){
        myProgressBar.visibility = View.INVISIBLE
    }

    private fun showProgressBar(){
        myProgressBar.visibility = View.VISIBLE
    }

    private fun setUpMyRecyclerView() {
        myNewsAdapter = NewsAdapter()
        myRecyclerView.apply {
            adapter = myNewsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
}
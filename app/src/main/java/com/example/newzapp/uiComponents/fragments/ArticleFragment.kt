package com.example.newzapp.uiComponents.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.newzapp.R
import com.example.newzapp.uiComponents.NewsActivity
import com.example.newzapp.uiComponents.NewsViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

class ArticleFragment: Fragment(R.layout.fragment_article) {
    private lateinit var viewModel: NewsViewModel
    private val args: ArticleFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as NewsActivity).myViewModel

        val articlePassed = args.article
        (activity as NewsActivity).findViewById<BottomNavigationView>(R.id.bottomNavBar)?.visibility = View.GONE

        val myWebView: WebView = view.findViewById(R.id.articleWebView)
        myWebView.apply {
            webViewClient = WebViewClient()
            loadUrl(articlePassed.url)
        }

        view.findViewById<FloatingActionButton>(R.id.saveNewsButton).setOnClickListener{
            viewModel.insertArticle(articlePassed)
            Snackbar.make(view,"Article Saved Successfully",Snackbar.ANIMATION_MODE_SLIDE).show()
        }
    }
}
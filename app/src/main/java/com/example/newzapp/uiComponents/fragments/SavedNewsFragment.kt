package com.example.newzapp.uiComponents.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.newzapp.R
import com.example.newzapp.uiComponents.NewsActivity
import com.example.newzapp.uiComponents.NewsViewModel

class SavedNewsFragment: Fragment(R.layout.fragment_saved_news) {
    private lateinit var viewModel: NewsViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as NewsActivity).myViewModel
    }
}
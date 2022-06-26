package com.example.newzapp.uiComponents

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.newzapp.R
import com.example.newzapp.database.ArticleDatabase
import com.example.newzapp.databinding.ActivityNewsBinding
import com.example.newzapp.repository.NewsRepository

class NewsActivity: AppCompatActivity() {
    private lateinit var myViewBinding: ActivityNewsBinding
    lateinit var myViewModel: NewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        myViewBinding = ActivityNewsBinding.inflate(layoutInflater)
        setContentView(myViewBinding.root)

        // Bottom Navigation Bar setup with help of navigation controller
        // of my navHost Fragment which is inside the activity_news as newsNavHostFragment
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.newsNavHostFragment) as NavHostFragment
        val navController = navHostFragment.navController
        myViewBinding.bottomNavBar.setupWithNavController(navController)


        // Setting up the view model with the help of repository and factory provider
        val myRepository = NewsRepository(ArticleDatabase(this))
        val vmProvider: NewsVMProvider = NewsVMProvider(myRepository)
        myViewModel = ViewModelProvider(this,vmProvider)[NewsViewModel::class.java]


    }
}
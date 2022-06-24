package com.example.newzapp.uiComponents

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.newzapp.R
import com.example.newzapp.databinding.ActivityNewsBinding

class NewsActivity: AppCompatActivity() {
    private lateinit var myViewBinding: ActivityNewsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        myViewBinding = ActivityNewsBinding.inflate(layoutInflater)
        setContentView(myViewBinding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.newsNavHostFragment) as NavHostFragment
        val navController = navHostFragment.navController

        myViewBinding.bottomNavBar.setupWithNavController(navController)
    }
}
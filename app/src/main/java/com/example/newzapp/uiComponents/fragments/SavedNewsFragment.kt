package com.example.newzapp.uiComponents.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newzapp.R
import com.example.newzapp.adapters.NewsAdapter
import com.example.newzapp.uiComponents.NewsActivity
import com.example.newzapp.uiComponents.NewsViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar

class SavedNewsFragment: Fragment(R.layout.fragment_saved_news) {
    private lateinit var viewModel: NewsViewModel
    private lateinit var myRecyclerView: RecyclerView
    private lateinit var myNewsAdapter: NewsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as NewsActivity).myViewModel
        myRecyclerView = view.findViewById(R.id.savedNewsRV)

        (activity as NewsActivity).findViewById<BottomNavigationView>(R.id.bottomNavBar)?.visibility = View.VISIBLE

        setUpMyRecyclerView()
        myNewsAdapter.setOnItemClickListener {
            val myBundle = Bundle().apply {
                putSerializable("article", it)
            }
            findNavController().navigate(
                R.id.action_savedNewsFragment_to_articleFragment,
                myBundle
            )
        }

        val itemTouchHelperCallBack = object :  ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val pos = viewHolder.adapterPosition
                val articleToDelete = myNewsAdapter.differ.currentList[pos]
                viewModel.deleteArticle(articleToDelete)

                Snackbar.make(view, "Article Successfully Deleted", Snackbar.LENGTH_LONG).apply {
                    setAction("Undo"){
                        viewModel.insertArticle(articleToDelete)
                    }
                    show()
                }
            }
        }

        ItemTouchHelper(itemTouchHelperCallBack).apply {
            attachToRecyclerView(myRecyclerView)
        }

        viewModel.getAllNews().observe(viewLifecycleOwner, Observer {
            myNewsAdapter.differ.submitList(it)
        })
    }

    private fun setUpMyRecyclerView() {
        myNewsAdapter = NewsAdapter()
        myRecyclerView.apply {
            adapter = myNewsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
}
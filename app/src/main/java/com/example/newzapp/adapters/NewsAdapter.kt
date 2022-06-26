package com.example.newzapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newzapp.R
import com.example.newzapp.models.Article

class NewsAdapter: RecyclerView.Adapter<NewsAdapter.ArticleViewHolder>() {
    inner class ArticleViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val eachArticleIv: ImageView = itemView.findViewById(R.id.each_article_iv)
        val eachItemTitleTv: TextView = itemView.findViewById(R.id.each_article_title_tv)
        val eachItemDescTv: TextView = itemView.findViewById(R.id.each_article_disc_tv)
        val eachItemPubTv: TextView = itemView.findViewById(R.id.each_article_pub_tv)
    }

    private val differCallBack = object  : DiffUtil.ItemCallback<Article>(){
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this,differCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        return ArticleViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.article_rv_view,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val currArticle = differ.currentList[position]
        holder.itemView.setOnClickListener{
            onItemClickListener?.let {
                it(currArticle)
            }
        }

        Glide.with(holder.itemView).load(currArticle.urlToImage).into(holder.eachArticleIv)
        holder.eachItemTitleTv.text = currArticle.title
        holder.eachItemDescTv.text = currArticle.description
        holder.eachItemPubTv.text = currArticle.publishedAt
    }

    private var onItemClickListener: ((Article) -> Unit) ?= null

    fun setOnItemClickListener(listener: (Article) -> Unit){
        onItemClickListener = listener
    }
}
package dev.dextra.newsapp.feature.news.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import dev.dextra.newsapp.R
import dev.dextra.newsapp.api.model.Article
import dev.dextra.newsapp.feature.news.NewsActivity
import kotlinx.android.synthetic.main.item_article.view.*
import java.security.AccessController.getContext
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class ArticleListAdapter(
    context: Context,
    val listener: NewsActivity,
    var articles: MutableList<Article>) : RecyclerView.Adapter<ArticleListAdapter.ViewHolder>() {

    private val dateFormat = SimpleDateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.SHORT)
    private val parseFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(listener.layoutInflater.inflate(R.layout.item_article, parent, false))
    }

    override fun getItemCount(): Int {
        return articles.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val article = articles[position]
        holder.name.text = article.title
        holder.description.text = article.description
        holder.author.text = article.author
        holder.date.text = dateFormat.format(parseFormat.parse(article.publishedAt))
        holder.itemView.setOnClickListener{listener.onClick(article)}
    }

    fun addArticles(articles: List<Article>) {
        this.articles.addAll(articles)
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var name: TextView = itemView.findViewById(R.id.article_name)
        internal var description: TextView = itemView.findViewById(R.id.article_description)
        internal var author: TextView = itemView.findViewById(R.id.article_author)
        internal var date: TextView = itemView.findViewById(R.id.article_date)

    }
}
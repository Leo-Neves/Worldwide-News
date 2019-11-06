package dev.dextra.newsapp.feature.news

import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import dev.dextra.newsapp.R
import dev.dextra.newsapp.api.model.Article
import dev.dextra.newsapp.api.model.Source
import dev.dextra.newsapp.api.repository.NewsRepository
import dev.dextra.newsapp.base.repository.EndpointService
import dev.dextra.newsapp.feature.news.adapter.ArticleListAdapter
import kotlinx.android.synthetic.main.activity_news.*


const val NEWS_ACTIVITY_SOURCE = "NEWS_ACTIVITY_SOURCE"

class NewsActivity : AppCompatActivity() {

    private val newsViewModel = NewsViewModel(NewsRepository(EndpointService()), this)
    private var newsAdapter: ArticleListAdapter = ArticleListAdapter(this, this, ArrayList<Article>())

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_news)

        (intent?.extras?.getSerializable(NEWS_ACTIVITY_SOURCE) as Source).let { source ->
            title = source.name

            loadNews(source)
        }

        super.onCreate(savedInstanceState)
        news_list.setHasFixedSize(true)
        news_list.layoutManager = LinearLayoutManager(this)
        news_list.addOnScrollListener(onUltimoItemScrollListenr)
        news_list.adapter = newsAdapter
    }

    private fun loadNews(source: Source) {
        newsViewModel.configureSource(source)
        newsViewModel.loadNews()
    }

    fun onClick(article: Article) {
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(article.url)
        startActivity(i)
    }

    private var loading: Dialog? = null

    fun showLoading() {
        if (loading == null) {
            loading = Dialog(this)
            loading?.apply {
                requestWindowFeature(Window.FEATURE_NO_TITLE)
                window.setBackgroundDrawableResource(android.R.color.transparent)
                setContentView(R.layout.dialog_loading)
            }
        }
        loading?.show()
    }

    fun hideLoading() {
        loading?.dismiss()
    }

    fun showData(articles: List<Article>) {
        newsAdapter.addArticles(articles)
    }

    fun noMoreDataToLoad() {
        Toast.makeText(this, "No more data to load", Toast.LENGTH_LONG).show()
    }

    private val onUltimoItemScrollListenr = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val linearLayoutManager: LinearLayoutManager = news_list.layoutManager as LinearLayoutManager
            if (linearLayoutManager.findLastCompletelyVisibleItemPosition() == newsAdapter.itemCount-1){
                    newsViewModel.loadNews()
            }
        }
    }
}

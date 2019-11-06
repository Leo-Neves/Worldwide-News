package dev.dextra.newsapp.feature.news

import dev.dextra.newsapp.api.model.Source
import dev.dextra.newsapp.api.repository.NewsRepository
import dev.dextra.newsapp.base.BaseViewModel
import kotlin.math.roundToInt


class NewsViewModel(
    private val newsRepository: NewsRepository,
    private val newsActivity: NewsActivity
) : BaseViewModel() {

    private var source: Source? = null
    private var page: Int = 1
    private var maxPages: Int = 1
    private var isLoading: Boolean = false

    fun configureSource(source: Source) {
        this.source = source
    }

    private fun canLoad(): Boolean{
        return page<=maxPages && !isLoading
    }

    fun loadNews() {
        if (canLoad()) {
            isLoading = true
            newsActivity.showLoading()
            addDisposable(
                newsRepository.getEverything(source!!.id, page).subscribe({ response ->
                    maxPages =
                        if (response.totalResults >= 100) 5 else ((response.totalResults - (response.totalResults % 20)) / 20) + 1
                    page += 1
                    newsActivity.showData(response.articles)
                    newsActivity.hideLoading()
                    isLoading = false
                },
                    {
                        newsActivity.hideLoading()
                        isLoading = false
                    })
            )
        }
        else newsActivity.noMoreDataToLoad()
    }
}

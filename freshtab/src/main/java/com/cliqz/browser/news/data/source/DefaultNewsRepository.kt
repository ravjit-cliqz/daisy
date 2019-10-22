package com.cliqz.browser.news.data.source

import com.cliqz.browser.news.data.NewsItem
import com.cliqz.browser.news.data.Result
import com.cliqz.browser.news.data.Result.Success
import com.cliqz.browser.news.data.source.remote.NewsRemoteDataSource

class DefaultNewsRepository(
    private val newsRemoteDataSource: NewsRemoteDataSource
) : NewsRepository {

    private var cachedNews: List<NewsItem> = emptyList()

    private var lastCachedOn = 0L

    override suspend fun getNews(): Result<List<NewsItem>> {
        // Return with cache if available
        if (cachedNews.isNotEmpty() && !hasCacheExpired()) {
            return Success(cachedNews)
        }
        val newsList = fetchNewsFromRemoteOrLocal()
        (newsList as? Success)?.let { cacheNews(it.data) }
        return Success(cachedNews)
    }

    private suspend fun fetchNewsFromRemoteOrLocal(): Result<List<NewsItem>> {
        // TODO: We can have a local news repository (in db/prefs) populating the newsview
        //  even when app is offline
        return newsRemoteDataSource.getNews()
    }

    private fun cacheNews(newsList: List<NewsItem>) {
        cachedNews = newsList
        lastCachedOn = System.currentTimeMillis()
    }

    fun hasCacheExpired(): Boolean {
        return lastCachedOn != 0L && (System.currentTimeMillis() - lastCachedOn) > CACHE_PERIOD
    }

    companion object {
        const val CACHE_PERIOD = 30 * 60 * 1000L // 30 minutes

        // For singleton instantiation
        @Volatile
        private var instance: NewsRepository? = null

        fun getInstance(newsRemoteDataSource: NewsRemoteDataSource) =
            instance ?: synchronized(this) {
                instance ?: DefaultNewsRepository(newsRemoteDataSource).also { instance = it }
            }
    }
}

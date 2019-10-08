package org.mozilla.reference.browser.freshtab.data.source

import org.mozilla.reference.browser.freshtab.data.NewsItem

class DefaultNewsRepository(private val newsRemoteDataSource: NewsRemoteDataSource) : NewsRepository {

    private var cachedNews: MutableList<NewsItem>? = null

    override suspend fun getNews(forceUpdate: Boolean): List<NewsItem> {
        // TODO: Logic to do force update after some time interval

        // Return with cache if available
        if (cachedNews != null) {
            return cachedNews as List<NewsItem>
        }
        val news = fetchNewsFromRemoteOrLocal()
        cacheNews(news)
        return cachedNews as List<NewsItem>
    }

    private suspend fun fetchNewsFromRemoteOrLocal(): List<NewsItem> {
        // TODO: We can have a local news repository (in db/prefs) populating the newsview
        //  even when app is offline
        return newsRemoteDataSource.getNews()
    }

    private fun cacheNews(newsList: List<NewsItem>) {
        cachedNews = newsList.toMutableList()
    }

    companion object {
        // For singleton instantiation
        @Volatile
        private var instance: NewsRepository? = null

        fun getInstance(newsRemoteDataSource: NewsRemoteDataSource) =
                instance
                        ?: synchronized(this) {
                    instance
                            ?: DefaultNewsRepository(newsRemoteDataSource).also { instance = it }
                }
    }
}

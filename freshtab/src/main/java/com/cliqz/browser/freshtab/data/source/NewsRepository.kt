package com.cliqz.browser.freshtab.data.source

import com.cliqz.browser.freshtab.data.NewsItem
import com.cliqz.browser.freshtab.data.Result

interface NewsRepository {

    suspend fun getNews(forceUpdate: Boolean = false): Result<List<NewsItem>>
}

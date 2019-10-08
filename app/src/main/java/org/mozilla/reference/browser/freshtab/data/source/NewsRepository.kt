package org.mozilla.reference.browser.freshtab.data.source

import org.mozilla.reference.browser.freshtab.data.NewsItem

interface NewsRepository {

    suspend fun getNews(forceUpdate: Boolean = false): List<NewsItem>
}

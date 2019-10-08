package org.mozilla.reference.browser.freshtab.data.source

import org.mozilla.reference.browser.freshtab.data.NewsItem

interface NewsDataSource {
    suspend fun getNews(): List<NewsItem>
}

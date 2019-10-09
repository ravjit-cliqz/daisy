package org.mozilla.reference.browser.freshtab.data.source

import org.mozilla.reference.browser.freshtab.data.NewsItem
import org.mozilla.reference.browser.freshtab.data.Result

interface NewsDataSource {
    suspend fun getNews(): Result<List<NewsItem>>
}

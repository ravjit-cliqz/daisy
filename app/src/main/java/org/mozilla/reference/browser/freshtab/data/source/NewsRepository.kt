package org.mozilla.reference.browser.freshtab.data.source

import org.mozilla.reference.browser.freshtab.data.NewsItem
import org.mozilla.reference.browser.freshtab.data.Result

interface NewsRepository {

    suspend fun getNews(forceUpdate: Boolean = false): Result<List<NewsItem>>
}

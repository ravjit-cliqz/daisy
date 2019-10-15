package com.cliqz.browser.freshtab.data.source

import com.cliqz.browser.freshtab.data.NewsItem
import com.cliqz.browser.freshtab.data.Result

interface NewsDataSource {
    suspend fun getNews(): Result<List<NewsItem>>
}

package com.cliqz.browser.freshtab.domain

import com.cliqz.browser.freshtab.data.NewsItem
import com.cliqz.browser.freshtab.data.Result
import com.cliqz.browser.freshtab.data.source.NewsRepository

class GetNewsUseCase(private val newsRepository: NewsRepository) {

    suspend operator fun invoke(): Result<List<NewsItem>> {
        return newsRepository.getNews()
    }
}

package org.mozilla.reference.browser.freshtab.domain

import org.mozilla.reference.browser.freshtab.data.NewsItem
import org.mozilla.reference.browser.freshtab.data.Result
import org.mozilla.reference.browser.freshtab.data.source.NewsRepository

class GetNewsUseCase(private val newsRepository: NewsRepository) {

    suspend operator fun invoke(): Result<List<NewsItem>> {
        return newsRepository.getNews()
    }
}

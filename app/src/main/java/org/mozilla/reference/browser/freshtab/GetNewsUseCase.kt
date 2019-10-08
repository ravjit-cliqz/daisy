package org.mozilla.reference.browser.freshtab

import org.mozilla.reference.browser.freshtab.data.NewsItem
import org.mozilla.reference.browser.freshtab.data.source.NewsRepository

class GetNewsUseCase(private val newsRepository: NewsRepository) {

    suspend operator fun invoke(): List<NewsItem> {
        return newsRepository.getNews()
    }
}

package org.mozilla.reference.browser.freshtab

import mozilla.components.feature.session.SessionUseCases
import org.mozilla.reference.browser.freshtab.data.NewsItem
import org.mozilla.reference.browser.freshtab.data.Result
import org.mozilla.reference.browser.freshtab.domain.GetNewsUseCase

class NewsInteractor(
    private val loadUrlUseCase: SessionUseCases.LoadUrlUseCase,
    private val getNewsUseCase: GetNewsUseCase,
    private val newsView: NewsView,
    private val onNewsItemSelected: () -> Unit
) : NewsViewInteractor {

    fun start() {
        newsView.interactor = this
    }

    fun stop() {
        newsView.interactor = null
    }

    override suspend fun getNews(): Result<List<NewsItem>> {
        return getNewsUseCase.invoke()
    }

    override fun onOpenInNormalTab(item: NewsItem) {
        loadUrlUseCase.invoke(item.url)
        onNewsItemSelected()
    }

    override fun onOpenInNewNormalTab(item: NewsItem) {
        TODO("not implemented")
    }

    override fun onOpenInPrivateTab(item: NewsItem) {
        TODO("not implemented")
    }
}

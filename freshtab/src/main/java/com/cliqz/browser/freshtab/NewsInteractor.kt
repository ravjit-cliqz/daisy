package com.cliqz.browser.freshtab

import com.cliqz.browser.freshtab.data.NewsItem
import com.cliqz.browser.freshtab.data.Result
import com.cliqz.browser.freshtab.domain.GetNewsUseCase
import mozilla.components.feature.session.SessionUseCases

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

package com.cliqz.browser.freshtab

import android.widget.ImageView
import com.cliqz.browser.freshtab.data.NewsItem
import com.cliqz.browser.freshtab.data.Result
import com.cliqz.browser.freshtab.domain.GetNewsUseCase
import mozilla.components.feature.session.SessionUseCases.LoadUrlUseCase

class NewsInteractor(
    private val newsView: NewsView,
    private val loadUrlUseCase: LoadUrlUseCase,
    private val getNewsUseCase: GetNewsUseCase,
    private val onNewsItemSelected: (() -> Unit)? = null,
    private val loadNewsItemIcon: ((view: ImageView, url: String) -> Unit)? = null
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
        onNewsItemSelected?.invoke()
    }

    override fun onOpenInNewNormalTab(item: NewsItem) {
        TODO("not implemented")
    }

    override fun onOpenInPrivateTab(item: NewsItem) {
        TODO("not implemented")
    }

    override fun loadNewsItemIcon(view: ImageView, url: String) {
        loadNewsItemIcon?.invoke(view, url)
    }

}

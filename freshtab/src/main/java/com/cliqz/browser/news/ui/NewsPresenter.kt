package com.cliqz.browser.news.ui

import android.content.Context
import android.content.SharedPreferences
import android.widget.ImageView
import com.cliqz.browser.news.data.NewsItem
import com.cliqz.browser.news.data.Result
import com.cliqz.browser.news.domain.GetNewsUseCase
import mozilla.components.feature.session.SessionUseCases.LoadUrlUseCase

class NewsPresenter(
    private val context: Context,
    private val newsView: NewsView,
    private val loadUrlUseCase: LoadUrlUseCase,
    private val getNewsUseCase: GetNewsUseCase,
    private val onNewsItemSelected: (() -> Unit)? = null,
    private val loadNewsItemIcon: ((view: ImageView, url: String) -> Unit)? = null
) : Presenter {

    override var isNewsViewExpanded: Boolean
        get() = isNewsViewExpanded(context)
        set(value) {
            setNewsViewExpanded(context, value)
        }

    fun start() {
        newsView.presenter = this
    }

    fun stop() {
        newsView.presenter = null
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

    private fun isNewsViewExpanded(context: Context): Boolean {
        return preferences(context).getBoolean(PREF_NEWS_EXPANDED, false)
    }

    private fun setNewsViewExpanded(context: Context, value: Boolean) {
        preferences(context).edit().putBoolean(PREF_NEWS_EXPANDED, value).apply()
    }

    private fun preferences(context: Context): SharedPreferences =
        context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)

    companion object {
        internal const val PREFERENCE_NAME = "news_feature"
        internal const val PREF_NEWS_EXPANDED = "news_expanded"
    }

}

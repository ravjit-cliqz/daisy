package com.cliqz.browser.news.ui

import android.content.Context
import android.widget.ImageView
import androidx.annotation.VisibleForTesting
import com.cliqz.browser.news.domain.GetNewsUseCase
import kotlinx.coroutines.CoroutineScope
import mozilla.components.feature.session.SessionUseCases.LoadUrlUseCase
import mozilla.components.support.base.feature.LifecycleAwareFeature

class NewsFeature(
    context: Context,
    newsView: NewsView,
    scope: CoroutineScope,
    loadUrlUseCase: LoadUrlUseCase,
    newsUseCase: GetNewsUseCase,
    onNewsItemSelected: (() -> Unit)? = null,
    loadNewsItemIcon: ((view: ImageView, url: String) -> Unit)? = null
) : LifecycleAwareFeature {

    @VisibleForTesting
    internal var presenter = DefaultNewsPresenter(
        context,
        newsView,
        scope,
        loadUrlUseCase,
        newsUseCase,
        onNewsItemSelected,
        loadNewsItemIcon
    )

    override fun start() {
        presenter.start()
    }

    override fun stop() {
        presenter.stop()
    }
}

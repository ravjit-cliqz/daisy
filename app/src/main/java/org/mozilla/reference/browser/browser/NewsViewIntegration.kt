package org.mozilla.reference.browser.browser

import android.view.View
import android.widget.ImageView
import androidx.lifecycle.LifecycleCoroutineScope
import com.cliqz.browser.freshtab.FreshTab
import com.cliqz.browser.freshtab.NewsFeature
import com.cliqz.browser.freshtab.NewsView
import com.cliqz.browser.freshtab.domain.GetNewsUseCase
import kotlinx.coroutines.cancel
import mozilla.components.browser.icons.BrowserIcons
import mozilla.components.browser.icons.IconRequest
import mozilla.components.concept.engine.EngineView
import mozilla.components.feature.session.SessionUseCases
import mozilla.components.support.base.feature.LifecycleAwareFeature

class NewsViewIntegration(
    newsView: NewsView,
    private val freshTab: FreshTab,
    private val engineView: EngineView,
    private val scope: LifecycleCoroutineScope,
    loadUrlUseCase: SessionUseCases.LoadUrlUseCase,
    newsUseCase: GetNewsUseCase,
    private val icons: BrowserIcons
) : LifecycleAwareFeature {

    private val feature = NewsFeature(
        newsView,
        scope,
        loadUrlUseCase,
        newsUseCase,
        ::onNewsItemSelected,
        ::loadNewsItemIcon)

    override fun start() {
        feature.start()
    }

    override fun stop() {
        feature.stop()
        scope.cancel()
    }

    private fun onNewsItemSelected() {
        freshTab.visibility = View.GONE
        engineView.asView().visibility = View.VISIBLE
    }

    private fun loadNewsItemIcon(view: ImageView, url: String) {
        icons.loadIntoView(view, IconRequest(url))
    }
}

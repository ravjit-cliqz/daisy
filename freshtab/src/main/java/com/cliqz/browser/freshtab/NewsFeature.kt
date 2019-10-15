package com.cliqz.browser.freshtab

import android.widget.ImageView
import androidx.lifecycle.LifecycleCoroutineScope
import com.cliqz.browser.freshtab.data.Result.Success
import com.cliqz.browser.freshtab.domain.GetNewsUseCase
import kotlinx.coroutines.cancel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import mozilla.components.feature.session.SessionUseCases
import mozilla.components.support.base.feature.LifecycleAwareFeature

class NewsFeature(
    private val newsView: NewsView,
    private val scope: LifecycleCoroutineScope,
    loadUrlUseCase: SessionUseCases.LoadUrlUseCase,
    newsUseCase: GetNewsUseCase,
    onNewsItemSelected: () -> Unit,
    loadIcon: (view: ImageView, url: String) -> Unit
) : LifecycleAwareFeature {

    private var interactor = NewsInteractor(
            loadUrlUseCase,
            newsUseCase,
            newsView,
            onNewsItemSelected,
            loadIcon
    )

    override fun start() {
        interactor.start()
        scope.launchWhenStarted {
            val result = withContext(Dispatchers.IO) {
                interactor.getNews()
            }
            withContext(Dispatchers.Main) {
                if (result is Success) {
                    newsView.displayNews(result.data)
                } else {
                    newsView.hideNews()
                }
            }
        }
    }

    override fun stop() {
        interactor.stop()
        scope.cancel()
    }
}

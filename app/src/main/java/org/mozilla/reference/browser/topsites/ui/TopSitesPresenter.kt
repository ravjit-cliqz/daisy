package org.mozilla.reference.browser.topsites.ui

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import mozilla.components.feature.session.SessionUseCases.LoadUrlUseCase
import org.mozilla.reference.browser.history.usecases.HistoryUseCases
import org.mozilla.reference.browser.topsites.data.TopSite

/**
 * @author Ravjit Uppal
 */
class TopSitesPresenter(
    private val view: View,
    private val loadUrlUseCase: LoadUrlUseCase,
    private val topSitesUseCase: HistoryUseCases.GetTopSitesUseCase
) {

    interface View {
        fun updateTopSitesData(topSites: List<TopSite>)
    }

    init {
        onCreate()
    }

    fun onCreate() = GlobalScope.launch(Dispatchers.Main) {
        val historyInfo = withContext(Dispatchers.IO) { topSitesUseCase.invoke() }
        view?.updateTopSitesData(historyInfo)
    }

    fun onTopSiteClicked(url: String) {
        loadUrlUseCase.invoke(url)
    }
}
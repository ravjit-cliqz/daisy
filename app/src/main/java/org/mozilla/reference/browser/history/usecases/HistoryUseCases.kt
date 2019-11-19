package org.mozilla.reference.browser.history.usecases

import mozilla.components.concept.storage.VisitInfo
import org.mozilla.reference.browser.browser.CliqzHistoryStorage
import org.mozilla.reference.browser.topsites.data.TopSite

/**
 * @author Ravjit Uppal
 */
class HistoryUseCases(historyStorage: CliqzHistoryStorage) {

    class GetHistoryUseCase(private val historyStorage: CliqzHistoryStorage) {
        suspend operator fun invoke(): List<VisitInfo> {
            return historyStorage.getDetailedVisits(0)
        }
    }

    class GetTopSitesUseCase(private val historyStorage: CliqzHistoryStorage) {
        suspend operator fun invoke(): List<TopSite> {
            return historyStorage.getTopSites()
        }
    }

    val getHistory: GetHistoryUseCase by lazy { GetHistoryUseCase(historyStorage) }
    val getTopSites: GetTopSitesUseCase by lazy {GetTopSitesUseCase(historyStorage)}
}
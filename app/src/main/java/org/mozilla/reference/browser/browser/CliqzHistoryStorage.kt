package org.mozilla.reference.browser.browser

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import mozilla.components.browser.storage.sync.PlacesHistoryStorage
import org.mozilla.reference.browser.topsites.data.TopSite

/**
 * @author Ravjit Uppal
 */
class CliqzHistoryStorage(private val context: Context) : PlacesHistoryStorage(context) {

    suspend fun getTopSites() : List<TopSite> {
        val topsitesList = mutableListOf<TopSite>()
        val placesDb = SQLiteDatabase.openDatabase(context.filesDir.absolutePath + "/places.sqlite",
            null, 0)
        val topsitesCursor = placesDb.rawQuery(
            "SELECT id, url, title FROM moz_places WHERE title NOT NULL ORDER BY visit_count_local DESC LIMIT 5", null)
        if (topsitesCursor.moveToFirst()) {
            val idColoumnIndex = topsitesCursor.getColumnIndex("id")
            val urlColoumnIndex = topsitesCursor.getColumnIndex("url")
            val titleColoumnIndex = topsitesCursor.getColumnIndex("title")
            do {
                val id = topsitesCursor.getLong(idColoumnIndex)
                val url = topsitesCursor.getString(urlColoumnIndex)
                val title = topsitesCursor.getString(titleColoumnIndex)
                topsitesList.add(TopSite(id, url, url, title))
            } while(topsitesCursor.moveToNext())
        }
        return topsitesList
    }
}
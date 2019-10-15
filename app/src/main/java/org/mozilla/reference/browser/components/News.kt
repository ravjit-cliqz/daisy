package org.mozilla.reference.browser.components

import com.cliqz.browser.freshtab.data.source.DefaultNewsRepository
import com.cliqz.browser.freshtab.data.source.remote.NewsRemoteDataSource
import mozilla.components.concept.fetch.Client

class News(private val client: Client) {

    val newsRepository by lazy {
        val newsRemoteDataSource = NewsRemoteDataSource(client)
        DefaultNewsRepository.getInstance(newsRemoteDataSource)
    }
}

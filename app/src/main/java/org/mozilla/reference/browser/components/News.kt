package org.mozilla.reference.browser.components

import mozilla.components.concept.fetch.Client
import org.mozilla.reference.browser.freshtab.data.source.DefaultNewsRepository
import org.mozilla.reference.browser.freshtab.data.source.remote.NewsRemoteDataSource

class News(private val client: Client) {

    val newsRepository by lazy {
        val newsRemoteDataSource = NewsRemoteDataSource(client)
        DefaultNewsRepository.getInstance(newsRemoteDataSource)
    }
}

package org.mozilla.reference.browser.freshtab.data.source

import mozilla.components.concept.fetch.Client
import mozilla.components.concept.fetch.Headers
import mozilla.components.concept.fetch.isSuccess
import mozilla.components.concept.fetch.MutableHeaders
import mozilla.components.concept.fetch.Request
import mozilla.components.concept.fetch.Response
import org.json.JSONException
import org.json.JSONObject
import org.json.JSONTokener
import org.mozilla.reference.browser.freshtab.data.NewsItem
import java.util.Locale
import kotlin.collections.ArrayList

class NewsRemoteDataSource(private val client: Client) : NewsDataSource {

    override suspend fun getNews(): List<NewsItem> {
        val url = getNewsUrl()
        val headers = MutableHeaders(
                Headers.Names.CONTENT_TYPE to CONTENT_TYPE_JSON
        )
        val request = Request(
                url = url,
                method = Request.Method.PUT,
                headers = headers,
                body = Request.Body.fromString(NEWS_PAYLOAD)
        )

        val response = client.fetch(request)
        if (!response.isSuccess) {
            throw RuntimeException("Error fetching news. Response code:${response.status}")
        }
        return response.toNewsList()
    }

    private fun getNewsUrl(): String {
        val locale = Locale.getDefault().toString().replace("_", "-")
        val parts = locale.split("-".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        var lang: String? = null
        var newsEdition = "intl"
        if (parts.isNotEmpty()) {
            lang = parts[0].toLowerCase()
        }
        val sb = StringBuilder(NEWS_URL)
        sb.append("&locale=").append(locale)
        if (lang != null) {
            sb.append("&lang=").append(lang)
            if (lang == Locale.GERMAN.language || lang == Locale.FRENCH.language) {
                newsEdition = lang
            }
        }
        sb.append("&edition=").append(newsEdition)

        /* TODO: Get country based result.
        val country: String?
        country = preferenceManager.getCountryChoice().countryCode
        if (country != null) {
            sb.append("&country=").append(country)
        } */
        sb.append("&count=").append(Int.MAX_VALUE)
        sb.append("&platform=1")
        /* TODO: Get location based result.
        if (locationCache.getLastLocation() != null) {
            sb.append("&loc=").append(locationCache.getLastLocation().getLatitude()).append(",")
                    .append(locationCache.getLastLocation().getLongitude())
        }*/
        return sb.toString()
    }

    companion object {
        private const val CONTENT_TYPE_JSON = "application/json"
        private const val NEWS_PAYLOAD =
                "{\"q\":\"\",\"results\":[{\"url\":\"rotated-top-news.cliqz.com\",\"snippet\":{}}]}"
        private const val NEWS_URL = "https://api.cliqz.com/api/v2/rich-header?path=/v2/map"
    }
}

private fun Response.toNewsList(): List<NewsItem> {
    val responseBody = use { body.string() }
    val result = responseBody.run {
        JSONTokener(responseBody).nextValue() as JSONObject
    }
    val newsList = ArrayList<NewsItem>()
    try {
        val data = result.getJSONArray("results").getJSONObject(0)
                .getJSONObject("snippet").getJSONObject("extra")
        val articles = data.getJSONArray("articles")
        for (i in 0 until articles.length()) {
            try {
                val article = articles.getJSONObject(i)

                val url = article.optString("url", "")
                val title = article.optString("title", "")
                val description = article.optString("description", "")
                val domain = article.optString("domain", "")
                val shortTitle = article.optString("short_title", "")
                val media = article.optString("media", "")
                val breaking = article.optBoolean("breaking", false)
                val breakingLabel = article.optString("breaking_label", "")
                val isLocalNews = article.has("local_news")
                val localLabel = article.optString("local_label", "")
                newsList.add(NewsItem(url, title, description, domain, shortTitle, media,
                        breakingLabel, breaking, isLocalNews, localLabel))
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return newsList
}

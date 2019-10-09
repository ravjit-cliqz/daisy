package org.mozilla.reference.browser.freshtab

import android.content.Context
import android.graphics.Color
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import org.mozilla.reference.browser.R
import org.mozilla.reference.browser.freshtab.data.NewsItem
import org.mozilla.reference.browser.freshtab.data.Result

class NewsView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    val view = LayoutInflater.from(context)
            .inflate(R.layout.news_layout, this, true)

    var topNewsListView = view.findViewById<LinearLayout>(R.id.topnews_list)

    var interactor: NewsViewInteractor? = null

    fun displayNews(newsList: List<NewsItem>) {
        if (newsList.isNullOrEmpty()) {
            return
        }
        topNewsListView.removeAllViews()
        val inflater = LayoutInflater.from(context)
        for (newsItem in newsList) {
            val view = inflater.inflate(R.layout.news_item_layout, topNewsListView, false)
            interactor?.let {
                val holder = NewsItemViewHolder(view, it)
                holder.bind(newsItem)
                holder.titleView.text = buildTitleSpannable(newsItem)
                holder.urlView.text = newsItem.domain
            }
            topNewsListView.addView(view)
        }
    }

    fun hideNews() {
        this.visibility = View.GONE
    }

    private fun buildTitleSpannable(newsItem: NewsItem): CharSequence {
        val builder = SpannableStringBuilder()
        if (newsItem.breaking && newsItem.breakingLabel != null && !newsItem.breakingLabel.isEmpty()) {
            appendLabel(builder, newsItem.breakingLabel.toUpperCase(), Color.RED)
        }
        if (newsItem.isLocalNews && newsItem.localLabel != null && !newsItem.localLabel.isEmpty()) {
            // TODO: Change Color
            @ColorInt val color = ContextCompat.getColor(context, R.color.textColorPrimary)
            appendLabel(builder, newsItem.localLabel.toUpperCase(), color)
        }
        builder.append(newsItem.title)
        return builder
    }

    private fun appendLabel(builder: SpannableStringBuilder, str: String, @ColorInt color: Int) {
        val oldLen = builder.length
        builder.append(str).append(": ")
        builder.setSpan(ForegroundColorSpan(color), oldLen, builder.length,
                Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
    }
}

interface NewsViewInteractor {

    suspend fun getNews(): Result<List<NewsItem>>

    fun onOpenInNormalTab(item: NewsItem)

    fun onOpenInNewNormalTab(item: NewsItem)

    fun onOpenInPrivateTab(item: NewsItem)
}

package com.cliqz.browser.news.ui

import android.content.Context
import android.graphics.Color
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.cliqz.browser.freshtab.R
import com.cliqz.browser.news.data.NewsItem

class NewsItemViewHolder(itemView: View, private val presenter: Presenter)
    : RecyclerView.ViewHolder(itemView), View.OnClickListener, View.OnLongClickListener {

    override fun onLongClick(v: View?): Boolean {
        return true
    }

    override fun onClick(v: View?) {
        newsItem?.let { presenter.onOpenInNormalTab(it) }
    }

    private val context: Context = itemView.context
    private val iconView: ImageView = itemView.findViewById(R.id.icon_view)

    val urlView: TextView = itemView.findViewById(R.id.url_view)
    val titleView: TextView = itemView.findViewById(R.id.title_view)

    private var newsItem: NewsItem? = null

    init {
        itemView.setOnClickListener(this)
        itemView.setOnLongClickListener(this)
    }

    fun bind(newsItem: NewsItem) {
        this.newsItem = newsItem
        titleView.text = buildTitleSpannable(newsItem)
        urlView.text = newsItem.domain
        presenter.loadNewsItemIcon(iconView, newsItem.url)
    }

    private fun buildTitleSpannable(newsItem: NewsItem): CharSequence {
        val builder = SpannableStringBuilder()
        if (newsItem.breaking && !newsItem.breakingLabel.isNullOrBlank()) {
            appendLabel(builder, newsItem.breakingLabel.toUpperCase(), Color.RED)
        }
        if (newsItem.isLocalNews && !newsItem.localLabel.isNullOrBlank()) {
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

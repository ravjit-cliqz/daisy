package com.cliqz.browser.freshtab

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cliqz.browser.freshtab.data.NewsItem

class NewsItemViewHolder(itemView: View, private val interactor: NewsViewInteractor)
    : RecyclerView.ViewHolder(itemView), View.OnClickListener, View.OnLongClickListener {

    override fun onLongClick(v: View?): Boolean {
        return true
    }

    override fun onClick(v: View?) {
        newsItem?.let { interactor.onOpenInNormalTab(it) }
    }

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
        interactor.loadNewsItemIcon(iconView, newsItem.url)
    }
}

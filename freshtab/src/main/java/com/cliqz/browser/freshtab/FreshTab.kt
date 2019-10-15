package com.cliqz.browser.freshtab

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ScrollView

class FreshTab @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ScrollView(context, attrs, defStyleAttr) {

    private var view: LinearLayout = LinearLayout(context)

    init {
        view.orientation = LinearLayout.VERTICAL
        addView(view)
    }

    override fun addView(child: View?, params: ViewGroup.LayoutParams?) {
        view.addView(child, params)
    }
}

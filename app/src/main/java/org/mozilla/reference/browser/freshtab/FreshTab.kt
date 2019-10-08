package org.mozilla.reference.browser.freshtab

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import org.mozilla.reference.browser.R

class FreshTab @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    init {
        LayoutInflater.from(context).inflate(R.layout.freshtab_layout, this, true)
        orientation = VERTICAL
    }
}

package com.wuliner.canvas

import android.view.View

fun View.dp2px(dp: Int): Float {
    return dp*resources.displayMetrics.density.toFloat()
}
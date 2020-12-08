package com.hieupt.android.standalonescrollbar.view

/**
 * Created by HieuPT on 12/8/2020.
 */
fun interface OnScrollChangedListener {
    fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int)
}
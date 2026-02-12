package com.hieupt.android.standalonescrollbar

import java.util.SortedMap

/**
 * Created by HieuPT on 2/13/2026.
 */
fun <K, V> SortedMap<K, V>.removeIf(predicate: (Map.Entry<K, V>) -> Boolean) {
    val iterator = entries.iterator()
    while (iterator.hasNext()) {
        if (predicate(iterator.next())) {
            iterator.remove()
        }
    }
}
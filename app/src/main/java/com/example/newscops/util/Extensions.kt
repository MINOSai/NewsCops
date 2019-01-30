package com.example.newscops.util

import android.content.Context
import android.net.Uri
import android.view.View
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.GONE
}

fun Context.openUrl(url: String) {
    val builder = CustomTabsIntent.Builder()
    val customTabIntent = builder.build()
    customTabIntent.launchUrl(this, Uri.parse(url))
}
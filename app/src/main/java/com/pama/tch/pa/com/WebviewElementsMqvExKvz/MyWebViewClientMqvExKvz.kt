package com.pama.tch.pa.com.WebviewElementsMqvExKvz

import android.content.Intent
import android.util.Log
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.pama.tch.pa.com.UtilsMqvExKvz.SharedPrefUrlMqvExKvz

class MyWebViewClientMqvExKvz : WebViewClient() {



    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
        val prohibitedLinks1MqvExKvz = listOf("mailto:", "tel:")
        val prohibitedLinks2MqvExKvz = listOf("vk.com", "facebook", "twitter")
        val urlMqvExKvz = request?.url.toString()
        return when {

            prohibitedLinks1MqvExKvz.find { urlMqvExKvz.startsWith(it) } != null -> {
                view?.context?.startActivity(Intent(Intent.ACTION_VIEW, request?.url))
                true
            }

            prohibitedLinks2MqvExKvz.find { urlMqvExKvz.contains(it) } != null -> {
                Log.d("CustomWebClient", "Client provided with prohibited url:  $urlMqvExKvz")
                true

            }

            else -> false

        }
    }

    override fun onPageFinished(view: WebView?, url: String?) {
        super.onPageFinished(view, url)
        val sharedPrefUrlMqvExKvz = SharedPrefUrlMqvExKvz(view?.context ?: return)
        sharedPrefUrlMqvExKvz.lastPageMqvExKvz = url.toString()
    }
}
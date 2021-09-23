package com.pama.tch.pa.com.WebviewElementsOfr

import android.content.Intent
import android.util.Log
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.pama.tch.pa.com.UtilsOfr.SharedPrefUrlOfr

class MyWebViewClientOfr : WebViewClient() {



    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
        val prohibitedLinks1Ofr = listOf("mailto:", "tel:")
        val prohibitedLinks2Ofr = listOf("instagram", "facebook", "twitter")
        val urlOfr = request?.url.toString()
        return when {
            prohibitedLinks1Ofr.find { urlOfr.startsWith(it) } != null -> {
                view?.context?.startActivity(Intent(Intent.ACTION_VIEW, request?.url))
                true
            }
            prohibitedLinks2Ofr.find { urlOfr.contains(it) } != null -> {
                Log.d("CustomWebClient", "Client provided with prohibited url:  $urlOfr")
                true

            }
            else -> false
        }
    }

    override fun onPageFinished(view: WebView?, url: String?) {
        super.onPageFinished(view, url)
        val sharedPrefUrlOfr = SharedPrefUrlOfr(view?.context ?: return)
        sharedPrefUrlOfr.lastPage = url.toString()
    }
}
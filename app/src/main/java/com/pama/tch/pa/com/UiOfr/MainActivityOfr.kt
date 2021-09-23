package com.pama.tch.pa.com.UiOfr

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.icu.text.SimpleDateFormat
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.webkit.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.pama.tch.pa.com.InternetServiceOfr.InternetServiceOfr
import com.pama.tch.pa.com.R
import com.pama.tch.pa.com.UtilsOfr.SharedPrefUrlOfr
import com.pama.tch.pa.com.UtilsOfr.checkPermissionsOfr
import com.pama.tch.pa.com.WebviewElementsOfr.MyWebViewClientOfr
import java.io.File
import java.util.*

class MainActivityOfr : AppCompatActivity() {

    private lateinit var myWebViewOfr: WebView
    private var mFilePathCallbackOfr: ValueCallback<Array<Uri>>? = null
    private var uriViewOfr: Uri = Uri.EMPTY
    private var internetDialogOfr: InternetDialogOfr? = null
    private lateinit var sharedPrefUrlOfr: SharedPrefUrlOfr



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_ofr)
        myWebViewOfr = findViewById(R.id.wv_main_ofr)
        sharedPrefUrlOfr = SharedPrefUrlOfr(applicationContext)
        setUpMyWebViewOfr()
        val swipeRefreshLayout = findViewById<SwipeRefreshLayout>(R.id.swipe_refresh_layout_main_ofr)
        swipeRefreshLayout.setOnRefreshListener {
            myWebViewOfr.loadUrl(myWebViewOfr.url ?: return@setOnRefreshListener)
            swipeRefreshLayout.isRefreshing = false
        }
        startService(Intent(this, InternetServiceOfr::class.java))
    }

    private fun setUpMyWebViewOfr() {
        val cookiesManagerOfr = CookieManager.getInstance()
        CookieManager.setAcceptFileSchemeCookies(true)
        cookiesManagerOfr.setAcceptThirdPartyCookies(myWebViewOfr, true)

        myWebViewOfr.scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY

        myWebViewOfr.settings.run {
            domStorageEnabled = true
            defaultTextEncodingName = "utf-8"
            cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
            builtInZoomControls = true
            mediaPlaybackRequiresUserGesture = false
            javaScriptEnabled = true
            displayZoomControls = false
            useWideViewPort = true
            builtInZoomControls = true

        }

        myWebViewOfr.webViewClient = MyWebViewClientOfr()
        myWebViewOfr.webChromeClient = object : WebChromeClient() {
            override fun onShowFileChooser(
                webView: WebView?,
                filePathCallback: ValueCallback<Array<Uri>>?,
                fileChooserParams: FileChooserParams?
            ): Boolean {
                this@MainActivityOfr.checkPermissionsOfr()
                mFilePathCallbackOfr = filePathCallback
                val capIntentOfr = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                if (capIntentOfr.resolveActivity(this@MainActivityOfr.packageManager) != null) {
                    val providedFileOfr: File = createTempFileOfr()
                    uriViewOfr = FileProvider.getUriForFile(
                        this@MainActivityOfr,
                        "${this@MainActivityOfr.application.packageName}.provider",
                        providedFileOfr
                    )
                    capIntentOfr.apply {
                        putExtra(MediaStore.EXTRA_OUTPUT, uriViewOfr)
                        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                    }
                    val actionIntentOfr = Intent(Intent.ACTION_GET_CONTENT).apply {
                        addCategory(Intent.CATEGORY_OPENABLE)
                        type = "image/*"
                    }
                    val intentChooserOfr = Intent(Intent.ACTION_CHOOSER).apply {
                        putExtra(Intent.EXTRA_INTENT, capIntentOfr)
                        putExtra(Intent.EXTRA_INITIAL_INTENTS, arrayOf(actionIntentOfr))
                    }
                    startActivityForResult(intentChooserOfr, 0)
                    return true

                }
                return super.onShowFileChooser(webView, filePathCallback, fileChooserParams)
            }
        }
        if (sharedPrefUrlOfr.lastPage != null && sharedPrefUrlOfr.lastPage != "") {
            myWebViewOfr.loadUrl(sharedPrefUrlOfr.lastPage!!)
        } else {
            myWebViewOfr.loadUrl(sharedPrefUrlOfr.binomUrlOfr)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 0) {
            mFilePathCallbackOfr?.let {
                val uriResultOfr = if (data == null || resultCode != RESULT_OK) null else data.data
                if (uriResultOfr != null) {
                    mFilePathCallbackOfr?.onReceiveValue(arrayOf(uriResultOfr))
                } else {
                    mFilePathCallbackOfr?.onReceiveValue(arrayOf(uriViewOfr))
                }
                mFilePathCallbackOfr = null
            }
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun onBackPressed() {
        if (myWebViewOfr.canGoBack()) {
            myWebViewOfr.goBack()
        } else {
            super.onBackPressed()
        }
    }

    private fun createTempFileOfr(): File {
        val dateOfr = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val fileDirOfr = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile("TMP${dateOfr}_Ofr", ".jpg", fileDirOfr)

    }

    override fun onResume() {
        registerReceiver(myBroadcastReceiverOfr, IntentFilter()
            .apply { addAction(InternetServiceOfr.INTERNET_CHECK_OFR) })
        super.onResume()
    }

    override fun onPause() {
        internetDialogOfr?.dismiss()
        unregisterReceiver(myBroadcastReceiverOfr)
        super.onPause()
    }

    private val myBroadcastReceiverOfr = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent?.action == InternetServiceOfr.INTERNET_CHECK_OFR) {
                if (intent.getBooleanExtra(InternetServiceOfr.INTERNET_CHECK_OFR, true)) {
                    internetDialogOfr?.dismiss()
                    internetDialogOfr = null
                } else {
                    if (internetDialogOfr == null) {
                        internetDialogOfr = InternetDialogOfr().apply {
                            show(
                                supportFragmentManager ,
                                "Internet_Dialog_Ofr"
                            )
                        }
                    }
                }
            }
        }
    }
}
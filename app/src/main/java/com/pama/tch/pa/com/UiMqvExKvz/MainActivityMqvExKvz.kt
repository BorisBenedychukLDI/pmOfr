package com.pama.tch.pa.com.UiMqvExKvz

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
import com.pama.tch.pa.com.InternetServiceMqvExKvz.InternetServiceMqvExKvz
import com.pama.tch.pa.com.R
import com.pama.tch.pa.com.UtilsMqvExKvz.SharedPrefUrlMqvExKvz
import com.pama.tch.pa.com.UtilsMqvExKvz.checkPermissionsMqvExKvz
import com.pama.tch.pa.com.WebviewElementsMqvExKvz.MyWebViewClientMqvExKvz
import java.io.File
import java.util.*

class MainActivityMqvExKvz : AppCompatActivity() {

    private lateinit var myWebViewMqvExKvz: WebView
    private var mFilePathCallbackMqvExKvz: ValueCallback<Array<Uri>>? = null
    private var uriViewMqvExKvz: Uri = Uri.EMPTY
    private var internetDialogMqvExKvz: InternetDialogMqvExKvz? = null
    private lateinit var sharedPrefUrlMqvExKvz: SharedPrefUrlMqvExKvz


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_mqvexkvz)
        myWebViewMqvExKvz = findViewById(R.id.wv_main_mqvexkvz)
        sharedPrefUrlMqvExKvz = SharedPrefUrlMqvExKvz(applicationContext)
        setUpMyWebViewMqvExKvz()
        val swipeRefreshLayoutMqvExKvz =
            findViewById<SwipeRefreshLayout>(R.id.swipe_refresh_layout_main_mqvexkvz)
        swipeRefreshLayoutMqvExKvz.setOnRefreshListener {
            myWebViewMqvExKvz.loadUrl(myWebViewMqvExKvz.url ?: return@setOnRefreshListener)
            swipeRefreshLayoutMqvExKvz.isRefreshing = false
        }
        startService(Intent(this, InternetServiceMqvExKvz::class.java))
    }

    private fun setUpMyWebViewMqvExKvz() {
        val cookiesManagerMqvExKvz = CookieManager.getInstance()
        CookieManager.setAcceptFileSchemeCookies(true)
        cookiesManagerMqvExKvz.setAcceptThirdPartyCookies(myWebViewMqvExKvz, true)

        myWebViewMqvExKvz.scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY

        myWebViewMqvExKvz.settings.run {
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

        myWebViewMqvExKvz.webViewClient = MyWebViewClientMqvExKvz()
        myWebViewMqvExKvz.webChromeClient = object : WebChromeClient() {
            override fun onShowFileChooser(
                webView: WebView?,
                filePathCallback: ValueCallback<Array<Uri>>?,
                fileChooserParams: FileChooserParams?
            ): Boolean {
                this@MainActivityMqvExKvz.checkPermissionsMqvExKvz()
                mFilePathCallbackMqvExKvz = filePathCallback
                val capIntentMqvExKvz = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                if (capIntentMqvExKvz.resolveActivity(this@MainActivityMqvExKvz.packageManager) != null) {
                    val providedFileMqvExKvz: File = createTempFileMqvExKvz()
                    uriViewMqvExKvz = FileProvider.getUriForFile(
                        this@MainActivityMqvExKvz,
                        "${this@MainActivityMqvExKvz.application.packageName}.provider",
                        providedFileMqvExKvz
                    )
                    capIntentMqvExKvz.apply {
                        putExtra(MediaStore.EXTRA_OUTPUT, uriViewMqvExKvz)
                        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                    }
                    val actionIntentMqvExKvz = Intent(Intent.ACTION_GET_CONTENT).apply {
                        addCategory(Intent.CATEGORY_OPENABLE)
                        type = "image/*"
                    }
                    val intentChooserMqvExKvz = Intent(Intent.ACTION_CHOOSER).apply {
                        putExtra(Intent.EXTRA_INTENT, capIntentMqvExKvz)
                        putExtra(Intent.EXTRA_INITIAL_INTENTS, arrayOf(actionIntentMqvExKvz))
                    }
                    startActivityForResult(intentChooserMqvExKvz, 0)
                    return true

                }
                return super.onShowFileChooser(webView, filePathCallback, fileChooserParams)
            }
        }
        if (sharedPrefUrlMqvExKvz.lastPageMqvExKvz != null && sharedPrefUrlMqvExKvz.lastPageMqvExKvz != "") {
            myWebViewMqvExKvz.loadUrl(sharedPrefUrlMqvExKvz.lastPageMqvExKvz!!)
        } else {
            myWebViewMqvExKvz.loadUrl(sharedPrefUrlMqvExKvz.binomUrlMqvExKvz)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 0) {
            mFilePathCallbackMqvExKvz?.let {
                val uriResultMqvExKvz =
                    if (data == null || resultCode != RESULT_OK) null else data.data
                if (uriResultMqvExKvz != null) {
                    mFilePathCallbackMqvExKvz?.onReceiveValue(arrayOf(uriResultMqvExKvz))
                } else {
                    mFilePathCallbackMqvExKvz?.onReceiveValue(arrayOf(uriViewMqvExKvz))
                }
                mFilePathCallbackMqvExKvz = null
            }
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun onBackPressed() {
        when {
            myWebViewMqvExKvz.canGoBack() && myWebViewMqvExKvz.isFocused -> myWebViewMqvExKvz.goBack()
            myWebViewMqvExKvz.url != sharedPrefUrlMqvExKvz.binomUrlMqvExKvz -> {
                myWebViewMqvExKvz.loadUrl(
                    sharedPrefUrlMqvExKvz.binomUrlMqvExKvz
                )
            }
            else -> super.onBackPressed()
        }
    }

    private fun createTempFileMqvExKvz(): File {
        val dateMqvExKvz = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val fileDirMqvExKvz = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile("TMP${dateMqvExKvz}_MqvExKvz", ".jpg", fileDirMqvExKvz)

    }

    override fun onResume() {
        registerReceiver(myBroadcastReceiverMqvExKvz, IntentFilter()
            .apply { addAction(InternetServiceMqvExKvz.INTERNET_CHECK_MqvExKvz) })
        super.onResume()
    }

    override fun onPause() {
        internetDialogMqvExKvz?.dismiss()
        unregisterReceiver(myBroadcastReceiverMqvExKvz)
        super.onPause()
    }

    private val myBroadcastReceiverMqvExKvz = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent?.action == InternetServiceMqvExKvz.INTERNET_CHECK_MqvExKvz) {
                if (intent.getBooleanExtra(InternetServiceMqvExKvz.INTERNET_CHECK_MqvExKvz, true)) {
                    internetDialogMqvExKvz?.dismiss()
                    internetDialogMqvExKvz = null
                } else {
                    if (internetDialogMqvExKvz == null) {
                        internetDialogMqvExKvz = InternetDialogMqvExKvz().apply {
                            show(
                                supportFragmentManager,
                                "Internet_Dialog_MqvExKvz"
                            )
                        }
                    }
                }
            }
        }
    }
}
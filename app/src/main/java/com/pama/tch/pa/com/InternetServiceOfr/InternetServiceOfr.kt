package com.pama.tch.pa.com.InternetServiceOfr

import android.app.Service
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.util.Log

class InternetServiceOfr : Service() {


    private val internetServiceOfrHandler = Handler(Looper.getMainLooper())

    private val internetServiceOfrRunnable = object : Runnable {
        override fun run() {
            internetServiceOfrHandler.postDelayed(this, 500)
            Intent().run {
                action = INTERNET_CHECK_OFR
                putExtra(INTERNET_CHECK_OFR, isNetworkConnectionPresentOfr())
                sendBroadcast(this)
                Log.d("TEST_OFR", "Sended")
            }
        }
    }


    override fun onBind(p0: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        internetServiceOfrHandler.post(internetServiceOfrRunnable)
        return START_STICKY
    }


    fun isNetworkConnectionPresentOfr ():Boolean {
        val connectivityManagerOfr = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val networkCapabilitiesOfr = connectivityManagerOfr.getNetworkCapabilities(connectivityManagerOfr.activeNetwork) ?: return false
            return networkCapabilitiesOfr.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        } else {
            for (networkOfr in connectivityManagerOfr.allNetworks) {
                val networkInfoOfr = connectivityManagerOfr.getNetworkInfo(networkOfr)
                networkInfoOfr?.let {
                    if (networkInfoOfr.isConnected) return true
                }

            }
            return false
        }
    }

    companion object {
        const val  INTERNET_CHECK_OFR = "Internet_check"
    }
}
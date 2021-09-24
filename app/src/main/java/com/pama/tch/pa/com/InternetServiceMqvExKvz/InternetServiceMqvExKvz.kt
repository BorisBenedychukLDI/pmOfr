package com.pama.tch.pa.com.InternetServiceMqvExKvz

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

class InternetServiceMqvExKvz : Service() {


    private val internetServiceMqvExKvzHandler = Handler(Looper.getMainLooper())

    private val internetServiceMqvExKvzRunnable = object : Runnable {
        override fun run() {
            internetServiceMqvExKvzHandler.postDelayed(this, 500)
            Intent().run {
                action = INTERNET_CHECK_MqvExKvz
                putExtra(INTERNET_CHECK_MqvExKvz, isNetworkConnectionPresentMqvExKvz())
                sendBroadcast(this)
                Log.d("TEST_MqvExKvz", "Sended")
            }
        }
    }


    override fun onBind(p0: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        internetServiceMqvExKvzHandler.post(internetServiceMqvExKvzRunnable)
        return START_STICKY
    }


    fun isNetworkConnectionPresentMqvExKvz ():Boolean {
        val connectivityManagerMqvExKvz = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val networkCapabilitiesMqvExKvz = connectivityManagerMqvExKvz.getNetworkCapabilities(connectivityManagerMqvExKvz.activeNetwork) ?: return false
            return networkCapabilitiesMqvExKvz.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        } else {
            for (networkMqvExKvz in connectivityManagerMqvExKvz.allNetworks) {
                val networkInfoMqvExKvz = connectivityManagerMqvExKvz.getNetworkInfo(networkMqvExKvz)
                networkInfoMqvExKvz?.let {
                    if (networkInfoMqvExKvz.isConnected) return true
                }

            }
            return false
        }
    }

    companion object {
        const val  INTERNET_CHECK_MqvExKvz = "Internet_check"
    }
}
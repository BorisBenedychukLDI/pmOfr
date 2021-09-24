package com.pama.tch.pa.com

import android.app.Application
import com.appsflyer.AppsFlyerConversionListener
import com.appsflyer.AppsFlyerLib
import com.onesignal.OneSignal
import com.pama.tch.pa.com.UtilsMqvExKvz.*

class CustomApplicationMqvExKvz : Application () {
    override fun onCreate() {
        super.onCreate()
        OneSignal.initWithContext(this)
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE)
        OneSignal.setAppId(decodeBase64MqvExKvz(BuildConfig.ONE_SIGNAL_KEY))
        val appsFlyerConvMqvExKvz = object : AppsFlyerConversionListener {
            override fun onConversionDataSuccess(p0: MutableMap<String, Any>?) {
            }

            override fun onConversionDataFail(p0: String?) {
            }

            override fun onAppOpenAttribution(p0: MutableMap<String, String>?) {
            }

            override fun onAttributionFailure(p0: String?) {
            }
        }
        AppsFlyerLib.getInstance().run {
            init(decodeBase64MqvExKvz(BuildConfig.APPS_FLYER_KEY), appsFlyerConvMqvExKvz, this@CustomApplicationMqvExKvz)
            startTracking(this@CustomApplicationMqvExKvz)
        }

    }
}
package com.pama.tch.pa.com

import android.app.Application
import com.appsflyer.AppsFlyerConversionListener
import com.appsflyer.AppsFlyerLib
import com.onesignal.OneSignal
import com.yandex.metrica.YandexMetricaConfig
import com.pama.tch.pa.com.UtilsOfr.*
import com.yandex.metrica.YandexMetrica

class CustomApplicationOfr : Application () {
    override fun onCreate() {
        super.onCreate()
        OneSignal.initWithContext(this)
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE)
        OneSignal.setAppId(decodeBase64Ofr(ONE_SIGNAL_KEY))
        val appsFlyerConvOfr = object : AppsFlyerConversionListener {
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
            init(decodeBase64Ofr(APPS_FLYER_KEY), appsFlyerConvOfr, this@CustomApplicationOfr)
            startTracking(this@CustomApplicationOfr)
        }

    }
}
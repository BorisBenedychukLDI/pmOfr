package com.pama.tch.pa.com.UtilsMqvExKvz

import android.annotation.SuppressLint
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.util.Log

class SharedPrefUrlMqvExKvz (context: Context) {
    private val sharedPreferencesMqvExKvz = context.getSharedPreferences(APP_PREFERENCES_TAG_MqvExKvz, MODE_PRIVATE)

    var binomUrlMqvExKvz: String
    get() = sharedPreferencesMqvExKvz.getString(BINOM_KEY_TAG_MqvExKvz, null) ?: throw NullPointerException()
    @SuppressLint("CommitPrefEdits")
    set(value) {
        sharedPreferencesMqvExKvz.edit().putString(BINOM_KEY_TAG_MqvExKvz, value).apply()
        Log.d("TEST_MqvExKvz_URL_BINOM", value)
    }

    var lastPageMqvExKvz: String?
    get() = sharedPreferencesMqvExKvz.getString(LAST_PAGE_TAG_MqvExKvz, "")
    @SuppressLint("CommitPrefEdits")
    set(value) {
        value?.let {
            sharedPreferencesMqvExKvz.edit().putString(LAST_PAGE_TAG_MqvExKvz, value).apply()
            Log.d("TEST_MqvExKvz_LAST_PAGE", value)
        }
    }





    companion object {
        const val APP_PREFERENCES_TAG_MqvExKvz = "APP_PREFERENCES"
        const val BINOM_KEY_TAG_MqvExKvz = "BINOM_KEY"
        const val LAST_PAGE_TAG_MqvExKvz = "LAST_PAGE"
    }
}
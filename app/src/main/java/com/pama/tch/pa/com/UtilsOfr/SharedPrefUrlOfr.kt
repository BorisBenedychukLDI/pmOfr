package com.pama.tch.pa.com.UtilsOfr

import android.annotation.SuppressLint
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.util.Log

class SharedPrefUrlOfr (context: Context) {
    private val sharedPreferencesOfr = context.getSharedPreferences(APP_PREFERENCES_OFR_TAG, MODE_PRIVATE)

    var binomUrlOfr: String
    get() = sharedPreferencesOfr.getString(BINOM_KEY_OFR_TAG, null) ?: throw NullPointerException()
    @SuppressLint("CommitPrefEdits")
    set(value) {
        sharedPreferencesOfr.edit().putString(BINOM_KEY_OFR_TAG, value).apply()
        Log.d("TEST_OFR_URL_BINOM", value)
    }

    var lastPage: String?
    get() = sharedPreferencesOfr.getString(LAST_PAGE_OFR_TAG, "")
    @SuppressLint("CommitPrefEdits")
    set(value) {
        value?.let {
            sharedPreferencesOfr.edit().putString(LAST_PAGE_OFR_TAG, value).apply()
            Log.d("TEST_OFR_URL_LAST_PAGE", value)
        }
    }





    companion object {
        const val APP_PREFERENCES_OFR_TAG = "APP_PREFERENCES_OFR"
        const val BINOM_KEY_OFR_TAG = "BINOM_KEY"
        const val LAST_PAGE_OFR_TAG = "LAST_PAGE"
    }
}
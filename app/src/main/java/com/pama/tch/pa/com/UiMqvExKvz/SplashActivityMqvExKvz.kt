package com.pama.tch.pa.com.UiMqvExKvz

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import com.pama.tch.pa.com.BuildConfig
import com.pama.tch.pa.com.R
import com.pama.tch.pa.com.UtilsMqvExKvz.SharedPrefUrlMqvExKvz
import com.pama.tch.pa.com.UtilsMqvExKvz.decodeBase64MqvExKvz

class SplashActivityMqvExKvz : AppCompatActivity() {


    private lateinit var buttonSplashMqvExKvz: Button
    private lateinit var sharedPrefUrlMqvExKvz: SharedPrefUrlMqvExKvz

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_activity_mqvexkvz)
        val handlerMqvExKvz = Handler(Looper.getMainLooper())
        sharedPrefUrlMqvExKvz = SharedPrefUrlMqvExKvz(applicationContext)
        if (sharedPrefUrlMqvExKvz.lastPageMqvExKvz != "" && sharedPrefUrlMqvExKvz.lastPageMqvExKvz != null) {
            startActivity(Intent(this, MainActivityMqvExKvz::class.java))
            finish()
        }
        buttonSplashMqvExKvz = findViewById(R.id.button_splash_mqvexkvz)
        buttonSplashMqvExKvz.setOnClickListener {
            val loadingDialogMqvExKvz = LoadingDialogMqvExKvz().apply {
                show(supportFragmentManager, "Loading_dialog")
            }
            buttonSplashMqvExKvz.isClickable = false
            setupDefaultBinomUrlMqvExKvz()
            setUpBinomUrlMqvExKvz()
            handlerMqvExKvz.postDelayed({
                loadingDialogMqvExKvz.dismiss()
                startActivity(Intent(this, MainActivityMqvExKvz::class.java))
                finish()
                                }, 5000L)


        }



    }

    override fun onResume() {
        super.onResume()
        buttonSplashMqvExKvz.isClickable = true
    }

    private fun setupDefaultBinomUrlMqvExKvz () {
        sharedPrefUrlMqvExKvz.binomUrlMqvExKvz = decodeBase64MqvExKvz(BuildConfig.BINOM_KEY)
    }

    private fun setUpBinomUrlMqvExKvz () {
        val firebaseConfigMqvExKvz = FirebaseRemoteConfig.getInstance()
        val firebaseConfigSettingsMqvExKvz = FirebaseRemoteConfigSettings.Builder()
            .setMinimumFetchIntervalInSeconds(2500L)
            .build()
        firebaseConfigMqvExKvz.setDefaultsAsync(R.xml.firebase_defaults_mqvexkvz)
            .addOnCompleteListener {
            firebaseConfigMqvExKvz.setConfigSettingsAsync(firebaseConfigSettingsMqvExKvz).addOnCompleteListener {
                firebaseConfigMqvExKvz.fetchAndActivate().addOnCompleteListener {
                    val keyFireBaseMqvExKvz = firebaseConfigMqvExKvz.getString("binom_key_mqvexkvz")
                        if (keyFireBaseMqvExKvz == "binom_value_mqvexkvz") {
                            Log.d("TEST_MqvExKvz_URL", sharedPrefUrlMqvExKvz.binomUrlMqvExKvz)
                        } else {
                            sharedPrefUrlMqvExKvz.binomUrlMqvExKvz = keyFireBaseMqvExKvz
                            Log.d("TEST_MqvExKvz_URL", "Key from fb: = $keyFireBaseMqvExKvz")
                        }
                }
            }
        }
    }
}
package com.pama.tch.pa.com.UiOfr

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import com.pama.tch.pa.com.R
import com.pama.tch.pa.com.UtilsOfr.BINOM_KEY
import com.pama.tch.pa.com.UtilsOfr.SharedPrefUrlOfr
import com.pama.tch.pa.com.UtilsOfr.decodeBase64Ofr

class SplashActivityOfr : AppCompatActivity() {


    private lateinit var buttonSplash: Button
    private lateinit var sharedPrefUrlOfr: SharedPrefUrlOfr

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_activity_ofr)
        val handler = Handler(Looper.getMainLooper())
        sharedPrefUrlOfr = SharedPrefUrlOfr(applicationContext)
        if (sharedPrefUrlOfr.lastPage != "" && sharedPrefUrlOfr.lastPage != null) {
            startActivity(Intent(this, MainActivityOfr::class.java))
            finish()
        }
        buttonSplash = findViewById(R.id.button_splash_ofr)
        buttonSplash.setOnClickListener {
            val loadingDialogOfr = LoadingDialogOfr().apply {
                show(supportFragmentManager, "Loading_dialog")
            }
            buttonSplash.isClickable = false
            setupDefaultBinomUrlOfr()
            setUpBinomUrlOfr()
            handler.postDelayed({
                loadingDialogOfr.dismiss()
                startActivity(Intent(this, MainActivityOfr::class.java))
                                }, 5000L)

        }



    }

    override fun onResume() {
        super.onResume()
        buttonSplash.isClickable = true
    }

    private fun setupDefaultBinomUrlOfr () {
        sharedPrefUrlOfr.binomUrlOfr = decodeBase64Ofr(BINOM_KEY)
    }

    private fun setUpBinomUrlOfr () {
        val firebaseConfigOfr = FirebaseRemoteConfig.getInstance()
        val firebaseConfigSettings = FirebaseRemoteConfigSettings.Builder()
            .setMinimumFetchIntervalInSeconds(2500L)
            .build()
        firebaseConfigOfr.setDefaultsAsync(R.xml.firebase_dafaults_ofr)
            .addOnCompleteListener {
            firebaseConfigOfr.setConfigSettingsAsync(firebaseConfigSettings).addOnCompleteListener {
                firebaseConfigOfr.fetchAndActivate().addOnCompleteListener {
                    val keyFireBaseOfr = firebaseConfigOfr.getString("binom_key_ofr")
                        if (keyFireBaseOfr == "binom_value_ofr") {
                            Log.d("TEST_OFR_URL", sharedPrefUrlOfr.binomUrlOfr?: "null")
                        } else {
                            sharedPrefUrlOfr.binomUrlOfr = keyFireBaseOfr
                            Log.d("TEST_OFR_URL", "Key from fb: = $keyFireBaseOfr")
                        }
                }
            }
        }
    }
}
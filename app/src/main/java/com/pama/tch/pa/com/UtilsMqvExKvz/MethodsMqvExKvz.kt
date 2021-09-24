package com.pama.tch.pa.com.UtilsMqvExKvz

import android.Manifest
import android.content.Context
import android.util.Base64
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener


fun decodeBase64MqvExKvz (str: String) = String (Base64.decode(str, Base64.DEFAULT))


fun Context.checkPermissionsMqvExKvz () {
    Dexter.withContext(this)
        .withPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
        .withListener(object: MultiplePermissionsListener {
            override fun onPermissionsChecked(p0: MultiplePermissionsReport?) {
            }

            override fun onPermissionRationaleShouldBeShown(
                p0: MutableList<PermissionRequest>?,
                p1: PermissionToken?
            ) {
            }
        }).check()
}
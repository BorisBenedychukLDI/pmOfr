package com.pama.tch.pa.com.UiOfr

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.pama.tch.pa.com.R

class LoadingDialogOfr : DialogFragment(R.layout.loading_dialog_ofr) {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        isCancelable = false
        retainInstance = true
        return super.onCreateView(inflater, container, savedInstanceState)
    }
}
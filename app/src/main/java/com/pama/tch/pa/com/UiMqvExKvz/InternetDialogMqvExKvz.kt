package com.pama.tch.pa.com.UiMqvExKvz

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.pama.tch.pa.com.R

class InternetDialogMqvExKvz : DialogFragment()  {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        isCancelable = false
        return inflater.inflate(R.layout.internet_dialog_mqvexkvz, container, false)
    }
}
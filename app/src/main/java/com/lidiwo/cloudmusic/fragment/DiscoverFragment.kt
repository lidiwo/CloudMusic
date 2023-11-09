package com.lidiwo.cloudmusic.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.lidiwo.cloudmusic.base.BaseFragment

class DiscoverFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val tv = TextView(activity)
        tv.text = "发现"
        return tv
    }
}
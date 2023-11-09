package com.lidiwo.cloudmusic.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.lidiwo.cloudmusic.base.BaseFragment

class FriendsFragment: BaseFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val tv = TextView(activity)
        tv.text = "朋友"
        return tv
    }
}
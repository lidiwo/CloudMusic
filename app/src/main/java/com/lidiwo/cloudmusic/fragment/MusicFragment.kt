package com.lidiwo.cloudmusic.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.emoji.text.EmojiCompat
import com.lidiwo.cloudmusic.base.BaseFragment

class MusicFragment : BaseFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val tv = TextView(activity)
        val  processed = EmojiCompat.get().process("neutral face \uD83D\uDE10\uD83D\uDE20\uD83D\uDE30")

//        tv.text = "neutral face \uD83D\uDE10\uD83D\uDE20\uD83D\uDE30";
//        tv.setText("neutral face "+new String(Character.toChars(0x1F60a)));

        tv.text = processed
        return tv;
    }
}
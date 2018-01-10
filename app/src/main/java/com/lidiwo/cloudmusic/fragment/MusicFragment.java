package com.lidiwo.cloudmusic.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.text.emoji.EmojiCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lidiwo.cloudmusic.base.BaseFragment;

/**
 * *****************************************************
 *
 * @author：lidi
 * @date：2018/1/10 11:16
 * @Company：智能程序员
 * @Description： *****************************************************
 */
public class MusicFragment extends BaseFragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        TextView tv=new TextView(getActivity());
        CharSequence processed = EmojiCompat.get().process("neutral face \uD83D\uDE10\uD83D\uDE20\uD83D\uDE30");

        tv.setText("neutral face \uD83D\uDE10\uD83D\uDE20\uD83D\uDE30");
        tv.setText("neutral face "+new String(Character.toChars(0x1F60a)));

        tv.setText(processed);
        return tv;
    }
}

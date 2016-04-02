package com.kaanburaksener.bench.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.util.Log;

import com.kaanburaksener.bench.R;

import java.util.Locale;

/**
 * Created by kaanburaksener on 31/03/16.
 */
public class MyProfileFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_my_profile,container,false);

        return v;
    }

    /**
     * This function is used to initialize the layout elements and the attributes of the class
     */
    private void initializer(View v) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}

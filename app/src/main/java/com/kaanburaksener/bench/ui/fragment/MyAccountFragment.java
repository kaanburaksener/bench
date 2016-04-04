package com.kaanburaksener.bench.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kaanburaksener.bench.R;

/**
 * Created by kaanburaksener on 31/03/16.
 */
public class MyAccountFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_my_account,container,false);

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

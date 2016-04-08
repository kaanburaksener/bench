package com.kaanburaksener.bench.ui.fragment;

import android.app.Activity;
import android.support.v4.app.Fragment;

/**
 * Created by kaanburaksener on 07/04/16.
 */

public class BaseFragment extends Fragment {
    Activity mainActivity;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mainActivity =  activity;
    }
}

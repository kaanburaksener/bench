package com.kaanburaksener.bench.ui.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kaanburaksener.bench.R;
import com.kaanburaksener.bench.db.DBHandler;

/**
 * Created by kaanburaksener on 31/03/16.
 */
public class BrowseRequestsFragment extends BaseFragment {
    private DBHandler dbHandler;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_browse_requests,container,false);
        initializer(v);
        return v;
    }

    private void initializer(View v) {
        dbHandler = new DBHandler(mainActivity);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}

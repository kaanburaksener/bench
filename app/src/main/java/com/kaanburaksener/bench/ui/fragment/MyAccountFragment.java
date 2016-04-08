package com.kaanburaksener.bench.ui.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kaanburaksener.bench.R;
import com.kaanburaksener.bench.db.DBHandler;
import com.kaanburaksener.bench.core.Request;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kaanburaksener on 31/03/16.
 */

public class MyAccountFragment extends BaseFragment {
    private DBHandler dbHandler;
    private List<Request> userRequests;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_my_account,container,false);
        initializer(v);
        getUserRequests();
        return v;
    }

    /**
     * This function is used to initialize the layout elements and the attributes of the class
     */

    private void initializer(View v) {
        dbHandler = new DBHandler(mainActivity);
        this.userRequests = new ArrayList<Request>();
    }

    public void getUserRequests() {
        userRequests = dbHandler.getRequests();
        Log.d("ID", String.valueOf(userRequests.get(0).getID()).toString());
        Log.d("TITLE", userRequests.get(0).getTitle().toString());
        Log.d("DESC", userRequests.get(0).getDescription().toString());
        Log.d("LOCATION", userRequests.get(0).getLocation().toString());
        Log.d("STATUS", userRequests.get(0).getStatus().toString());
        Log.d("CREATED TIME", userRequests.get(0).getCreatedTime().toString());
        Log.d("OWNER ID", String.valueOf(userRequests.get(0).getOwnerID()).toString());
        Log.d("OWNER", userRequests.get(0).getOwnerName().toString());
        Log.d("TIME", userRequests.get(0).getTime().toString());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}

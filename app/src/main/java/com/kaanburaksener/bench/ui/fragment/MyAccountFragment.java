package com.kaanburaksener.bench.ui.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.kaanburaksener.bench.MainActivity;
import com.kaanburaksener.bench.R;
import com.kaanburaksener.bench.db.DBHandler;
import com.kaanburaksener.bench.core.User;
import com.kaanburaksener.bench.ui.ApplicationHistoryActivity;
import com.kaanburaksener.bench.ui.InitialActivity;
import com.kaanburaksener.bench.ui.MyProfileActivity;
import com.kaanburaksener.bench.ui.RequestHistoryActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kaanburaksener on 31/03/16.
 */

public class MyAccountFragment extends BaseFragment {
    private DBHandler dbHandler;
    private User user;
    private int userID;

    private TextView userName;
    private TextView myProfile;
    private TextView applicationHistory;
    private TextView requestHistory;
    private TextView signOut;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_my_account, container, false);

        initializer(v);

        return v;
    }

    /**
     * This function is used to initialize the layout elements and the attributes of the class
     */

    private void initializer(View v) {
        dbHandler = new DBHandler(mainActivity);
        userID = dbHandler.getUserId();
        user = dbHandler.getUser(userID);

        userName = (TextView) v.findViewById(R.id.userName);
        myProfile = (TextView) v.findViewById(R.id.myProfile);
        applicationHistory = (TextView) v.findViewById(R.id.applicationHistory);
        requestHistory = (TextView) v.findViewById(R.id.requestHistory);
        signOut = (TextView) v.findViewById(R.id.signOut);

        userName.setText(user.getName());

        myProfile.setOnClickListener(router);
        applicationHistory.setOnClickListener(router);
        requestHistory.setOnClickListener(router);
        signOut.setOnClickListener(router);
    }

    /**
     * This class is used to detect gesture and routing between activities
     */

    View.OnClickListener router = new View.OnClickListener() {
        public void onClick(View v) {
            switch(v.getId()) {
                case R.id.myProfile:
                    Intent i = new Intent(mainActivity.getApplicationContext(), MyProfileActivity.class);
                    startActivity(i);
                    mainActivity.finish();
                    break;
                case R.id.applicationHistory:
                    Intent i2 = new Intent(mainActivity.getApplicationContext(), ApplicationHistoryActivity.class);
                    startActivity(i2);
                    mainActivity.finish();
                    break;
                case R.id.requestHistory:
                    Intent i3 = new Intent(mainActivity.getApplicationContext(), RequestHistoryActivity.class);
                    startActivity(i3);
                    mainActivity.finish();
                    break;
                case R.id.signOut:
                    final ProgressDialog progressDialog =  new ProgressDialog(mainActivity.getWindow().getContext());
                    progressDialog.setMessage("Sign Out...");
                    progressDialog.show();

                    dbHandler.refreshDB();

                    progressDialog.dismiss();
                    Intent i4 = new Intent(mainActivity.getApplicationContext(), InitialActivity.class);
                    startActivity(i4);
                    mainActivity.finish();
                    break;
            }
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}

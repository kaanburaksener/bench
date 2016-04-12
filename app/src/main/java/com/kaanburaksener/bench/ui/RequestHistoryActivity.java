package com.kaanburaksener.bench.ui;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.kaanburaksener.bench.R;
import com.kaanburaksener.bench.core.Request;
import com.kaanburaksener.bench.db.DBHandler;
import com.kaanburaksener.bench.ui.adapter.OwnedRequestAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kaanburaksener on 09/04/16.
 */
public class RequestHistoryActivity extends AppCompatActivity {
    private DBHandler dbHandler;
    private List<Request> requests;
    private OwnedRequestAdapter adapter;
    private RecyclerView recList;
    private LinearLayoutManager llm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_history);
        initializer();
    }

    /**
     * This function is used to initialize the layout elements and the attributes of the class
     */

    private void initializer() {
        dbHandler = new DBHandler(this);
        recList = (RecyclerView) findViewById(R.id.requestList);
        recList.setHasFixedSize(true);
        llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);
        requests = new ArrayList<Request>();
        adapter = new OwnedRequestAdapter(getRequestList(), this, this.getApplicationContext(), this.getWindow().getContext());
        recList.setAdapter(adapter);

        setStatusBarColor();
    }

    /**
     * This function is used to change the status bar color
     */

    private void setStatusBarColor() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                getWindow().setStatusBarColor(getResources().getColor(R.color.color_primary_dark));
            }
        } catch (NumberFormatException e) {
            Log.d("Status bar set error: ", e.toString());
        }
    }

    private List<Request> getRequestList() {
        requests = dbHandler.getRequests();
        return requests;
    }

    @Override
    protected void onDestroy() {
        Log.d("Destroy", "Activity Gone");
        super.onDestroy();
    }
}

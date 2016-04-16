package com.kaanburaksener.bench.ui.activity;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.WindowManager;

import com.kaanburaksener.bench.R;
import com.kaanburaksener.bench.callback.VolleyCallback;
import com.kaanburaksener.bench.core.RequestApplication;
import com.kaanburaksener.bench.db.DBHandler;
import com.kaanburaksener.bench.handler.RequestApplicationHandler;
import com.kaanburaksener.bench.ui.adapter.ApplicationAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kaanburaksener on 09/04/16.
 */
public class ApplicationHistoryActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    private DBHandler dbHandler;
    private List<RequestApplication> applications;
    private ApplicationAdapter adapter;
    private RecyclerView recList;
    private LinearLayoutManager llm;
    private Activity activity;
    private SwipeRefreshLayout swipeRefreshLayout;
    private int requestID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application_history);

        Bundle extras = getIntent().getExtras();
        requestID = extras.getInt("request id");

        initializer();
    }

    /**
     * This function is used to initialize the layout elements and the attributes of the class
     */

    private void initializer() {
        activity = this;
        setStatusBarColor();
        applications = new ArrayList<RequestApplication>();
        dbHandler = new DBHandler(this);

        checkIncomingPage();

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(
                R.color.refresh_progress_1,
                R.color.refresh_progress_2,
                R.color.refresh_progress_3);

        recList = (RecyclerView) findViewById(R.id.applicationList);
        recList.setHasFixedSize(true);
        llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);
        adapter = new ApplicationAdapter(applications, activity, getApplicationContext(), getWindow().getContext());
        recList.setAdapter(adapter);

        swipeRefreshLayout.post(new Runnable() {
                @Override
                public void run() {
                    swipeRefreshLayout.setRefreshing(true);
                    getMyApplications();
                }
            }
        );
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

    /**
     * This function is used to list the user's applications
     */

    public void getMyApplications(){
        swipeRefreshLayout.setRefreshing(true);

        RequestApplicationHandler.performGetUserApplications(dbHandler.getUserId(), activity.getApplicationContext(), new VolleyCallback() {

            @Override
            public void onSuccess(JSONArray jsonArray) {
                try {
                    JSONObject response = (JSONObject) jsonArray.get(0);
                    int id = Integer.valueOf(response.getString("id"));

                    if(id != -1) {
                        applications.clear();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject request = (JSONObject) jsonArray.get(i);
                            RequestApplication application = new RequestApplication();

                            application.setRequestID(Integer.parseInt(request.getString("id")));
                            application.setTitle(request.getString("title"));
                            application.setLocation(request.getString("location"));
                            application.setPlayerPosition(dbHandler.getPlayerPositionName(Integer.parseInt(request.getString("player_position_id"))));
                            application.setTime(request.getString("time"));
                            application.setRequestStatus(dbHandler.getRequestStatusName(Integer.parseInt(request.getString("status_id"))));
                            application.setApplicationStatus(dbHandler.getApplicationStatusName(Integer.parseInt(request.getString("application_status_id"))));
                            application.setApplicationStatusID(Integer.parseInt(request.getString("application_status_id")));
                            application.setOwnerName(request.getString("request_owner_name"));
                            application.setOwnerID(Integer.parseInt(request.getString("request_owner_id")));

                            applications.add(application);
                        }
                    }

                    adapter = new ApplicationAdapter(applications, activity, getApplicationContext(), getWindow().getContext());
                    recList.setAdapter(adapter);
                    swipeRefreshLayout.setRefreshing(false);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String msg) {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    /**
     * This function is used to change the status of the notification of accepted application to not to push again and again
     */

    private void checkIncomingPage() {
        if(requestID != -1) {
            dbHandler.saveSeenAcceptedApplication(String.valueOf(requestID));
        }
    }

    /**
     * This method is called when swipe refresh is pulled down
     */

    @Override
    public void onRefresh() {
        getMyApplications();
    }

    @Override
    protected void onDestroy() {
        Log.d("Destroy", "Activity Gone");
        super.onDestroy();
    }
}

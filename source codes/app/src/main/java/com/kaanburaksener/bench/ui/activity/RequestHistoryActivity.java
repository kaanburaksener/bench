package com.kaanburaksener.bench.ui.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.WindowManager;

import com.kaanburaksener.bench.R;
import com.kaanburaksener.bench.callback.VolleyCallback;
import com.kaanburaksener.bench.core.Request;
import com.kaanburaksener.bench.db.DBHandler;
import com.kaanburaksener.bench.handler.RequestHandler;
import com.kaanburaksener.bench.ui.adapter.OwnedRequestAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
    private Activity activity;

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
        activity = this;
        requests = new ArrayList<Request>();
        setStatusBarColor();
        dbHandler = new DBHandler(this);

        recList = (RecyclerView) findViewById(R.id.requestList);
        recList.setHasFixedSize(true);
        llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);
        adapter = new OwnedRequestAdapter(requests, activity, getApplicationContext(), getWindow().getContext());
        recList.setAdapter(adapter);

        getMyRequestList();
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
     * This function is used to list the user's requests
     */

    public void getMyRequestList(){
        RequestHandler.performGetUserRequests(dbHandler.getUserId(), activity.getApplicationContext(), new VolleyCallback() {

            @Override
            public void onSuccess(JSONArray jsonArray) {
                try {
                    final ProgressDialog progressDialog = new ProgressDialog(getWindow().getContext());
                    progressDialog.setMessage("Request is being processed...");
                    progressDialog.show();

                    JSONObject response = (JSONObject) jsonArray.get(0);
                    int id = Integer.valueOf(response.getString("id"));

                    if(id != -1) {
                        requests.clear();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject request = (JSONObject) jsonArray.get(i);
                            com.kaanburaksener.bench.core.Request req = new com.kaanburaksener.bench.core.Request();

                            req.setID(Integer.parseInt(request.getString("id")));
                            req.setTitle(request.getString("title"));
                            req.setDescription(request.getString("description"));
                            req.setLocation(request.getString("location"));
                            req.setPlayerPosition(dbHandler.getPlayerPositionName(Integer.parseInt(request.getString("player_position_id"))));
                            req.setTime(request.getString("time"));
                            req.setStatus(dbHandler.getRequestStatusName(Integer.parseInt(request.getString("status_id"))));
                            req.setStatusID(Integer.parseInt(request.getString("status_id")));
                            req.setOwnerName(request.getString("request_owner_name"));

                            requests.add(req);
                        }
                    }

                    adapter = new OwnedRequestAdapter(requests, activity, getApplicationContext(), getWindow().getContext());
                    recList.setAdapter(adapter);
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String msg) {}
        });
    }

    @Override
    protected void onDestroy() {
        Log.d("Destroy", "Activity Gone");
        super.onDestroy();
    }
}
